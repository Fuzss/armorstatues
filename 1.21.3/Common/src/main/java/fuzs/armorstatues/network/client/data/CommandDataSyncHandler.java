package fuzs.armorstatues.network.client.data;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Unit;
import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.config.ClientConfig;
import fuzs.statuemenus.api.v1.helper.ScaleAttributeHelper;
import fuzs.statuemenus.api.v1.network.client.data.DataSyncHandler;
import fuzs.statuemenus.api.v1.world.inventory.ArmorStandHolder;
import fuzs.statuemenus.api.v1.world.inventory.data.ArmorStandAlignment;
import fuzs.statuemenus.api.v1.world.inventory.data.ArmorStandPose;
import fuzs.statuemenus.api.v1.world.inventory.data.ArmorStandScreenType;
import fuzs.statuemenus.api.v1.world.inventory.data.ArmorStandStyleOption;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiPredicate;

public class CommandDataSyncHandler implements DataSyncHandler {
    public static final String NO_PERMISSION_TRANSLATION_KEY = ArmorStatues.MOD_ID + ".dataSync.failure.noPermission";
    public static final String NO_ARMOR_STAND_TRANSLATION_KEY = ArmorStatues.MOD_ID + ".dataSync.failure.noArmorStand";
    public static final String OUT_OF_RANGE_TRANSLATION_KEY = ArmorStatues.MOD_ID + ".dataSync.failure.outOfRange";
    public static final String NOT_FINISHED_TRANSLATION_KEY = ArmorStatues.MOD_ID + ".dataSync.failure.notFinished";
    public static final String FINISHED_TRANSLATION_KEY = ArmorStatues.MOD_ID + ".dataSync.finished";
    public static final String FAILURE_TRANSLATION_KEY = ArmorStatues.MOD_ID + ".dataSync.failure";
    private static final Queue<List<String>> CLIENT_COMMAND_QUEUE = new ArrayDeque<>();

    @Nullable
    private static ArmorStand queueArmorStand;
    private static int itemDequeuedTicks;

    private final ArmorStandHolder holder;
    protected final LocalPlayer player;
    protected ArmorStandPose lastSyncedPose;

    public CommandDataSyncHandler(ArmorStandHolder holder, LocalPlayer player) {
        this.holder = holder;
        this.lastSyncedPose = ArmorStandPose.fromEntity(this.holder.getArmorStand());
        this.player = player;
    }

    @Override
    public ArmorStandHolder getArmorStandHolder() {
        return this.holder;
    }

    @Override
    public void sendName(String name) {
        if (!this.isEditingAllowed()) return;
        DataSyncHandler.setCustomArmorStandName(this.getArmorStand(), name);
        CompoundTag tag = new CompoundTag();
        tag.putString("CustomName", Component.Serializer.toJson(Component.literal(name), this.player.registryAccess()));
        this.enqueueEntityData(tag);
        this.finalizeCurrentOperation();
    }

    @Override
    public void sendPose(ArmorStandPose pose, boolean finalize) {
        if (!this.isEditingAllowed()) return;
        // split this into multiple chat messages as the client chat field has a very low character limit
        this.sendPosePart(pose::serializeBodyPoses, this.lastSyncedPose);
        this.sendPosePart(pose::serializeArmPoses, this.lastSyncedPose);
        this.sendPosePart(pose::serializeLegPoses, this.lastSyncedPose);
        pose.applyToEntity(this.getArmorStand());
        this.lastSyncedPose = pose.copyAndFillFrom(this.lastSyncedPose);
        if (finalize) this.finalizeCurrentOperation();
    }

    private void sendPosePart(BiPredicate<CompoundTag, ArmorStandPose> dataWriter, ArmorStandPose lastSyncedPose) {
        CompoundTag tag = new CompoundTag();
        if (dataWriter.test(tag, lastSyncedPose)) {
            CompoundTag tagToSend = new CompoundTag();
            tagToSend.put("Pose", tag);
            this.enqueueEntityData(tagToSend);
        }
    }

    @Override
    public @Nullable ArmorStandPose getLastSyncedPose() {
        return this.lastSyncedPose;
    }

    @Override
    public void sendPosition(double posX, double posY, double posZ, boolean finalize) {
        if (!this.isEditingAllowed()) return;
        ListTag listTag = new ListTag();
        listTag.add(DoubleTag.valueOf(posX));
        listTag.add(DoubleTag.valueOf(posY));
        listTag.add(DoubleTag.valueOf(posZ));
        CompoundTag tag = new CompoundTag();
        tag.put("Pos", listTag);
        this.enqueueEntityData(tag);
        if (finalize) this.finalizeCurrentOperation();
    }

    @Override
    public void sendScale(float scale, boolean finalize) {
        this.sendScale(true, scale, finalize);
    }

    public void sendScale(boolean hasModifier, float scale, boolean finalize) {
        // track if our scale attribute modifier is already present, so we only try to remove it if necessary
        // otherwise an error message from the remove command is displayed which would also be fine if this no longer works
        List<String> clientCommands = new ArrayList<>();
        if (hasModifier) {
            clientCommands.add("attribute %s %s modifier remove %s".formatted(this.getArmorStand().getStringUUID(),
                    Attributes.SCALE.unwrapKey().orElseThrow().location(),
                    ScaleAttributeHelper.SCALE_BONUS_ID));
        }
        if (scale != ScaleAttributeHelper.DEFAULT_SCALE) {
            clientCommands.add("attribute %s %s modifier add %s %s add_value".formatted(this.getArmorStand()
                            .getStringUUID(),
                    Attributes.SCALE.unwrapKey().orElseThrow().location(),
                    ScaleAttributeHelper.SCALE_BONUS_ID,
                    scale - ScaleAttributeHelper.DEFAULT_SCALE));
        }
        this.enqueueClientCommand(clientCommands);
        if (finalize) this.finalizeCurrentOperation();
    }

    @Override
    public void sendRotation(float rotation, boolean finalize) {
        if (!this.isEditingAllowed()) return;
        ListTag listTag = new ListTag();
        listTag.add(FloatTag.valueOf(rotation));
        CompoundTag tag = new CompoundTag();
        tag.put("Rotation", listTag);
        this.enqueueEntityData(tag);
        if (finalize) this.finalizeCurrentOperation();
    }

    @Override
    public void sendStyleOption(ArmorStandStyleOption styleOption, boolean value, boolean finalize) {
        if (!this.isEditingAllowed()) return;
        CompoundTag tag = new CompoundTag();
        styleOption.toTag(tag, value);
        this.enqueueEntityData(tag);
        styleOption.setOption(this.getArmorStand(), value);
        if (finalize) this.finalizeCurrentOperation();
    }

    @Override
    public void sendAlignment(ArmorStandAlignment alignment) {
        if (!this.isEditingAllowed()) return;
        DataSyncHandler.super.sendAlignment(alignment);
    }

    @Override
    public boolean supportsScreenType(ArmorStandScreenType screenType) {
        return !screenType.requiresServer();
    }

    @Override
    public void tick() {
        if (itemDequeuedTicks > 0) itemDequeuedTicks--;
        if (itemDequeuedTicks == 0 && queueArmorStand != null && !CLIENT_COMMAND_QUEUE.isEmpty()) {
            if (this.testArmorStand(queueArmorStand).right().isPresent()) {
                for (String clientCommand : CLIENT_COMMAND_QUEUE.poll()) {
                    this.player.connection.sendCommand(clientCommand);
                }
            } else {
                CLIENT_COMMAND_QUEUE.clear();
            }
            itemDequeuedTicks = this.getDequeueDelayTicks();
        } else if (itemDequeuedTicks == 1 && CLIENT_COMMAND_QUEUE.isEmpty()) {
            this.sendDisplayMessage(Component.translatable(FINISHED_TRANSLATION_KEY), false);
        }
    }

    protected int getDequeueDelayTicks() {
        return 5;
    }

    @Override
    public boolean shouldContinueTicking() {
        return !CLIENT_COMMAND_QUEUE.isEmpty() || itemDequeuedTicks != 0;
    }

    protected boolean isEditingAllowed() {
        return this.isEditingAllowed(!ArmorStatues.CONFIG.get(ClientConfig.class).overrideClientPermissionsCheck);
    }

    protected final boolean isEditingAllowed(boolean testPermissionLevel) {
        if (testPermissionLevel && !this.player.hasPermissions(2)) {
            this.sendFailureMessage(Component.translatable(NO_PERMISSION_TRANSLATION_KEY));
            return false;
        }
        return this.player.getAbilities().mayBuild &&
                this.testArmorStand(this.getArmorStand()).ifLeft(this::sendFailureMessage).right().isPresent();
    }

    protected Either<Component, Unit> testArmorStand(ArmorStand armorStand) {
        return !armorStand.isAlive() ? Either.left(Component.translatable(NO_ARMOR_STAND_TRANSLATION_KEY)) :
                Either.right(Unit.INSTANCE);
    }

    protected boolean enqueueClientCommand(String clientCommand) {
        return this.enqueueClientCommand(Collections.singletonList(clientCommand));
    }

    protected boolean enqueueClientCommand(List<String> clientCommand) {
        if (CLIENT_COMMAND_QUEUE.isEmpty()) {
            queueArmorStand = null;
        } else if (queueArmorStand != null) {
            this.sendFailureMessage(Component.translatable(NOT_FINISHED_TRANSLATION_KEY));
            return false;
        }
        CLIENT_COMMAND_QUEUE.offer(clientCommand);
        return true;
    }

    @Override
    public void finalizeCurrentOperation() {
        if (!CLIENT_COMMAND_QUEUE.isEmpty()) {
            queueArmorStand = this.getArmorStand();
        }
    }

    protected void sendFailureMessage(Component component) {
        this.sendDisplayMessage(Component.translatable(FAILURE_TRANSLATION_KEY, component), true);
    }

    protected void sendDisplayMessage(Component component, boolean failure) {
        this.player.displayClientMessage(Component.empty()
                .append(component)
                .withStyle(failure ? ChatFormatting.RED : ChatFormatting.GREEN), false);
    }

    private void enqueueEntityData(CompoundTag tag) {
        this.enqueueClientCommand("data merge entity %s %s".formatted(this.getArmorStand().getStringUUID(),
                tag.getAsString()));
    }
}
