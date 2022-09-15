package fuzs.armorstatues.init;

import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.init.RegistryManager;
import fuzs.puzzleslib.init.RegistryReference;
import net.minecraft.world.inventory.MenuType;

public class ModRegistry {
    private static final RegistryManager REGISTRY = CoreServices.FACTORIES.registration(ArmorStatues.MOD_ID);
    public static final RegistryReference<MenuType<ArmorStandMenu>> ARMOR_STAND_MENU_TYPE = REGISTRY.registerExtendedMenuTypeSupplier("armor_stand", () -> ArmorStandMenu::new);

    public static void touch() {

    }
}
