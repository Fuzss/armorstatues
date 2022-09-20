package fuzs.armorstatues.network.client.data;

import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.client.gui.screens.inventory.ArmorStandScreenType;
import fuzs.armorstatues.client.gui.screens.inventory.ArmorStandStyleOption;
import fuzs.armorstatues.network.client.*;
import fuzs.armorstatues.world.inventory.ArmorStandPose;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class NetworkDataSyncHandler implements DataSyncHandler {
    private final ArmorStand armorStand;

    public NetworkDataSyncHandler(ArmorStand armorStand) {
        this.armorStand = armorStand;
    }

    @Override
    public ArmorStand getArmorStand() {
        return this.armorStand;
    }

    @Override
    public void sendName(String name) {
        DataSyncHandler.super.sendName(name);
        ArmorStatues.NETWORK.sendToServer(new C2SArmorStandNameMessage(name));
    }

    @Override
    public void sendPose(ArmorStandPose currentPose, ArmorStandPose lastSyncedPose) {
        DataSyncHandler.super.sendPose(currentPose, lastSyncedPose);
        CompoundTag tag = new CompoundTag();
        if (currentPose.serializeAllPoses(tag, lastSyncedPose)) {
            ArmorStatues.NETWORK.sendToServer(new C2SArmorStandPoseMessage(tag));
        }
    }

    @Override
    public void sendPosition(double posX, double posY, double posZ) {
        DataSyncHandler.super.sendPosition(posX, posY, posZ);
        ArmorStatues.NETWORK.sendToServer(new C2SArmorStandPositionMessage(posX, posY, posZ));
    }

    @Override
    public void sendRotation(float rotation) {
        DataSyncHandler.super.sendRotation(rotation);
        ArmorStatues.NETWORK.sendToServer(new C2SArmorStandRotationMessage(rotation));
    }

    @Override
    public void sendStyleOption(ArmorStandStyleOption styleOption, boolean value) {
        DataSyncHandler.super.sendStyleOption(styleOption, value);
        ArmorStatues.NETWORK.sendToServer(new C2SArmorStandStyleMessage(styleOption, value));
    }

    @Override
    public ArmorStandScreenType[] tabs() {
        return ArmorStandScreenType.values();
    }

    @Override
    public Optional<ArmorStandScreenType> getLastType(@Nullable ArmorStandScreenType lastType) {
        return Optional.ofNullable(lastType);
    }
}
