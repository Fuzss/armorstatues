package fuzs.armorstatues.world.inventory;

import net.minecraft.world.entity.decoration.ArmorStand;

public interface ArmorStandHolder {

    ArmorStand getArmorStand();

    static ArmorStandHolder simple(ArmorStand armorStand) {
        return () -> armorStand;
    }
}
