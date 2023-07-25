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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CommandDataSyncHandler implements DataSyncHandler {
    private static final Component NO_PERMISSION_COMPONENT = Component.translatable("armorstatues.screen.failure.noPermission");

    @Nullable
    static ArmorStandScreenType lastType;

    private final ArmorStand armorStand;
    protected final LocalPlayer player;
    private ArmorStandPose lastSyncedPose;

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
        this.sendDataCommand(tag);
    }

    @Override
    public void sendPose(ArmorStandPose currentPose) {
        if (!this.testPermissionLevel()) return;
        currentPose.applyToEntity(this.getArmorStand());
        // split this into multiple chat messages as the client chat field has a very low character limit
        this.sendPosePart(currentPose::serializeBodyPoses, this.lastSyncedPose);
        this.sendPosePart(currentPose::serializeArmPoses, this.lastSyncedPose);
        this.sendPosePart(currentPose::serializeLegPoses, this.lastSyncedPose);
        this.lastSyncedPose = currentPose;
    }

    private void sendPosePart(BiPredicate<CompoundTag, ArmorStandPose> dataWriter, ArmorStandPose lastSyncedPose) {
        CompoundTag tag = new CompoundTag();
        if (dataWriter.test(tag, lastSyncedPose)) {
            CompoundTag tag1 = new CompoundTag();
            tag1.put("Pose", tag);
            this.sendDataCommand(tag1);
        }
    }

    @Override
    public void sendPosition(double posX, double posY, double posZ) {
        if (!this.testPermissionLevel()) return;
        ListTag listTag = new ListTag();
        listTag.add(DoubleTag.valueOf(posX));
        listTag.add(DoubleTag.valueOf(posY));
        listTag.add(DoubleTag.valueOf(posZ));
        CompoundTag tag = new CompoundTag();
        tag.put("Pos", listTag);
        this.sendDataCommand(tag);

    }

    @Override
    public void sendRotation(float rotation) {
        if (!this.testPermissionLevel()) return;
        ListTag listTag = new ListTag();
        listTag.add(FloatTag.valueOf(rotation));
        CompoundTag tag = new CompoundTag();
        tag.put("Rotation", listTag);
        this.sendDataCommand(tag);
    }

    @Override
    public void sendStyleOption(ArmorStandStyleOption styleOption, boolean value) {
        if (!this.testPermissionLevel()) return;
        styleOption.setOption(this.getArmorStand(), value);
        CompoundTag tag = new CompoundTag();
        styleOption.toTag(tag, value);
        this.sendDataCommand(tag);
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

    private boolean testPermissionLevel() {
        if (!this.player.hasPermissions(2)) {
            this.sendFailureMessage(NO_PERMISSION_COMPONENT);
            return false;
        }
        return true;
    }

    protected void sendFailureMessage(Component component) {
        this.sendDisplayMessage(Component.translatable("armorstatues.screen.failure", component), true);
    }

    protected void sendDisplayMessage(Component component, boolean failure) {
        this.player.displayClientMessage(Component.empty().append(component).withStyle(failure ? ChatFormatting.RED : ChatFormatting.GREEN), false);
    }

    private void sendDataCommand(CompoundTag tag) {
        this.player.commandSigned("data merge entity %s %s".formatted(this.getArmorStand().getStringUUID(), tag.getAsString()), null);
    }
}
