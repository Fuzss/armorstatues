package fuzs.armorstatues.client.renderer.entity;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import com.mojang.math.Vector3f;
import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.client.init.ModClientRegistry;
import fuzs.armorstatues.client.model.PlayerStatueModel;
import fuzs.armorstatues.client.renderer.entity.layers.PlayerStatueCapeLayer;
import fuzs.armorstatues.client.renderer.entity.layers.PlayerStatueDeadmau5EarsLayer;
import fuzs.armorstatues.world.entity.decoration.PlayerStatue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.PlayerModelPart;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class PlayerStatueRenderer extends LivingEntityRenderer<ArmorStand, PlayerStatueModel> {
    private static final ResourceLocation STRAW_STATUE_LOCATION = new ResourceLocation(ArmorStatues.MOD_ID, "textures/entity/straw_statue.png");

    public PlayerStatueRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerStatueModel(context.bakeLayer(ModClientRegistry.PLAYER_STATUE), false), 0.0F);
        this.addLayer(new HumanoidArmorLayer<>(this, new ArmorStandArmorModel(context.bakeLayer(ModClientRegistry.PLAYER_STATUE_INNER_ARMOR)), new ArmorStandArmorModel(context.bakeLayer(ModClientRegistry.PLAYER_STATUE_OUTER_ARMOR))));
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new ElytraLayer<>(this, context.getModelSet()));
        this.addLayer(new PlayerStatueDeadmau5EarsLayer(this));
        this.addLayer(new PlayerStatueCapeLayer(this));
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
    }

    @Override
    public void render(ArmorStand entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        if (entity instanceof PlayerStatue playerStatue) {
            this.setModelProperties(playerStatue);
        }
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    private void setModelProperties(PlayerStatue clientPlayer) {
        PlayerStatueModel playerModel = this.getModel();
        playerModel.setAllVisible(true);
        playerModel.hat.visible = clientPlayer.isModelPartShown(PlayerModelPart.HAT);
        playerModel.jacket.visible = clientPlayer.isModelPartShown(PlayerModelPart.JACKET);
        playerModel.leftPants.visible = clientPlayer.isModelPartShown(PlayerModelPart.LEFT_PANTS_LEG);
        playerModel.rightPants.visible = clientPlayer.isModelPartShown(PlayerModelPart.RIGHT_PANTS_LEG);
        playerModel.leftSleeve.visible = clientPlayer.isModelPartShown(PlayerModelPart.LEFT_SLEEVE);
        playerModel.rightSleeve.visible = clientPlayer.isModelPartShown(PlayerModelPart.RIGHT_SLEEVE);
    }

    @Override
    public ResourceLocation getTextureLocation(ArmorStand entity) {
        Either<ResourceLocation, ResourceLocation> texture = getPlayerProfileTexture(entity, MinecraftProfileTexture.Type.SKIN);
        return texture.left().orElseGet(texture.right()::get);
    }

    public static Either<ResourceLocation, ResourceLocation> getPlayerProfileTexture(Entity entity, MinecraftProfileTexture.Type type) {
        if (entity instanceof PlayerStatue playerStatue) {
            GameProfile gameProfile = playerStatue.getOwner();
            if (gameProfile != null) {
                Minecraft minecraft = Minecraft.getInstance();
                Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.getSkinManager().getInsecureSkinInformation(gameProfile);
                return map.containsKey(type) ? Either.left(minecraft.getSkinManager().registerTexture(map.get(type), type)) : Either.right(STRAW_STATUE_LOCATION);
            }
        }
        return Either.right(STRAW_STATUE_LOCATION);
    }

    @Override
    protected void scale(ArmorStand livingEntity, PoseStack matrixStack, float partialTickTime) {
        matrixStack.scale(0.9375F, 0.9375F, 0.9375F);
    }

    @Override
    protected void setupRotations(ArmorStand entityLiving, PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - rotationYaw));
        float f = (float)(entityLiving.level.getGameTime() - entityLiving.lastHit) + partialTicks;
        if (f < 5.0F) {
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.sin(f / 1.5F * 3.1415927F) * 3.0F));
        }

    }

    @Override
    protected boolean shouldShowName(ArmorStand entity) {
        double d = this.entityRenderDispatcher.distanceToSqr(entity);
        float f = entity.isCrouching() ? 32.0F : 64.0F;
        return d >= (double)(f * f) ? false : entity.isCustomNameVisible();
    }

    @Override
    @Nullable
    protected RenderType getRenderType(ArmorStand livingEntity, boolean bodyVisible, boolean translucent, boolean glowing) {
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
