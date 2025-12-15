package fuzs.armorstatues.init;

import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.world.entity.decoration.ArmorStandStatueImpl;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import fuzs.statuemenus.api.v1.world.inventory.StatueMenu;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

public class ModRegistry {
    static final RegistryManager REGISTRIES = RegistryManager.from(ArmorStatues.MOD_ID);
    public static final Holder.Reference<MenuType<StatueMenu>> ARMOR_STAND_MENU_TYPE = REGISTRIES.registerMenuType(
            "armor_stand",
            (int containerId, Inventory inventory, StatueMenu.Data data) -> {
                return new <ArmorStand>StatueMenu(ModRegistry.ARMOR_STAND_MENU_TYPE.value(),
                        containerId,
                        inventory,
                        data,
                        ArmorStandStatueImpl::new);
            },
            StatueMenu.Data.STREAM_CODEC);

    public static void bootstrap() {
        // NO-OP
    }
}
