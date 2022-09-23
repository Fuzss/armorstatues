package fuzs.armorstatues.client;

import fuzs.armorstatues.api.client.gui.screens.armorstand.ArmorStandScreenFactory;
import fuzs.armorstatues.api.network.client.data.NetworkDataSyncHandler;
import fuzs.armorstatues.api.world.inventory.ArmorStandMenu;
import fuzs.armorstatues.init.ModRegistry;
import fuzs.puzzleslib.client.core.ClientModConstructor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ArmorStatuesClient implements ClientModConstructor {

    @Override
    public void onRegisterMenuScreens(MenuScreensContext context) {
        // don't use method reference, it will ignore any changes made to ArmorStandScreenType::getLastType
        context.registerMenuScreen(ModRegistry.ARMOR_STAND_MENU_TYPE.get(), (ArmorStandMenu menu, Inventory inventory, Component component) -> ArmorStandScreenFactory.createLastScreenType(menu, inventory, component, new NetworkDataSyncHandler(menu.getArmorStand())));
    }
}
