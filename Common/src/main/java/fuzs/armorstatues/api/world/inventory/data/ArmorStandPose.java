package fuzs.armorstatues.api.world.inventory.data;

import fuzs.armorstatues.mixin.accessor.ArmorStandAccessor;
import net.minecraft.Util;
import net.minecraft.core.Rotations;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class ArmorStandPose {
    public static final ArmorStandPose ATHENA = new ArmorStandPose("athena").withBodyPose(new Rotations(0.0F, 0.0F, 2.0F)).withHeadPose(new Rotations(-5.0F, 0.0F, 0.0F)).withLeftArmPose(new Rotations(10.0F, 0.0F, -5.0F)).withLeftLegPose(new Rotations(-3.0F, -3.0F, -3.0F)).withRightArmPose(new Rotations(-60.0F, 20.0F, -10.0F)).withRightLegPose(new Rotations(3.0F, 3.0F, 3.0F));
    public static final ArmorStandPose BRANDISH = new ArmorStandPose("brandish").withBodyPose(new Rotations(0.0F, 0.0F, -2.0F)).withHeadPose(new Rotations(-15.0F, 0.0F, 0.0F)).withLeftArmPose(new Rotations(20.0F, 0.0F, -10.0F)).withLeftLegPose(new Rotations(5.0F, -3.0F, -3.0F)).withRightArmPose(new Rotations(-110.0F, 50.0F, 0.0F)).withRightLegPose(new Rotations(-5.0F, 3.0F, 3.0F));
    public static final ArmorStandPose CANCAN_A = new ArmorStandPose("cancanA").withBodyPose(new Rotations(0.0F, 22.0F, 0.0F)).withHeadPose(new Rotations(-5.0F, 18.0F, 0.0F)).withLeftArmPose(new Rotations(8.0F, 0.0F, -114.0F)).withLeftLegPose(new Rotations(-111.0F, 55.0F, 0.0F)).withRightArmPose(new Rotations(0.0F, 84.0F, 111.0F)).withRightLegPose(new Rotations(0.0F, 23.0F, -13.0F));
    public static final ArmorStandPose CANCAN_B = new ArmorStandPose("cancanB").withBodyPose(new Rotations(0.0F, -18.0F, 0.0F)).withHeadPose(new Rotations(-10.0F, -20.0F, 0.0F)).withLeftArmPose(new Rotations(0.0F, 0.0F, -112.0F)).withLeftLegPose(new Rotations(0.0F, 0.0F, 13.0F)).withRightArmPose(new Rotations(8.0F, 90.0F, 111.0F)).withRightLegPose(new Rotations(-119.0F, -42.0F, 0.0F));
    public static final ArmorStandPose DEFAULT = new ArmorStandPose("default").withLeftArmPose(new Rotations(-10.0F, 0.0F, -10.0F)).withLeftLegPose(new Rotations(-1.0F, 0.0F, -1.0F)).withRightArmPose(new Rotations(-15.0F, 0.0F, 10.0F)).withRightLegPose(new Rotations(1.0F, 0.0F, 1.0F));
    public static final ArmorStandPose ENTERTAIN = new ArmorStandPose("entertain").withHeadPose(new Rotations(-15.0F, 0.0F, 0.0F)).withLeftArmPose(new Rotations(-110.0F, -35.0F, 0.0F)).withLeftLegPose(new Rotations(5.0F, -3.0F, -3.0F)).withRightArmPose(new Rotations(-110.0F, 35.0F, 0.0F)).withRightLegPose(new Rotations(-5.0F, 3.0F, 3.0F));
    public static final ArmorStandPose HERO = new ArmorStandPose("hero").withBodyPose(new Rotations(0.0F, 8.0F, 0.0F)).withHeadPose(new Rotations(-4.0F, 67.0F, 0.0F)).withLeftArmPose(new Rotations(16.0F, 32.0F, -8.0F)).withLeftLegPose(new Rotations(0.0F, -75.0F, -8.0F)).withRightArmPose(new Rotations(-99.0F, 63.0F, 0.0F)).withRightLegPose(new Rotations(4.0F, 63.0F, 8.0F));
    public static final ArmorStandPose HONOR = new ArmorStandPose("honor").withHeadPose(new Rotations(-15.0F, 0.0F, 0.0F)).withLeftArmPose(new Rotations(-110.0F, 35.0F, 0.0F)).withLeftLegPose(new Rotations(5.0F, -3.0F, -3.0F)).withRightArmPose(new Rotations(-110.0F, -35.0F, 0.0F)).withRightLegPose(new Rotations(-5.0F, 3.0F, 3.0F));
    public static final ArmorStandPose RIPOSTE = new ArmorStandPose("riposte").withHeadPose(new Rotations(16.0F, 20.0F, 0.0F)).withLeftArmPose(new Rotations(4.0F, 8.0F, 237.0F)).withLeftLegPose(new Rotations(-14.0F, -18.0F, -16.0F)).withRightArmPose(new Rotations(246.0F, 0.0F, 89.0F)).withRightLegPose(new Rotations(8.0F, 20.0F, 4.0F));
    public static final ArmorStandPose SALUTE = new ArmorStandPose("salute").withLeftArmPose(new Rotations(10.0F, 0.0F, -5.0F)).withLeftLegPose(new Rotations(-1.0F, 0.0F, -1.0F)).withRightArmPose(new Rotations(-70.0F, -40.0F, 0.0F)).withRightLegPose(new Rotations(1.0F, 0.0F, 1.0F));
    public static final ArmorStandPose SOLEMN = new ArmorStandPose("solemn").withBodyPose(new Rotations(0.0F, 0.0F, 2.0F)).withHeadPose(new Rotations(15.0F, 0.0F, 0.0F)).withLeftArmPose(new Rotations(-30.0F, 15.0F, 15.0F)).withLeftLegPose(new Rotations(-1.0F, 0.0F, -1.0F)).withRightArmPose(new Rotations(-60.0F, -20.0F, -10.0F)).withRightLegPose(new Rotations(1.0F, 0.0F, 1.0F));
    public static final ArmorStandPose ZOMBIE = new ArmorStandPose("zombie").withHeadPose(new Rotations(-10.0F, 0.0F, -5.0F)).withLeftArmPose(new Rotations(-105.0F, 0.0F, 0.0F)).withLeftLegPose(new Rotations(7.0F, 0.0F, 0.0F)).withRightArmPose(new Rotations(-100.0F, 0.0F, 0.0F)).withRightLegPose(new Rotations(-46.0F, 0.0F, 0.0F));
    public static final double DEGREES_SNAP_INTERVAL = 0.125;
    public static final DecimalFormat ROTATION_FORMAT = Util.make(new DecimalFormat("#.##"), (decimalFormat) -> {
        decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
    });
    private static final Random RANDOM = new Random();
    
    @Nullable
    private final String name;
    private final Rotations headPose;
    private final Rotations bodyPose;
    private final Rotations leftArmPose;
    private final Rotations rightArmPose;
    private final Rotations leftLegPose;
    private final Rotations rightLegPose;

    private ArmorStandPose(@Nullable String name) {
        this(name, new Rotations(0.0F, 0.0F, 0.0F), new Rotations(0.0F, 0.0F, 0.0F), new Rotations(0.0F, 0.0F, 0.0F), new Rotations(0.0F, 0.0F, 0.0F), new Rotations(0.0F, 0.0F, 0.0F), new Rotations(0.0F, 0.0F, 0.0F));
    }

    private ArmorStandPose(@Nullable String name, Rotations headPose, Rotations bodyPose, Rotations leftArmPose, Rotations rightArmPose, Rotations leftLegPose, Rotations rightLegPose) {
        this.name = name;
        this.headPose = headPose;
        this.bodyPose = bodyPose;
        this.leftArmPose = leftArmPose;
        this.rightArmPose = rightArmPose;
        this.leftLegPose = leftLegPose;
        this.rightLegPose = rightLegPose;
    }
    
    public static ArmorStandPose empty() {
        return new ArmorStandPose(null);
    }

    @Override
    public String toString() {
        return this.name != null ? this.name.toUpperCase(Locale.ROOT) : "POSE";
    }

    public String getTranslationKey() {
        Objects.requireNonNull(this.name, "name is null");
        return "armorstatues.entity.armor_stand.pose." + this.name;
    }

    public Rotations getHeadPose() {
        return this.headPose;
    }

    public Rotations getBodyPose() {
        return this.bodyPose;
    }

    public Rotations getLeftArmPose() {
        return this.leftArmPose;
    }

    public Rotations getRightArmPose() {
        return this.rightArmPose;
    }

    public Rotations getLeftLegPose() {
        return this.leftLegPose;
    }

    public Rotations getRightLegPose() {
        return this.rightLegPose;
    }

    public ArmorStandPose withHeadPose(Rotations rotation) {
        return new ArmorStandPose(this.name, rotation, this.bodyPose, this.leftArmPose, this.rightArmPose, this.leftLegPose, this.rightLegPose);
    }

    public ArmorStandPose withBodyPose(Rotations rotation) {
        return new ArmorStandPose(this.name, this.headPose, rotation, this.leftArmPose, this.rightArmPose, this.leftLegPose, this.rightLegPose);
    }

    public ArmorStandPose withLeftArmPose(Rotations rotation) {
        return new ArmorStandPose(this.name, this.headPose, this.bodyPose, rotation, this.rightArmPose, this.leftLegPose, this.rightLegPose);
    }

    public ArmorStandPose withRightArmPose(Rotations rotation) {
        return new ArmorStandPose(this.name, this.headPose, this.bodyPose, this.leftArmPose, rotation, this.leftLegPose, this.rightLegPose);
    }

    public ArmorStandPose withLeftLegPose(Rotations rotation) {
        return new ArmorStandPose(this.name, this.headPose, this.bodyPose, this.leftArmPose, this.rightArmPose, rotation, this.rightLegPose);
    }

    public ArmorStandPose withRightLegPose(Rotations rotation) {
        return new ArmorStandPose(this.name, this.headPose, this.bodyPose, this.leftArmPose, this.rightArmPose, this.leftLegPose, rotation);
    }

    public ArmorStandPose mirror() {
        return new ArmorStandPose(this.name, mirrorRotation(this.headPose), mirrorRotation(this.bodyPose), mirrorRotation(this.rightArmPose), mirrorRotation(this.leftArmPose), mirrorRotation(this.rightLegPose), mirrorRotation(this.leftLegPose));
    }

    private static Rotations mirrorRotation(Rotations rotations) {
        return new Rotations(rotations.getX(), -rotations.getY(), -rotations.getZ());
    }

    public void applyToEntity(ArmorStand armorStand) {
        armorStand.setHeadPose(this.headPose);
        armorStand.setBodyPose(this.bodyPose);
        armorStand.setLeftArmPose(this.leftArmPose);
        armorStand.setRightArmPose(this.rightArmPose);
        armorStand.setLeftLegPose(this.leftLegPose);
        armorStand.setRightLegPose(this.rightLegPose);
    }

    public void serializeAllPoses(CompoundTag tag) {
        this.serializeBodyPoses(tag, null);
        this.serializeArmPoses(tag, null);
        this.serializeLegPoses(tag, null);
    }

    public boolean serializeBodyPoses(CompoundTag tag, @Nullable ArmorStandPose lastSentPose) {
        if (lastSentPose == null || !this.headPose.equals(lastSentPose.headPose) || !this.bodyPose.equals(lastSentPose.bodyPose)) {
            tag.put("Head", this.headPose.save());
            tag.put("Body", this.bodyPose.save());
            return true;
        }
        return false;
    }

    public boolean serializeArmPoses(CompoundTag tag, @Nullable ArmorStandPose lastSentPose) {
        if (lastSentPose == null || !this.leftArmPose.equals(lastSentPose.leftArmPose) || !this.rightArmPose.equals(lastSentPose.rightArmPose)) {
            tag.put("LeftArm", this.leftArmPose.save());
            tag.put("RightArm", this.rightArmPose.save());
            return true;
        }
        return false;
    }

    public boolean serializeLegPoses(CompoundTag tag, @Nullable ArmorStandPose lastSentPose) {
        if (lastSentPose == null || !this.leftLegPose.equals(lastSentPose.leftLegPose) || !this.rightLegPose.equals(lastSentPose.rightLegPose)) {
            tag.put("LeftLeg", this.leftLegPose.save());
            tag.put("RightLeg", this.rightLegPose.save());
            return true;
        }
        return false;
    }

    public static ArmorStandPose fromEntity(ArmorStand armorStand) {
        return new ArmorStandPose(null, armorStand.getHeadPose(), armorStand.getBodyPose(), armorStand.getLeftArmPose(), armorStand.getRightArmPose(), armorStand.getLeftLegPose(), armorStand.getRightLegPose());
    }

    public static void applyTagToEntity(ArmorStand armorStand, CompoundTag tag) {
        ((ArmorStandAccessor) armorStand).callReadPose(tag);
    }

    public static ArmorStandPose random(PosePartMutator[] mutators, boolean clampRotations) {
        checkMutatorsSize(mutators);
        return new ArmorStandPose(null, mutators[0].randomRotations(clampRotations), mutators[1].randomRotations(clampRotations), mutators[2].randomRotations(clampRotations), mutators[3].randomRotations(clampRotations), mutators[4].randomRotations(clampRotations), mutators[5].randomRotations(clampRotations));
    }

    public static ArmorStandPose[] values() {
        return new ArmorStandPose[]{DEFAULT, SOLEMN, ATHENA, BRANDISH, HONOR, ENTERTAIN, SALUTE, HERO, RIPOSTE, ZOMBIE, CANCAN_A, CANCAN_B};
    }

    public static ArmorStandPose selectRandomPose() {
        ArmorStandPose[] values = values();
        return values[RANDOM.nextInt(values().length)];
    }

    public static void checkMutatorsSize(PosePartMutator[] mutators) {
        if (mutators.length != 6) throw new IllegalArgumentException("Invalid mutators size: Expected 6, was %s".formatted(mutators.length));
    }

    public static double snapValue(double value, double snapInterval) {
        if (snapInterval > 0.0 && snapInterval < 1.0) {
            double currentSnap = 0.0;
            while (currentSnap < 1.0) {
                double snapRegion = snapInterval * 0.1;
                if (value >= currentSnap - snapRegion && value < currentSnap + snapRegion) {
                    return currentSnap;
                }
                currentSnap += snapInterval;
            }
        }
        return value;
    }
}
