package fuzs.armorstatues.init;

import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.api.world.inventory.ArmorStandMenu;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandScreenType;
import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.init.RegistryManager;
import fuzs.puzzleslib.init.RegistryReference;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ModRegistry {
    static final RegistryManager REGISTRY = CoreServices.FACTORIES.registration(ArmorStatues.MOD_ID);
    public static final RegistryReference<MenuType<ArmorStandMenu>> ARMOR_STAND_MENU_TYPE = REGISTRY.registerExtendedMenuTypeSupplier("armor_stand", () -> (containerId, inventory, data) -> {
        return ArmorStandMenu.create(ModRegistry.ARMOR_STAND_MENU_TYPE.get(), containerId, inventory, data);
    });
    public static final ArmorStandScreenType ALIGNMENTS = new ArmorStandScreenType("alignments", new ItemStack(Items.DIAMOND_PICKAXE));

    public static void touch() {

    }
}
