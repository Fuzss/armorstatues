package fuzs.armorstatues.client;

import fuzs.armorstatues.api.client.gui.screens.armorstand.ArmorStandScreenFactory;
import fuzs.armorstatues.api.client.init.ModClientRegistry;
import fuzs.armorstatues.api.world.inventory.ArmorStandMenu;
import fuzs.armorstatues.init.ModRegistry;
import fuzs.puzzleslib.client.core.ClientModConstructor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ArmorStatuesClient implements ClientModConstructor {

    @Override
    public void onRegisterMenuScreens(MenuScreensContext context) {
        // compiler doesn't like method reference :(
        context.registerMenuScreen(ModRegistry.ARMOR_STAND_MENU_TYPE.get(), (ArmorStandMenu menu, Inventory inventory, Component component) -> {
            return ArmorStandScreenFactory.createLastScreenType(menu, inventory, component);
        });
    }

    @Override
    public void onRegisterKeyMappings(KeyMappingsContext context) {
        context.registerKeyMappings(ModClientRegistry.CYCLE_TABS_KEY_MAPPING);
    }
}
