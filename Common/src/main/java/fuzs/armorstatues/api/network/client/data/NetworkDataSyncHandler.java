package fuzs.armorstatues.api.network.client.data;

import fuzs.armorstatues.api.ArmorStatuesApi;
import fuzs.armorstatues.api.network.client.*;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandPose;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandScreenType;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandStyleOption;
import fuzs.armorstatues.network.client.data.CommandDataSyncHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class NetworkDataSyncHandler implements DataSyncHandler {
    @Nullable
    private static ArmorStandScreenType lastType;

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
        ArmorStatuesApi.NETWORK.sendToServer(new C2SArmorStandNameMessage(name));
    }

    @Override
    public void sendPose(ArmorStandPose currentPose) {
        DataSyncHandler.super.sendPose(currentPose);
        CompoundTag tag = new CompoundTag();
        currentPose.serializeAllPoses(tag);
        ArmorStatuesApi.NETWORK.sendToServer(new C2SArmorStandPoseMessage(tag));
    }

    @Override
    public void sendPosition(double posX, double posY, double posZ) {
        DataSyncHandler.super.sendPosition(posX, posY, posZ);
        ArmorStatuesApi.NETWORK.sendToServer(new C2SArmorStandPositionMessage(posX, posY, posZ));
    }

    @Override
    public void sendRotation(float rotation) {
        DataSyncHandler.super.sendRotation(rotation);
        ArmorStatuesApi.NETWORK.sendToServer(new C2SArmorStandRotationMessage(rotation));
    }

    @Override
    public void sendStyleOption(ArmorStandStyleOption styleOption, boolean value) {
        DataSyncHandler.super.sendStyleOption(styleOption, value);
        ArmorStatuesApi.NETWORK.sendToServer(new C2SArmorStandStyleMessage(styleOption, value));
    }

    @Override
    public ArmorStandScreenType[] tabs() {
        return this.getDataProvider().getScreenTypes();
    }

    @Override
    public Optional<ArmorStandScreenType> getLastType() {
        List<ArmorStandScreenType> screenTypes = Arrays.asList(this.getDataProvider().getScreenTypes());
        return Optional.ofNullable(lastType).filter(screenTypes::contains);
    }

    @Override
    public void setLastType(ArmorStandScreenType lastType) {
        NetworkDataSyncHandler.lastType = CommandDataSyncHandler.lastType = lastType;
    }
}
