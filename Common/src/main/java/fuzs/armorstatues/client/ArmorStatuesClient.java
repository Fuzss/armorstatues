package fuzs.armorstatues.client;

import fuzs.armorstatues.client.gui.screens.inventory.ArmorStandScreen2;
import fuzs.armorstatues.init.ModRegistry;
import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import fuzs.puzzleslib.client.core.ClientModConstructor;
import net.minecraft.world.inventory.InventoryMenu;

public class ArmorStatuesClient implements ClientModConstructor {

    @Override
    public void onRegisterMenuScreens(MenuScreensContext context) {
        context.registerMenuScreen(ModRegistry.ARMOR_STAND_MENU_TYPE.get(), ArmorStandScreen2::new);
    }

    @Override
    public void onRegisterAtlasSprites(AtlasSpritesContext context) {
        context.registerAtlasSprite(InventoryMenu.BLOCK_ATLAS, ArmorStandMenu.EMPTY_ARMOR_SLOT_SWORD);
    }
}
