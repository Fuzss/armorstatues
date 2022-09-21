package fuzs.armorstatues.client;

import fuzs.armorstatues.client.gui.screens.armorstand.*;
import fuzs.armorstatues.client.init.ModClientRegistry;
import fuzs.armorstatues.client.renderer.entity.StrawStatueRenderer;
import fuzs.armorstatues.init.ModRegistry;
import fuzs.armorstatues.network.client.data.NetworkDataSyncHandler;
import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import fuzs.armorstatues.world.inventory.ArmorStandScreenType;
import fuzs.puzzleslib.client.core.ClientModConstructor;
import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;

public class ArmorStatuesClient implements ClientModConstructor {

    @Override
    public void onClientSetup() {
        ArmorStandScreenFactory.register(ArmorStandScreenType.EQUIPMENT, ArmorStandEquipmentScreen::new);
        ArmorStandScreenFactory.register(ArmorStandScreenType.ROTATIONS, ArmorStandRotationsScreen::new);
        ArmorStandScreenFactory.register(ArmorStandScreenType.STYLE, ArmorStandStyleScreen::new);
        ArmorStandScreenFactory.register(ArmorStandScreenType.POSES, ArmorStandPosesScreen::new);
        ArmorStandScreenFactory.register(ArmorStandScreenType.POSITION, ArmorStandPositionScreen::new);
        ArmorStandScreenFactory.register(ArmorStandScreenType.ALIGNMENTS, ArmorStandAlignmentsScreen::new);
        ArmorStandScreenFactory.register(ArmorStandScreenType.MODEL_PARTS, StrawStatueModelPartsScreen::new);
        ArmorStandScreenFactory.register(ArmorStandScreenType.STRAW_STATUE_STYLE, StrawStatueStyleScreen::new);
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

    @Override
    public void onRegisterEntityRenderers(EntityRenderersContext context) {
        context.registerEntityRenderer(ModRegistry.STRAW_STATUE_ENTITY_TYPE.get(), (EntityRendererProvider.Context context1) -> new StrawStatueRenderer(context1, false));
    }

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        context.registerLayerDefinition(ModClientRegistry.STRAW_STATUE, () -> LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE, false), 64, 64));
        context.registerLayerDefinition(ModClientRegistry.STRAW_STATUE_INNER_ARMOR, () -> ArmorStandArmorModel.createBodyLayer(new CubeDeformation(0.5F)));
        context.registerLayerDefinition(ModClientRegistry.STRAW_STATUE_OUTER_ARMOR, () -> ArmorStandArmorModel.createBodyLayer(new CubeDeformation(1.0F)));
        context.registerLayerDefinition(ModClientRegistry.STRAW_STATUE_SLIM, () -> LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE, true), 64, 64));
        context.registerLayerDefinition(ModClientRegistry.STRAW_STATUE_SLIM_INNER_ARMOR, () -> ArmorStandArmorModel.createBodyLayer(new CubeDeformation(0.5F)));
        context.registerLayerDefinition(ModClientRegistry.STRAW_STATUE_SLIM_OUTER_ARMOR, () -> ArmorStandArmorModel.createBodyLayer(new CubeDeformation(1.0F)));
    }
}
