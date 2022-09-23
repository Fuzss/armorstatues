package fuzs.strawstatues.client;

import fuzs.armorstatues.api.client.gui.screens.armorstand.ArmorStandScreenFactory;
import fuzs.puzzleslib.client.core.ClientModConstructor;
import fuzs.strawstatues.client.gui.screens.strawstatue.StrawStatueModelPartsScreen;
import fuzs.strawstatues.client.gui.screens.strawstatue.StrawStatueStyleScreen;
import fuzs.strawstatues.client.init.ModClientRegistry;
import fuzs.strawstatues.client.model.StrawStatueModel;
import fuzs.strawstatues.client.renderer.entity.StrawStatueRenderer;
import fuzs.strawstatues.init.ModRegistry;
import fuzs.strawstatues.world.entity.decoration.StrawStatue;
import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;

public class StrawStatuesClient implements ClientModConstructor {

    @Override
    public void onClientSetup() {
        ArmorStandScreenFactory.register(StrawStatue.MODEL_PARTS_SCREEN_TYPE, StrawStatueModelPartsScreen::new);
        ArmorStandScreenFactory.register(StrawStatue.STRAW_STATUE_STYLE_SCREEN_TYPE, StrawStatueStyleScreen::new);
    }

    @Override
    public void onRegisterEntityRenderers(EntityRenderersContext context) {
        context.registerEntityRenderer(ModRegistry.STRAW_STATUE_ENTITY_TYPE.get(), StrawStatueRenderer::new);
    }

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        context.registerLayerDefinition(ModClientRegistry.STRAW_STATUE, StrawStatueModel::createBodyLayer);
        context.registerLayerDefinition(ModClientRegistry.STRAW_STATUE_INNER_ARMOR, () -> ArmorStandArmorModel.createBodyLayer(new CubeDeformation(0.5F)));
        context.registerLayerDefinition(ModClientRegistry.STRAW_STATUE_OUTER_ARMOR, () -> ArmorStandArmorModel.createBodyLayer(new CubeDeformation(1.0F)));
    }
}
