package fuzs.armorstatues.network.client.data;

import fuzs.armorstatues.client.gui.screens.inventory.ArmorStandScreenType;
import fuzs.armorstatues.client.gui.screens.inventory.ArmorStandStyleOption;
import fuzs.armorstatues.world.inventory.ArmorStandPose;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CommandDataSyncHandler implements DataSyncHandler {
    private final ArmorStand armorStand;

    public CommandDataSyncHandler(ArmorStand armorStand) {
        this.armorStand = armorStand;
    }

    @Override
    public ArmorStand getArmorStand() {
        return this.armorStand;
    }

    @Override
    public void sendName(String name) {
        if (!this.testPermissionLevel()) return;
        DataSyncHandler.super.sendName(name);
        CompoundTag tag = new CompoundTag();
        tag.putString("CustomName", Component.Serializer.toJson(Component.literal(name)));
        this.sendCommand(tag);
    }

    @Override
    public void sendPose(ArmorStandPose currentPose, ArmorStandPose lastSyncedPose) {
        if (!this.testPermissionLevel()) return;
        DataSyncHandler.super.sendPose(currentPose, lastSyncedPose);
        // split this into multiple chat messages as the client chat field has a very low character limit
        this.sendPosePart(currentPose::serializeBodyPoses, lastSyncedPose);
        this.sendPosePart(currentPose::serializeArmPoses, lastSyncedPose);
        this.sendPosePart(currentPose::serializeLegPoses, lastSyncedPose);
    }

    private void sendPosePart(BiPredicate<CompoundTag, ArmorStandPose> dataWriter, ArmorStandPose lastSyncedPose) {
        CompoundTag tag = new CompoundTag();
        if (dataWriter.test(tag, lastSyncedPose)) {
            CompoundTag tag1 = new CompoundTag();
            tag1.put("Pose", tag);
            this.sendCommand(tag1);
        }
    }

    @Override
    public void sendPosition(double posX, double posY, double posZ) {
        if (!this.testPermissionLevel()) return;
        DataSyncHandler.super.sendPosition(posX, posY, posZ);
        ListTag listTag = new ListTag();
        listTag.add(DoubleTag.valueOf(posX));
        listTag.add(DoubleTag.valueOf(posY));
        listTag.add(DoubleTag.valueOf(posZ));
        CompoundTag tag = new CompoundTag();
        tag.put("Pos", listTag);
        this.sendCommand(tag);

    }

    @Override
    public void sendRotation(float rotation) {
        if (!this.testPermissionLevel()) return;
        DataSyncHandler.super.sendRotation(rotation);
        ListTag listTag = new ListTag();
        listTag.add(FloatTag.valueOf(rotation));
        CompoundTag tag = new CompoundTag();
        tag.put("Rotation", listTag);
        this.sendCommand(tag);
    }

    @Override
    public void sendStyleOption(ArmorStandStyleOption styleOption, boolean value) {
        if (!this.testPermissionLevel()) return;
        DataSyncHandler.super.sendStyleOption(styleOption, value);
        String dataKey = switch (styleOption) {
            case SHOW_NAME -> "CustomNameVisible";
            case SHOW_ARMS -> "ShowArms";
            case SMALL -> "Small";
            case INVISIBLE -> "Invisible";
            case NO_BASE_PLATE -> "NoBasePlate";
            case NO_GRAVITY -> "NoGravity";
            case SEALED -> "Invulnerable";
        };
        CompoundTag tag = new CompoundTag();
        tag.putBoolean(dataKey, value);
        if (styleOption == ArmorStandStyleOption.SEALED) {
            tag.putInt("DisabledSlots", value ? 4144959 : 0);
        }
        this.sendCommand(tag);
    }

    @Override
    public ArmorStandScreenType[] tabs() {
        return Stream.of(ArmorStandScreenType.values()).filter(Predicate.not(ArmorStandScreenType::requiresServer)).toArray(ArmorStandScreenType[]::new);
    }

    @Override
    public Optional<ArmorStandScreenType> getLastType(@Nullable ArmorStandScreenType lastType) {
        return Optional.ofNullable(lastType).filter(Predicate.not(ArmorStandScreenType::requiresServer));
    }

    private boolean testPermissionLevel() {
        Player player = Minecraft.getInstance().player;
        if (!player.hasPermissions(2)) {
            player.displayClientMessage(Component.translatable("armorstatues.screen.noPermission").withStyle(ChatFormatting.RED), false);
            return false;
        }
        return true;
    }

    private void sendCommand(CompoundTag tag) {
        Minecraft.getInstance().player.commandSigned("data merge entity %s %s".formatted(this.getArmorStand().getStringUUID(), tag.getAsString()), null);
    }
}
