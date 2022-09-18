package fuzs.armorstatues.world.inventory;

import net.minecraft.core.Rotations;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.decoration.ArmorStand;

public enum ArmorStandPose {
    ATHENA("athena", builder().bodyPose(new Rotations(0.0F, 0.0F, 2.0F)).headPose(new Rotations(-5.0F, 0.0F, 0.0F)).leftArmPose(new Rotations(10.0F, 0.0F, -5.0F)).leftLegPose(new Rotations(-3.0F, -3.0F, -3.0F)).rightArmPose(new Rotations(-60.0F, 20.0F, -10.0F)).rightLegPose(new Rotations(3.0F, 3.0F, 3.0F))),
    BRANDISH("brandish", builder().bodyPose(new Rotations(0.0F, 0.0F, -2.0F)).headPose(new Rotations(-15.0F, 0.0F, 0.0F)).leftArmPose(new Rotations(20.0F, 0.0F, -10.0F)).leftLegPose(new Rotations(5.0F, -3.0F, -3.0F)).rightArmPose(new Rotations(-110.0F, 50.0F, 0.0F)).rightLegPose(new Rotations(-5.0F, 3.0F, 3.0F))),
    CANCAN_A("cancanA", builder().bodyPose(new Rotations(0.0F, 22.0F, 0.0F)).headPose(new Rotations(-5.0F, 18.0F, 0.0F)).leftArmPose(new Rotations(8.0F, 0.0F, -114.0F)).leftLegPose(new Rotations(-111.0F, 55.0F, 0.0F)).rightArmPose(new Rotations(0.0F, 84.0F, 111.0F)).rightLegPose(new Rotations(0.0F, 23.0F, -13.0F)));
    
    private final String name;
    private final Rotations headPose;
    private final Rotations bodyPose;
    private final Rotations leftArmPose;
    private final Rotations rightArmPose;
    private final Rotations leftLegPose;
    private final Rotations rightLegPose;

    ArmorStandPose(String name, Builder builder) {
        this(name, builder.headPose, builder.bodyPose, builder.leftArmPose, builder.rightArmPose, builder.leftLegPose, builder.rightLegPose);
    }

    ArmorStandPose(String name, Rotations headPose, Rotations bodyPose, Rotations leftArmPose, Rotations rightArmPose, Rotations leftLegPose, Rotations rightLegPose) {
        this.name = name;
        this.headPose = headPose;
        this.bodyPose = bodyPose;
        this.leftArmPose = leftArmPose;
        this.rightArmPose = rightArmPose;
        this.leftLegPose = leftLegPose;
        this.rightLegPose = rightLegPose;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public Component getComponent() {
        return Component.translatable("armorstatues.armor_stand.pose." + this.name);
    }

    public void apply(ArmorStand armorStand) {
        armorStand.setHeadPose(this.headPose);
        armorStand.setBodyPose(this.bodyPose);
        armorStand.setLeftArmPose(this.leftArmPose);
        armorStand.setRightArmPose(this.rightArmPose);
        armorStand.setLeftLegPose(this.leftLegPose);
        armorStand.setRightLegPose(this.rightLegPose);
    }

    private static Builder builder() {
        return new Builder();
    }

    private static class Builder {
        Rotations headPose = new Rotations(0.0F, 0.0F, 0.0F);
        Rotations bodyPose = new Rotations(0.0F, 0.0F, 0.0F);
        Rotations leftArmPose = new Rotations(0.0F, 0.0F, 0.0F);
        Rotations rightArmPose = new Rotations(0.0F, 0.0F, 0.0F);
        Rotations leftLegPose = new Rotations(0.0F, 0.0F, 0.0F);
        Rotations rightLegPose = new Rotations(0.0F, 0.0F, 0.0F);

        public Builder headPose(Rotations headPose) {
            this.headPose = headPose;
            return this;
        }

        public Builder bodyPose(Rotations bodyPose) {
            this.bodyPose = bodyPose;
            return this;
        }

        public Builder leftArmPose(Rotations leftArmPose) {
            this.leftArmPose = leftArmPose;
            return this;
        }

        public Builder rightArmPose(Rotations rightArmPose) {
            this.rightArmPose = rightArmPose;
            return this;
        }

        public Builder leftLegPose(Rotations leftLegPose) {
            this.leftLegPose = leftLegPose;
            return this;
        }

        public Builder rightLegPose(Rotations rightLegPose) {
            this.rightLegPose = rightLegPose;
            return this;
        }
    }
}
