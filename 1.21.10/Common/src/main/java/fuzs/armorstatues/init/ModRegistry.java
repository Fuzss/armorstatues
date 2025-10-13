package fuzs.armorstatues.init;

import fuzs.armorstatues.ArmorStatues;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import fuzs.statuemenus.api.v1.world.entity.decoration.ArmorStandDataProvider;
import fuzs.statuemenus.api.v1.world.inventory.ArmorStandMenu;
import fuzs.statuemenus.api.v1.world.inventory.data.ArmorStandScreenType;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ModRegistry {
    static final RegistryManager REGISTRIES = RegistryManager.from(ArmorStatues.MOD_ID);
    public static final Holder.Reference<MenuType<ArmorStandMenu>> ARMOR_STAND_MENU_TYPE = REGISTRIES.registerMenuType(
            "armor_stand",
            (int containerId, Inventory inventory, ArmorStandMenu.ArmorStandData data) -> {
                return new ArmorStandMenu(ModRegistry.ARMOR_STAND_MENU_TYPE.value(),
                        containerId,
                        inventory,
                        data,
                        ModRegistry.ARMOR_STAND_DATA_PROVIDER);
            },
            ArmorStandMenu.ArmorStandData.STREAM_CODEC);

    public static final ArmorStandScreenType POSITION_SCREEN_TYPE = new ArmorStandScreenType(ArmorStatues.id(
            "commands_compatible_position"), new ItemStack(Items.GRASS_BLOCK));
    public static final ArmorStandScreenType ALIGNMENTS_SCREEN_TYPE = new ArmorStandScreenType(ArmorStatues.id(
            "alignments"), new ItemStack(Items.DIAMOND_PICKAXE));
    public static final ArmorStandScreenType VANILLA_TWEAKS_SCREEN_TYPE = new ArmorStandScreenType(ArmorStatues.id(
            "vanilla_tweaks"), new ItemStack(Items.WRITTEN_BOOK));
    public static final ArmorStandDataProvider ARMOR_STAND_DATA_PROVIDER = new ArmorStandDataProvider() {

        @Override
        public ArmorStandScreenType[] getScreenTypes() {
            return new ArmorStandScreenType[]{
                    ArmorStandScreenType.ROTATIONS,
                    ArmorStandScreenType.POSES,
                    ArmorStandScreenType.STYLE,
                    POSITION_SCREEN_TYPE,
                    ALIGNMENTS_SCREEN_TYPE,
                    ArmorStandScreenType.EQUIPMENT
            };
        }
    };

    public static void bootstrap() {
        // NO-OP
    }
}
