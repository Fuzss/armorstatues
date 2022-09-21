package fuzs.armorstatues.client.renderer.entity;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.client.init.ModClientRegistry;
import fuzs.armorstatues.client.model.StrawStatueArmorModel;
import fuzs.armorstatues.client.model.StrawStatueModel;
import fuzs.armorstatues.client.renderer.entity.layers.StrawStatueCapeLayer;
import fuzs.armorstatues.client.renderer.entity.layers.StrawStatueDeadmau5EarsLayer;
import fuzs.armorstatues.world.entity.decoration.StrawStatue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.PlayerModelPart;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class StrawStatueRenderer extends LivingEntityRenderer<StrawStatue, StrawStatueModel<StrawStatue>> {
    public static final ResourceLocation STRAW_STATUE_LOCATION = new ResourceLocation(ArmorStatues.MOD_ID, "textures/entity/straw_statue.png");

    public StrawStatueRenderer(EntityRendererProvider.Context context, boolean slimModel) {
        super(context, new StrawStatueModel<>(context.bakeLayer(slimModel ? ModClientRegistry.STRAW_STATUE_SLIM : ModClientRegistry.STRAW_STATUE), slimModel), 0.0F);
        this.addLayer(new HumanoidArmorLayer<>(this, new StrawStatueArmorModel<>(context.bakeLayer(slimModel ? ModClientRegistry.STRAW_STATUE_SLIM_INNER_ARMOR : ModClientRegistry.STRAW_STATUE_INNER_ARMOR)), new StrawStatueArmorModel<>(context.bakeLayer(slimModel ? ModClientRegistry.STRAW_STATUE_SLIM_OUTER_ARMOR : ModClientRegistry.STRAW_STATUE_OUTER_ARMOR))));
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new ElytraLayer<>(this, context.getModelSet()));
        this.addLayer(new StrawStatueDeadmau5EarsLayer(this));
        this.addLayer(new StrawStatueCapeLayer(this));
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
    }

    @Override
    public void render(StrawStatue entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        this.setModelProperties(entity);
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    private void setModelProperties(StrawStatue entity) {
        StrawStatueModel<StrawStatue> model = this.getModel();
        model.setAllVisible(true);
        model.hat.visible = entity.isModelPartShown(PlayerModelPart.HAT);
        model.jacket.visible = entity.isModelPartShown(PlayerModelPart.JACKET);
        model.leftPants.visible = entity.isModelPartShown(PlayerModelPart.LEFT_PANTS_LEG);
        model.rightPants.visible = entity.isModelPartShown(PlayerModelPart.RIGHT_PANTS_LEG);
        model.leftSleeve.visible = entity.isModelPartShown(PlayerModelPart.LEFT_SLEEVE);
        model.rightSleeve.visible = entity.isModelPartShown(PlayerModelPart.RIGHT_SLEEVE);
    }

    @Override
    public ResourceLocation getTextureLocation(StrawStatue entity) {
        return getPlayerProfileTexture(entity, MinecraftProfileTexture.Type.SKIN).orElse(STRAW_STATUE_LOCATION);
    }

    public static Optional<ResourceLocation> getPlayerProfileTexture(StrawStatue entity, MinecraftProfileTexture.Type type) {
        GameProfile gameProfile = entity.getOwner();
        if (gameProfile != null) {
            Minecraft minecraft = Minecraft.getInstance();
            Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.getSkinManager().getInsecureSkinInformation(gameProfile);
            if (map.containsKey(type)) {
                return Optional.of(minecraft.getSkinManager().registerTexture(map.get(type), type));
            }
        }
        return Optional.empty();
    }

    @Override
    protected void scale(StrawStatue livingEntity, PoseStack matrixStack, float partialTickTime) {
        matrixStack.scale(0.9375F, 0.9375F, 0.9375F);
    }

    @Override
    protected void setupRotations(StrawStatue entityLiving, PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - rotationYaw));
        float f = (float)(entityLiving.level.getGameTime() - entityLiving.lastHit) + partialTicks;
        if (f < 5.0F) {
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.sin(f / 1.5F * 3.1415927F) * 3.0F));
        }

    }

    @Override
    protected boolean shouldShowName(StrawStatue entity) {
        double d = this.entityRenderDispatcher.distanceToSqr(entity);
        float f = entity.isCrouching() ? 32.0F : 64.0F;
        return d >= (double)(f * f) ? false : entity.isCustomNameVisible();
    }

    @Override
    @Nullable
    protected RenderType getRenderType(StrawStatue livingEntity, boolean bodyVisible, boolean translucent, boolean glowing) {
        if (!livingEntity.isMarker()) {
            return super.getRenderType(livingEntity, bodyVisible, translucent, glowing);
        } else {
            ResourceLocation resourceLocation = this.getTextureLocation(livingEntity);
            if (translucent) {
                return RenderType.entityTranslucent(resourceLocation, false);
            } else {
                return bodyVisible ? RenderType.entityCutoutNoCull(resourceLocation, false) : null;
            }
        }
    }
}
