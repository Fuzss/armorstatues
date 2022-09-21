package fuzs.armorstatues.world.inventory;

import fuzs.armorstatues.world.entity.decoration.ArmorStandDataProvider;
import net.minecraft.world.entity.decoration.ArmorStand;

public interface ArmorStandHolder {

    ArmorStand getArmorStand();

    default ArmorStandDataProvider getDataProvider() {
        return this.getArmorStand() instanceof ArmorStandDataProvider dataProvider ? dataProvider : ArmorStandDataProvider.INSTANCE;
    }

    static ArmorStandHolder simple(ArmorStand armorStand) {
        return () -> armorStand;
    }
}
