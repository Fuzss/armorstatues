package fuzs.armorstatues.client;

import fuzs.armorstatues.client.gui.screens.inventory.ArmorStandScreenType;
import fuzs.armorstatues.init.ModRegistry;
import fuzs.armorstatues.network.client.data.NetworkDataSyncHandler;
import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import fuzs.puzzleslib.client.core.ClientModConstructor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;

public class ArmorStatuesClient implements ClientModConstructor {

    @Override
    public void onRegisterMenuScreens(MenuScreensContext context) {
        // don't use method reference, it will ignore any changes made to ArmorStandScreenType::getLastType
        context.registerMenuScreen(ModRegistry.ARMOR_STAND_MENU_TYPE.get(), (ArmorStandMenu menu, Inventory inventory, Component component) -> ArmorStandScreenType.createLastScreenType(menu, inventory, component, new NetworkDataSyncHandler(menu.getArmorStand())));
    }

    @Override
    public void onRegisterAtlasSprites(AtlasSpritesContext context) {
        context.registerAtlasSprite(InventoryMenu.BLOCK_ATLAS, ArmorStandMenu.EMPTY_ARMOR_SLOT_SWORD);
    }
}
