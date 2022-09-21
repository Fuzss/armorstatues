package fuzs.armorstatues.client.renderer.entity.layers;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Either;
import com.mojang.math.Vector3f;
import fuzs.armorstatues.client.model.PlayerStatueModel;
import fuzs.armorstatues.client.renderer.entity.PlayerStatueRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.decoration.ArmorStand;

public class PlayerStatueDeadmau5EarsLayer extends RenderLayer<ArmorStand, PlayerStatueModel> {

    public PlayerStatueDeadmau5EarsLayer(RenderLayerParent<ArmorStand, PlayerStatueModel> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, ArmorStand livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if ("deadmau5".equals(livingEntity.getName().getString()) && !livingEntity.isInvisible()) {
            Either<ResourceLocation, ResourceLocation> texture = PlayerStatueRenderer.getPlayerProfileTexture(livingEntity, MinecraftProfileTexture.Type.SKIN);
            if (texture.left().isPresent()) {
                matrixStack.pushPose();
                if (this.getParentModel().young) {
                    matrixStack.translate(0.0, 0.6875, 0.0);
                    matrixStack.scale(0.65F, 0.65F, 0.65F);
                }
                VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entitySolid(texture.left().get()));
                int i = LivingEntityRenderer.getOverlayCoords(livingEntity, 0.0F);
                for (int j = 0; j < 2; ++j) {
                    float f = Mth.lerp(partialTicks, livingEntity.yRotO, livingEntity.getYRot()) - Mth.lerp(partialTicks, livingEntity.yBodyRotO, livingEntity.yBodyRot);
                    float g = Mth.lerp(partialTicks, livingEntity.xRotO, livingEntity.getXRot());
                    matrixStack.pushPose();
                    matrixStack.mulPose(Vector3f.YP.rotationDegrees(f));
                    matrixStack.mulPose(Vector3f.XP.rotationDegrees(g));
                    matrixStack.translate((double) (0.375F * (float) (j * 2 - 1)), 0.0, 0.0);
                    matrixStack.translate(0.0, -0.375, 0.0);
                    matrixStack.mulPose(Vector3f.XP.rotationDegrees(-g));
                    matrixStack.mulPose(Vector3f.YP.rotationDegrees(-f));
                    float h = 1.3333334F;
                    matrixStack.scale(1.3333334F, 1.3333334F, 1.3333334F);
                    this.getParentModel().renderEars(matrixStack, vertexConsumer, packedLight, i);
                    matrixStack.popPose();
                }
                matrixStack.popPose();
            }
        }
    }
}
