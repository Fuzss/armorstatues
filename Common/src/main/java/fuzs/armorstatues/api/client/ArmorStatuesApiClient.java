package fuzs.armorstatues.api.client;

import fuzs.armorstatues.api.client.gui.screens.armorstand.*;
import fuzs.armorstatues.init.ModRegistry;
import fuzs.armorstatues.api.network.client.data.NetworkDataSyncHandler;
import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import fuzs.armorstatues.world.inventory.ArmorStandScreenType;
import fuzs.puzzleslib.client.core.ClientModConstructor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;

public class ArmorStatuesApiClient implements ClientModConstructor {

    @Override
    public void onClientSetup() {
        ArmorStandScreenFactory.register(ArmorStandScreenType.EQUIPMENT, ArmorStandEquipmentScreen::new);
        ArmorStandScreenFactory.register(ArmorStandScreenType.ROTATIONS, ArmorStandRotationsScreen::new);
        ArmorStandScreenFactory.register(ArmorStandScreenType.STYLE, ArmorStandStyleScreen::new);
        ArmorStandScreenFactory.register(ArmorStandScreenType.POSES, ArmorStandPosesScreen::new);
        ArmorStandScreenFactory.register(ArmorStandScreenType.POSITION, ArmorStandPositionScreen::new);
        ArmorStandScreenFactory.register(ArmorStandScreenType.ALIGNMENTS, ArmorStandAlignmentsScreen::new);
    }

    @Override
    public void onRegisterMenuScreens(MenuScreensContext context) {
        // don't use method reference, it will ignore any changes made to ArmorStandScreenType::getLastType
        context.registerMenuScreen(ModRegistry.ARMOR_STAND_MENU_TYPE.get(), (ArmorStandMenu menu, Inventory inventory, Component component) -> ArmorStandScreenFactory.createLastScreenType(menu, inventory, component, new NetworkDataSyncHandler(menu.getArmorStand())));
    }

    @Override
    public void onRegisterAtlasSprites(AtlasSpritesContext context) {
        context.registerAtlasSprite(InventoryMenu.BLOCK_ATLAS, ArmorStandMenu.EMPTY_ARMOR_SLOT_SWORD);
    }
}
