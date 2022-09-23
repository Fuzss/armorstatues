package fuzs.armorstatues.init;

import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.api.ArmorStatuesApi;
import fuzs.armorstatues.api.world.inventory.ArmorStandMenu;
import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.init.RegistryManager;
import fuzs.puzzleslib.init.RegistryReference;
import fuzs.puzzleslib.init.builder.ExtendedModMenuSupplier;
import net.minecraft.world.inventory.MenuType;

public class ModRegistry {
    private static final RegistryManager REGISTRY = CoreServices.FACTORIES.registration(ArmorStatues.MOD_ID);
    public static final RegistryReference<MenuType<ArmorStandMenu>> ARMOR_STAND_MENU_TYPE = REGISTRY.registerExtendedMenuTypeSupplier("armor_stand", () -> getArmorStandMenuTypeSupplier());

    public static void touch() {

    }

    private static ExtendedModMenuSupplier<ArmorStandMenu> getArmorStandMenuTypeSupplier() {
        return (containerId, inventory, data) -> ArmorStandMenu.create(ARMOR_STAND_MENU_TYPE.get(), containerId, inventory, data);
    }
}
