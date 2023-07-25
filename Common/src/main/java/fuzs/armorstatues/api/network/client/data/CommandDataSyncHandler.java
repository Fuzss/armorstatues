package fuzs.armorstatues.api.network.client.data;

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

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CommandDataSyncHandler implements DataSyncHandler {
    private static final Component NO_PERMISSION_COMPONENT = Component.translatable("armorstatues.screen.failure.noPermission");
    private static final Component NOT_FINISHED_COMPONENT = Component.translatable("armorstatues.screen.failure.notFinished");
    private static final Component FINISHED_COMPONENT = Component.translatable("armorstatues.screen.finished");
    private static final Queue<String> CLIENT_COMMAND_QUEUE = new ArrayDeque<>();

    @Nullable
    static ArmorStandScreenType lastType;
    @Nullable
    private static ArmorStand queueArmorStand;
    private static int itemDequeuedTicks;

    private final ArmorStand armorStand;
    protected final LocalPlayer player;
    protected ArmorStandPose lastSyncedPose;

    public CommandDataSyncHandler(ArmorStand armorStand, LocalPlayer player) {
        this.armorStand = armorStand;
        this.lastSyncedPose = ArmorStandPose.fromEntity(armorStand);
        this.player = player;
    }

    @Override
    public ArmorStand getArmorStand() {
        return this.armorStand;
    }

    @Override
    public void sendName(String name) {
        if (!this.testPermissionLevel()) return;
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
        if (!this.testPermissionLevel()) return;
        pose.applyToEntity(this.getArmorStand());
        // split this into multiple chat messages as the client chat field has a very low character limit
        this.sendPosePart(pose::serializeBodyPoses, this.lastSyncedPose);
        this.sendPosePart(pose::serializeArmPoses, this.lastSyncedPose);
        this.sendPosePart(pose::serializeLegPoses, this.lastSyncedPose);
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
    public final void sendPosition(double posX, double posY, double posZ) {
        this.sendPosition(posX, posY, posZ, true);

    }

    @Override
    public void sendPosition(double posX, double posY, double posZ, boolean finalize) {
        if (!this.testPermissionLevel()) return;
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
        if (!this.testPermissionLevel()) return;
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
        if (!this.testPermissionLevel()) return;
        styleOption.setOption(this.getArmorStand(), value);
        CompoundTag tag = new CompoundTag();
        styleOption.toTag(tag, value);
        this.enqueueEntityData(tag);
        if (finalize) this.finalizeCurrentOperation();
    }

    @Override
    public ArmorStandScreenType[] tabs() {
        return Stream.of(this.getDataProvider().getScreenTypes()).filter(Predicate.not(ArmorStandScreenType::requiresServer)).toArray(ArmorStandScreenType[]::new);
    }

    @Override
    public Optional<ArmorStandScreenType> getLastType() {
        List<ArmorStandScreenType> screenTypes = Arrays.asList(this.getDataProvider().getScreenTypes());
        return Optional.ofNullable(lastType).filter(screenTypes::contains).filter(Predicate.not(ArmorStandScreenType::requiresServer));
    }

    @Override
    public void setLastType(ArmorStandScreenType lastType) {
        CommandDataSyncHandler.lastType = lastType;
    }

    @Override
    public void tick() {
        if (itemDequeuedTicks > 0) itemDequeuedTicks--;
        if (itemDequeuedTicks == 0 && queueArmorStand != null && !CLIENT_COMMAND_QUEUE.isEmpty()) {
            if (queueArmorStand.isAlive()) {
                this.player.commandSigned(CLIENT_COMMAND_QUEUE.poll(), null);
            } else {
                CLIENT_COMMAND_QUEUE.clear();
            }
            itemDequeuedTicks = this.getDequeueDelayTicks();
        } else if (itemDequeuedTicks == 1 && CLIENT_COMMAND_QUEUE.isEmpty()) {
            this.sendDisplayMessage(FINISHED_COMPONENT, false);
        }
    }

    protected int getDequeueDelayTicks() {
        return 5;
    }

    @Override
    public boolean shouldContinueTicking() {
        return !CLIENT_COMMAND_QUEUE.isEmpty() || itemDequeuedTicks != 0;
    }

    private boolean testPermissionLevel() {
        if (!this.player.hasPermissions(2)) {
            this.sendFailureMessage(NO_PERMISSION_COMPONENT);
            return false;
        }
        return true;
    }

    protected boolean enqueueClientCommand(String clientCommand) {
        if (CLIENT_COMMAND_QUEUE.isEmpty()) {
            queueArmorStand = null;
        } else if (queueArmorStand != null) {
            this.sendFailureMessage(NOT_FINISHED_COMPONENT);
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
        this.sendDisplayMessage(Component.translatable("armorstatues.screen.failure", component), true);
    }

    protected void sendDisplayMessage(Component component, boolean failure) {
        this.player.displayClientMessage(Component.empty().append(component).withStyle(failure ? ChatFormatting.RED : ChatFormatting.GREEN), false);
    }

    private void enqueueEntityData(CompoundTag tag) {
        this.enqueueClientCommand("data merge entity %s %s".formatted(this.getArmorStand().getStringUUID(), tag.getAsString()));
    }
}
