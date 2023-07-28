package fuzs.armorstatues.network.client.data;

import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.api.network.client.data.DataSyncHandler;
import fuzs.armorstatues.api.world.inventory.ArmorStandHolder;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandAlignment;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandPose;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandScreenType;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandStyleOption;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.BiPredicate;

public class CommandDataSyncHandler implements DataSyncHandler {
    public static final String NO_PERMISSION_TRANSLATION_KEY = ArmorStatues.MOD_ID + ".dataSync.failure.noPermission";
    public static final String NO_ARMOR_STAND_TRANSLATION_KEY = ArmorStatues.MOD_ID + ".dataSync.failure.noArmorStand";
    public static final String NOT_FINISHED_TRANSLATION_KEY = ArmorStatues.MOD_ID + ".dataSync.failure.notFinished";
    public static final String FINISHED_TRANSLATION_KEY = ArmorStatues.MOD_ID + ".dataSync.finished";
    public static final String FAILURE_TRANSLATION_KEY = ArmorStatues.MOD_ID + ".dataSync.failure";
    private static final Queue<String> CLIENT_COMMAND_QUEUE = new ArrayDeque<>();

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
        tag.putString("CustomName", Component.Serializer.toJson(Component.literal(name)));
        this.enqueueEntityData(tag);
        this.finalizeCurrentOperation();
    }

    @Override
    public final void sendPose(ArmorStandPose pose) {
        this.sendPose(pose, true);
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
    public final void sendPosition(double posX, double posY, double posZ) {
        this.sendPosition(posX, posY, posZ, true);

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
    public final void sendRotation(float rotation) {
        this.sendRotation(rotation, true);
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
    public final void sendStyleOption(ArmorStandStyleOption styleOption, boolean value) {
        this.sendStyleOption(styleOption, value, true);
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
            if (this.testArmorStand(queueArmorStand)) {
                this.player.commandSigned(CLIENT_COMMAND_QUEUE.poll(), null);
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
        return this.isEditingAllowed(true);
    }

    protected final boolean isEditingAllowed(boolean testPermissionLevel) {
        if (testPermissionLevel && !this.player.hasPermissions(2)) {
            this.sendFailureMessage(Component.translatable(NO_PERMISSION_TRANSLATION_KEY));
            return false;
        } else if (queueArmorStand != null && !this.testArmorStand(queueArmorStand)) {
            this.sendFailureMessage(Component.translatable(NO_ARMOR_STAND_TRANSLATION_KEY));
            return false;
        }
        return true;
    }

    protected boolean testArmorStand(ArmorStand armorStand) {
        return armorStand.isAlive();
    }

    protected boolean enqueueClientCommand(String clientCommand) {
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
        this.player.displayClientMessage(Component.empty().append(component).withStyle(failure ? ChatFormatting.RED : ChatFormatting.GREEN), false);
    }

    private void enqueueEntityData(CompoundTag tag) {
        this.enqueueClientCommand("data merge entity %s %s".formatted(this.getArmorStand().getStringUUID(), tag.getAsString()));
    }
}
