package fuzs.armorstatues.client.renderer.entity.layers;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Either;
import com.mojang.math.Vector3f;
import fuzs.armorstatues.client.model.PlayerStatueModel;
import fuzs.armorstatues.client.renderer.entity.PlayerStatueRenderer;
import fuzs.armorstatues.world.entity.decoration.PlayerStatue;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class PlayerStatueCapeLayer extends RenderLayer<ArmorStand, PlayerStatueModel> {

    public PlayerStatueCapeLayer(RenderLayerParent<ArmorStand, PlayerStatueModel> pRenderer) {
        super(pRenderer);
    }

    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, ArmorStand armorStand, float limbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        Either<ResourceLocation, ResourceLocation> texture = PlayerStatueRenderer.getPlayerProfileTexture(armorStand, MinecraftProfileTexture.Type.CAPE);
        if (texture.left().isPresent() && !armorStand.isInvisible() && armorStand instanceof PlayerStatue playerStatue && playerStatue.isModelPartShown(PlayerModelPart.CAPE)) {
            ItemStack itemstack = armorStand.getItemBySlot(EquipmentSlot.CHEST);
            if (!itemstack.is(Items.ELYTRA)) {
                matrixStack.pushPose();
                if (this.getParentModel().young) {
                    matrixStack.translate(0.0, 0.75, 0.0);
                    matrixStack.scale(0.5F, 0.5F, 0.5F);
                }
                matrixStack.translate(0.0D, 0.0D, 0.125D);
                double d0 = 0.0;
                double d1 = 0.0;
                double d2 = 0.0;
                float f = armorStand.yBodyRotO + (armorStand.yBodyRot - armorStand.yBodyRotO);
                double d3 = Mth.sin(f * ((float)Math.PI / 180F));
                double d4 = -Mth.cos(f * ((float)Math.PI / 180F));
                float f1 = (float)d1 * 10.0F;
                f1 = Mth.clamp(f1, -6.0F, 32.0F);
                float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
                f2 = Mth.clamp(f2, 0.0F, 150.0F);
                float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;
                f3 = Mth.clamp(f3, -20.0F, 20.0F);
                if (f2 < 0.0F) {
                    f2 = 0.0F;
                }

                float f4 = 0.0F;
                f1 += Mth.sin(Mth.lerp(pPartialTicks, armorStand.walkDistO, armorStand.walkDist) * 6.0F) * 32.0F * f4;
                if (armorStand.isCrouching()) {
                    f1 += 25.0F;
                }

                matrixStack.mulPose(Vector3f.XP.rotationDegrees(6.0F + f2 / 2.0F + f1));
                matrixStack.mulPose(Vector3f.ZP.rotationDegrees(f3 / 2.0F));
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - f3 / 2.0F));
                VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entitySolid(texture.left().get()));
                this.getParentModel().renderCloak(matrixStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
                matrixStack.popPose();
            }
        }
    }
}
