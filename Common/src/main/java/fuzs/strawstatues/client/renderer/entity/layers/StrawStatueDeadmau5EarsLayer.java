package fuzs.strawstatues.client.renderer.entity.layers;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import fuzs.strawstatues.client.model.StrawStatueModel;
import fuzs.strawstatues.client.renderer.entity.StrawStatueRenderer;
import fuzs.strawstatues.world.entity.decoration.StrawStatue;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.Optional;

public class StrawStatueDeadmau5EarsLayer extends RenderLayer<StrawStatue, StrawStatueModel<StrawStatue>> {

    public StrawStatueDeadmau5EarsLayer(RenderLayerParent<StrawStatue, StrawStatueModel<StrawStatue>> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, StrawStatue livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if ("deadmau5".equals(livingEntity.getName().getString()) && !livingEntity.isInvisible()) {
            Optional<ResourceLocation> texture = StrawStatueRenderer.getPlayerProfileTexture(livingEntity, MinecraftProfileTexture.Type.SKIN);
            if (texture.isPresent()) {
                matrixStack.pushPose();
                if (this.getParentModel().young) {
                    matrixStack.translate(0.0, 0.6875, 0.0);
                    matrixStack.scale(0.65F, 0.65F, 0.65F);
                }
                VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entitySolid(texture.get()));
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
