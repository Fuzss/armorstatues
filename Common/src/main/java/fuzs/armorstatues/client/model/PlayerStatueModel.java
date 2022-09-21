package fuzs.armorstatues.client.model;

import fuzs.armorstatues.world.entity.decoration.PlayerStatue;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;

public class PlayerStatueModel extends PlayerModel<ArmorStand> {
    private final ModelPart cloak;

    public PlayerStatueModel(ModelPart modelPart, boolean slim) {
        super(modelPart, slim);
        this.cloak = modelPart.getChild("cloak");
    }

    @Override
    public void setupAnim(ArmorStand entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = 0.017453292F * entity.getHeadPose().getX();
        this.head.yRot = 0.017453292F * entity.getHeadPose().getY();
        this.head.zRot = 0.017453292F * entity.getHeadPose().getZ();
        this.body.xRot = 0.017453292F * entity.getBodyPose().getX();
        this.body.yRot = 0.017453292F * entity.getBodyPose().getY();
        this.body.zRot = 0.017453292F * entity.getBodyPose().getZ();
        this.leftArm.xRot = 0.017453292F * entity.getLeftArmPose().getX();
        this.leftArm.yRot = 0.017453292F * entity.getLeftArmPose().getY();
        this.leftArm.zRot = 0.017453292F * entity.getLeftArmPose().getZ();
        this.rightArm.xRot = 0.017453292F * entity.getRightArmPose().getX();
        this.rightArm.yRot = 0.017453292F * entity.getRightArmPose().getY();
        this.rightArm.zRot = 0.017453292F * entity.getRightArmPose().getZ();
        this.leftLeg.xRot = 0.017453292F * entity.getLeftLegPose().getX();
        this.leftLeg.yRot = 0.017453292F * entity.getLeftLegPose().getY();
        this.leftLeg.zRot = 0.017453292F * entity.getLeftLegPose().getZ();
        this.rightLeg.xRot = 0.017453292F * entity.getRightLegPose().getX();
        this.rightLeg.yRot = 0.017453292F * entity.getRightLegPose().getY();
        this.rightLeg.zRot = 0.017453292F * entity.getRightLegPose().getZ();
        this.hat.copyFrom(this.head);
        this.leftPants.copyFrom(this.leftLeg);
        this.rightPants.copyFrom(this.rightLeg);
        this.leftSleeve.copyFrom(this.leftArm);
        this.rightSleeve.copyFrom(this.rightArm);
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
}
