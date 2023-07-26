package fuzs.armorstatues.api.network.client.data;

import fuzs.armorstatues.api.world.inventory.ArmorStandHolder;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandPose;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandScreenType;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandStyleOption;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;

public interface DataSyncHandler {

    ArmorStandHolder getArmorStandHolder();

    default ArmorStand getArmorStand() {
        return this.getArmorStandHolder().getArmorStand();
    }

    void sendName(String name);

    void sendPose(ArmorStandPose pose);

    default void sendPose(ArmorStandPose pose, boolean finalize) {
        this.sendPose(pose);
    }

    void sendPosition(double posX, double posY, double posZ);

    default void sendPosition(double posX, double posY, double posZ, boolean finalize) {
        this.sendPosition(posX, posY, posZ);
    }

    void sendRotation(float rotation);

    default void sendRotation(float rotation, boolean finalize) {
        this.sendRotation(rotation);
    }

    void sendStyleOption(ArmorStandStyleOption styleOption, boolean value);

    default void sendStyleOption(ArmorStandStyleOption styleOption, boolean value, boolean finalize) {
        this.sendStyleOption(styleOption, value);
    }

    ArmorStandScreenType[] tabs();

    default boolean supportsScreenType(ArmorStandScreenType screenType) {
        return true;
    }

    default void tick() {

    }

    default boolean shouldContinueTicking() {
        return false;
    }

    default void finalizeCurrentOperation() {

    }

    static void setCustomArmorStandName(ArmorStand armorStand, String name) {
        name = SharedConstants.filterText(name);
        if (name.length() <= 50) {
            boolean remove = name.isBlank() || name.equals(EntityType.ARMOR_STAND.getDescription().getString());
            armorStand.setCustomName(remove ? null : Component.literal(name));
        }
    }
}
