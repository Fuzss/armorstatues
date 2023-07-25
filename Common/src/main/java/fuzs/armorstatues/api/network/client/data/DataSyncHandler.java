package fuzs.armorstatues.api.network.client.data;

import fuzs.armorstatues.api.world.inventory.ArmorStandHolder;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandPose;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandScreenType;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandStyleOption;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;

import java.util.Optional;

public interface DataSyncHandler extends ArmorStandHolder {

    void sendName(String name);

    void sendPose(ArmorStandPose currentPose);

    void sendPosition(double posX, double posY, double posZ);

    void sendRotation(float rotation);

    void sendStyleOption(ArmorStandStyleOption styleOption, boolean value);

    ArmorStandScreenType[] tabs();

    Optional<ArmorStandScreenType> getLastType();

    void setLastType(ArmorStandScreenType lastType);

    default void tick() {

    }

    default boolean shouldContinueTicking() {
        return false;
    }

    static void setCustomArmorStandName(ArmorStand armorStand, String name) {
        name = SharedConstants.filterText(name);
        if (name.length() <= 50) {
            boolean remove = name.isBlank() || name.equals(EntityType.ARMOR_STAND.getDescription().getString());
            armorStand.setCustomName(remove ? null : Component.literal(name));
        }
    }
}
