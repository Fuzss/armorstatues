package fuzs.armorstatues.network.client.data;

import fuzs.armorstatues.client.gui.screens.inventory.ArmorStandScreenType;
import fuzs.armorstatues.client.gui.screens.inventory.ArmorStandStyleOption;
import fuzs.armorstatues.world.inventory.ArmorStandHolder;
import fuzs.armorstatues.world.inventory.ArmorStandPose;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface DataSyncHandler extends ArmorStandHolder {

    default void sendName(String name) {
        setCustomArmorStandName(this.getArmorStand(), name);
    }

    default void sendPose(ArmorStandPose currentPose) {
        currentPose.applyToEntity(this.getArmorStand());
    }

    default void sendPosition(double posX, double posY, double posZ) {

    }

    default void sendRotation(float rotation) {

    }

    default void sendStyleOption(ArmorStandStyleOption styleOption, boolean value) {
        styleOption.setOption(this.getArmorStand(), value);
    }

    ArmorStandScreenType[] tabs();

    Optional<ArmorStandScreenType> getLastType();

    void setLastType(ArmorStandScreenType lastType);

    static void setCustomArmorStandName(ArmorStand armorStand, String name) {
        String s = SharedConstants.filterText(name);
        if (s.length() <= 50) {
            boolean remove = s.isBlank() || s.equals(EntityType.ARMOR_STAND.getDescription().getString());
            armorStand.setCustomName(remove ? null : Component.literal(s));
        }
    }
}
