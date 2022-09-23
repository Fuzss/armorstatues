package fuzs.strawstatues.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.strawstatues.world.entity.decoration.StrawStatue;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.decoration.ArmorStand;

import java.util.stream.StreamSupport;

public class StrawStatueModel extends PlayerModel<StrawStatue> {
    public final ModelPart slimLeftArm;
    public final ModelPart slimRightArm;
    public final ModelPart slimLeftSleeve;
    public final ModelPart slimRightSleeve;
    private final ModelPart cloak;

    private boolean slim;

    public StrawStatueModel(ModelPart modelPart, boolean slim) {
        super(modelPart, slim);
        this.slimLeftArm = modelPart.getChild("slim_left_arm");
        this.slimRightArm = modelPart.getChild("slim_right_arm");
        this.slimLeftSleeve = modelPart.getChild("slim_left_sleeve");
        this.slimRightSleeve = modelPart.getChild("slim_right_sleeve");
        this.cloak = modelPart.getChild("cloak");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = PlayerModel.createMesh(CubeDeformation.NONE, false);
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("slim_left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, CubeDeformation.NONE), PartPose.offset(5.0F, 2.5F, 0.0F));
        partDefinition.addOrReplaceChild("slim_right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, CubeDeformation.NONE), PartPose.offset(-5.0F, 2.5F, 0.0F));
        partDefinition.addOrReplaceChild("slim_left_sleeve", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, CubeDeformation.NONE.extend(0.25F)), PartPose.offset(5.0F, 2.5F, 0.0F));
        partDefinition.addOrReplaceChild("slim_right_sleeve", CubeListBuilder.create().texOffs(40, 32).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, CubeDeformation.NONE.extend(0.25F)), PartPose.offset(-5.0F, 2.5F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return Iterables.concat(super.headParts(), ImmutableList.of(this.hat));
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return StreamSupport.stream(super.bodyParts().spliterator(), false).filter(modelPart -> modelPart != this.hat).collect(ImmutableList.toImmutableList());
    }

    @Override
    public void setupAnim(StrawStatue entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        setupPoseAnim(this, entity);
        this.setupSlimAnim(entity);
        // use cloak instead of body, changing body rotations looks just weird
        this.cloak.xRot = -0.017453292F * entity.getBodyPose().getX();
        this.cloak.yRot = 0.017453292F * entity.getBodyPose().getY();
        this.cloak.zRot = -0.017453292F * entity.getBodyPose().getZ();
        this.hat.copyFrom(this.head);
        this.leftPants.copyFrom(this.leftLeg);
        this.rightPants.copyFrom(this.rightLeg);
        this.leftSleeve.copyFrom(this.leftArm);
        this.rightSleeve.copyFrom(this.rightArm);
        this.slimLeftSleeve.copyFrom(this.slimLeftArm);
        this.slimRightSleeve.copyFrom(this.slimRightArm);
        this.jacket.copyFrom(this.body);
        if (entity.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
            if (entity.isCrouching()) {
                this.cloak.z = 1.4F;
                this.cloak.y = 1.85F;
            } else {
                this.cloak.z = 0.0F;
                this.cloak.y = 0.0F;
            }
        } else if (entity.isCrouching()) {
            this.cloak.z = 0.3F;
            this.cloak.y = 0.8F;
        } else {
            this.cloak.z = -1.1F;
            this.cloak.y = -0.85F;
        }
    }

    private void setupSlimAnim(StrawStatue entity) {
        this.leftArm.visible = this.slimLeftArm.visible = true;
        this.rightArm.visible = this.slimRightArm.visible = true;
        this.slim = entity.slimArms();
        this.slimLeftArm.xRot = 0.017453292F * entity.getLeftArmPose().getX();
        this.slimLeftArm.yRot = 0.017453292F * entity.getLeftArmPose().getY();
        this.slimLeftArm.zRot = 0.017453292F * entity.getLeftArmPose().getZ();
        this.slimRightArm.xRot = 0.017453292F * entity.getRightArmPose().getX();
        this.slimRightArm.yRot = 0.017453292F * entity.getRightArmPose().getY();
        this.slimRightArm.zRot = 0.017453292F * entity.getRightArmPose().getZ();
        if (this.slim) {
            this.leftArm.visible = this.leftSleeve.visible = false;
            this.rightArm.visible = this.rightSleeve.visible = false;
        } else {
            this.slimLeftArm.visible = this.slimLeftSleeve.visible = false;
            this.slimRightArm.visible = this.slimRightSleeve.visible = false;
        }
    }

    @Override
    public void translateToHand(HumanoidArm side, PoseStack poseStack) {
        ModelPart modelPart = this.getArm(side);
        if (this.slim) {
            float f = 0.5F * (float)(side == HumanoidArm.RIGHT ? 1 : -1);
            modelPart.x += f;
            modelPart.translateAndRotate(poseStack);
            modelPart.x -= f;
        } else {
            modelPart.translateAndRotate(poseStack);
        }
    }

    public static <T extends ArmorStand> void setupPoseAnim(HumanoidModel<T> model, T entity) {
        model.head.xRot = 0.017453292F * entity.getHeadPose().getX();
        model.head.yRot = 0.017453292F * entity.getHeadPose().getY();
        model.head.zRot = 0.017453292F * entity.getHeadPose().getZ();
        model.leftArm.xRot = 0.017453292F * entity.getLeftArmPose().getX();
        model.leftArm.yRot = 0.017453292F * entity.getLeftArmPose().getY();
        model.leftArm.zRot = 0.017453292F * entity.getLeftArmPose().getZ();
        model.rightArm.xRot = 0.017453292F * entity.getRightArmPose().getX();
        model.rightArm.yRot = 0.017453292F * entity.getRightArmPose().getY();
        model.rightArm.zRot = 0.017453292F * entity.getRightArmPose().getZ();
        model.leftLeg.xRot = 0.017453292F * entity.getLeftLegPose().getX();
        model.leftLeg.yRot = 0.017453292F * entity.getLeftLegPose().getY();
        model.leftLeg.zRot = 0.017453292F * entity.getLeftLegPose().getZ();
        model.rightLeg.xRot = 0.017453292F * entity.getRightLegPose().getX();
        model.rightLeg.yRot = 0.017453292F * entity.getRightLegPose().getY();
        model.rightLeg.zRot = 0.017453292F * entity.getRightLegPose().getZ();
    }
}
