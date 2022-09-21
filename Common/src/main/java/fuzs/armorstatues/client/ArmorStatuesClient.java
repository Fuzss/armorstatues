package fuzs.armorstatues.client;

import fuzs.armorstatues.client.gui.screens.armorstand.data.ArmorStandScreenType;
import fuzs.armorstatues.client.init.ModClientRegistry;
import fuzs.armorstatues.client.renderer.entity.PlayerStatueRenderer;
import fuzs.armorstatues.init.ModRegistry;
import fuzs.armorstatues.network.client.data.NetworkDataSyncHandler;
import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import fuzs.puzzleslib.client.core.ClientModConstructor;
import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
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

    @Override
    public void onRegisterEntityRenderers(EntityRenderersContext context) {
        context.registerEntityRenderer(ModRegistry.PLAYER_STATUE_ENTITY_TYPE.get(), PlayerStatueRenderer::new);
    }

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        context.registerLayerDefinition(ModClientRegistry.PLAYER_STATUE, () -> LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE, false), 64, 64));
        context.registerLayerDefinition(ModClientRegistry.PLAYER_STATUE_INNER_ARMOR, () -> ArmorStandArmorModel.createBodyLayer(new CubeDeformation(0.5F)));
        context.registerLayerDefinition(ModClientRegistry.PLAYER_STATUE_OUTER_ARMOR, () -> ArmorStandArmorModel.createBodyLayer(new CubeDeformation(1.0F)));
    }
}
