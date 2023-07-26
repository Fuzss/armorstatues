package fuzs.armorstatues.api.network.client.data;

import fuzs.armorstatues.api.ArmorStatuesApi;
import fuzs.armorstatues.api.network.client.*;
import fuzs.armorstatues.api.world.inventory.ArmorStandHolder;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandPose;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandScreenType;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandStyleOption;
import net.minecraft.nbt.CompoundTag;

public class NetworkDataSyncHandler implements DataSyncHandler {
    private final ArmorStandHolder holder;

    public NetworkDataSyncHandler(ArmorStandHolder holder) {
        this.holder = holder;
    }

    @Override
    public ArmorStandHolder getArmorStandHolder() {
        return this.holder;
    }

    @Override
    public void sendName(String name) {
        DataSyncHandler.setCustomArmorStandName(this.getArmorStand(), name);
        ArmorStatuesApi.NETWORK.sendToServer(new C2SArmorStandNameMessage(name));
    }

    @Override
    public void sendPose(ArmorStandPose pose) {
        pose.applyToEntity(this.getArmorStand());
        CompoundTag tag = new CompoundTag();
        pose.serializeAllPoses(tag);
        ArmorStatuesApi.NETWORK.sendToServer(new C2SArmorStandPoseMessage(tag));
    }

    @Override
    public void sendPosition(double posX, double posY, double posZ) {
        ArmorStatuesApi.NETWORK.sendToServer(new C2SArmorStandPositionMessage(posX, posY, posZ));
    }

    @Override
    public void sendRotation(float rotation) {
        ArmorStatuesApi.NETWORK.sendToServer(new C2SArmorStandRotationMessage(rotation));
    }

    @Override
    public void sendStyleOption(ArmorStandStyleOption styleOption, boolean value) {
        styleOption.setOption(this.getArmorStand(), value);
        ArmorStatuesApi.NETWORK.sendToServer(new C2SArmorStandStyleMessage(styleOption, value));
    }

    @Override
    public ArmorStandScreenType[] tabs() {
        return this.getArmorStandHolder().getDataProvider().getScreenTypes();
    }
}
