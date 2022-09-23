package fuzs.armorstatues.api.client;

import fuzs.armorstatues.api.client.gui.screens.armorstand.*;
import fuzs.armorstatues.api.world.inventory.ArmorStandMenu;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandScreenType;
import fuzs.puzzleslib.client.core.ClientModConstructor;
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
    public void onRegisterAtlasSprites(AtlasSpritesContext context) {
        context.registerAtlasSprite(InventoryMenu.BLOCK_ATLAS, ArmorStandMenu.EMPTY_ARMOR_SLOT_SWORD);
    }
}
