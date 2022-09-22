package fuzs.strawstatues.client;

import fuzs.armorstatues.api.client.gui.screens.armorstand.ArmorStandScreenFactory;
import fuzs.armorstatues.init.ModRegistry;
import fuzs.puzzleslib.client.core.ClientModConstructor;
import fuzs.strawstatues.client.gui.screens.strawstatue.StrawStatueModelPartsScreen;
import fuzs.strawstatues.client.gui.screens.strawstatue.StrawStatueStyleScreen;
import fuzs.strawstatues.client.init.ModClientRegistry;
import fuzs.strawstatues.client.renderer.entity.StrawStatueRenderer;
import fuzs.strawstatues.world.entity.decoration.StrawStatue;
import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class StrawStatuesClient implements ClientModConstructor {

    @Override
    public void onClientSetup() {
        ArmorStandScreenFactory.register(StrawStatue.MODEL_PARTS, StrawStatueModelPartsScreen::new);
        ArmorStandScreenFactory.register(StrawStatue.STRAW_STATUE_STYLE, StrawStatueStyleScreen::new);
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
