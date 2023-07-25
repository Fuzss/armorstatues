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

public class ArmorStandPose {
    private static final String MINECRAFT_SOURCE = "Minecraft";
    private static final String VANILLA_TWEAKS_SOURCE = "Vanilla Tweaks";
    public static final double DEGREES_SNAP_INTERVAL = 0.125;
    public static final DecimalFormat ROTATION_FORMAT = Util.make(new DecimalFormat("#.##"), (decimalFormat) -> {
        decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
    });
    public static final ArmorStandPose ATHENA = new ArmorStandPose("athena", MINECRAFT_SOURCE).withBodyPose(new Rotations(0.0F, 0.0F, 2.0F)).withHeadPose(new Rotations(-5.0F, 0.0F, 0.0F)).withLeftArmPose(new Rotations(10.0F, 0.0F, -5.0F)).withLeftLegPose(new Rotations(-3.0F, -3.0F, -3.0F)).withRightArmPose(new Rotations(-60.0F, 20.0F, -10.0F)).withRightLegPose(new Rotations(3.0F, 3.0F, 3.0F));
    public static final ArmorStandPose BRANDISH = new ArmorStandPose("brandish", MINECRAFT_SOURCE).withBodyPose(new Rotations(0.0F, 0.0F, -2.0F)).withHeadPose(new Rotations(-15.0F, 0.0F, 0.0F)).withLeftArmPose(new Rotations(20.0F, 0.0F, -10.0F)).withLeftLegPose(new Rotations(5.0F, -3.0F, -3.0F)).withRightArmPose(new Rotations(-110.0F, 50.0F, 0.0F)).withRightLegPose(new Rotations(-5.0F, 3.0F, 3.0F));
    public static final ArmorStandPose CANCAN = new ArmorStandPose("cancan", MINECRAFT_SOURCE).withBodyPose(new Rotations(0.0F, 22.0F, 0.0F)).withHeadPose(new Rotations(-5.0F, 18.0F, 0.0F)).withLeftArmPose(new Rotations(8.0F, 0.0F, -114.0F)).withLeftLegPose(new Rotations(-111.0F, 55.0F, 0.0F)).withRightArmPose(new Rotations(0.0F, 84.0F, 111.0F)).withRightLegPose(new Rotations(0.0F, 23.0F, -13.0F));
    public static final ArmorStandPose DEFAULT = new ArmorStandPose("default", MINECRAFT_SOURCE).withLeftArmPose(new Rotations(-10.0F, 0.0F, -10.0F)).withLeftLegPose(new Rotations(-1.0F, 0.0F, -1.0F)).withRightArmPose(new Rotations(-15.0F, 0.0F, 10.0F)).withRightLegPose(new Rotations(1.0F, 0.0F, 1.0F));
    public static final ArmorStandPose ENTERTAIN = new ArmorStandPose("entertain", MINECRAFT_SOURCE).withHeadPose(new Rotations(-15.0F, 0.0F, 0.0F)).withLeftArmPose(new Rotations(-110.0F, -35.0F, 0.0F)).withLeftLegPose(new Rotations(5.0F, -3.0F, -3.0F)).withRightArmPose(new Rotations(-110.0F, 35.0F, 0.0F)).withRightLegPose(new Rotations(-5.0F, 3.0F, 3.0F));
    public static final ArmorStandPose HERO = new ArmorStandPose("hero", MINECRAFT_SOURCE).withBodyPose(new Rotations(0.0F, 8.0F, 0.0F)).withHeadPose(new Rotations(-4.0F, 67.0F, 0.0F)).withLeftArmPose(new Rotations(16.0F, 32.0F, -8.0F)).withLeftLegPose(new Rotations(0.0F, -75.0F, -8.0F)).withRightArmPose(new Rotations(-99.0F, 63.0F, 0.0F)).withRightLegPose(new Rotations(4.0F, 63.0F, 8.0F));
    public static final ArmorStandPose HONOR = new ArmorStandPose("honor", MINECRAFT_SOURCE).withHeadPose(new Rotations(-15.0F, 0.0F, 0.0F)).withLeftArmPose(new Rotations(-110.0F, 35.0F, 0.0F)).withLeftLegPose(new Rotations(5.0F, -3.0F, -3.0F)).withRightArmPose(new Rotations(-110.0F, -35.0F, 0.0F)).withRightLegPose(new Rotations(-5.0F, 3.0F, 3.0F));
    public static final ArmorStandPose RIPOSTE = new ArmorStandPose("riposte", MINECRAFT_SOURCE).withHeadPose(new Rotations(16.0F, 20.0F, 0.0F)).withLeftArmPose(new Rotations(4.0F, 8.0F, 237.0F)).withLeftLegPose(new Rotations(-14.0F, -18.0F, -16.0F)).withRightArmPose(new Rotations(246.0F, 0.0F, 89.0F)).withRightLegPose(new Rotations(8.0F, 20.0F, 4.0F));
    public static final ArmorStandPose SALUTE = new ArmorStandPose("salute", MINECRAFT_SOURCE).withLeftArmPose(new Rotations(10.0F, 0.0F, -5.0F)).withLeftLegPose(new Rotations(-1.0F, 0.0F, -1.0F)).withRightArmPose(new Rotations(-70.0F, -40.0F, 0.0F)).withRightLegPose(new Rotations(1.0F, 0.0F, 1.0F));
    public static final ArmorStandPose SOLEMN = new ArmorStandPose("solemn", MINECRAFT_SOURCE).withBodyPose(new Rotations(0.0F, 0.0F, 2.0F)).withHeadPose(new Rotations(15.0F, 0.0F, 0.0F)).withLeftArmPose(new Rotations(-30.0F, 15.0F, 15.0F)).withLeftLegPose(new Rotations(-1.0F, 0.0F, -1.0F)).withRightArmPose(new Rotations(-60.0F, -20.0F, -10.0F)).withRightLegPose(new Rotations(1.0F, 0.0F, 1.0F));
    public static final ArmorStandPose ZOMBIE = new ArmorStandPose("zombie", MINECRAFT_SOURCE).withHeadPose(new Rotations(-10.0F, 0.0F, -5.0F)).withLeftArmPose(new Rotations(-105.0F, 0.0F, 0.0F)).withLeftLegPose(new Rotations(7.0F, 0.0F, 0.0F)).withRightArmPose(new Rotations(-100.0F, 0.0F, 0.0F)).withRightLegPose(new Rotations(-46.0F, 0.0F, 0.0F));
    public static final ArmorStandPose WALKING = new ArmorStandPose("walking", VANILLA_TWEAKS_SOURCE).withRightArmPose(new Rotations(20.0f,0.0f,10.0f)).withLeftArmPose(new Rotations(-20.0f,0.0f,-10.0f)).withRightLegPose(new Rotations(-20.0f,0.0f,0.0f)).withLeftLegPose(new Rotations(20.0f,0.0f,0.0f));
    public static final ArmorStandPose RUNNING = new ArmorStandPose("running", VANILLA_TWEAKS_SOURCE).withRightArmPose(new Rotations(-40.0f,0.0f,10.0f)).withLeftArmPose(new Rotations(40.0f,0.0f,-10.0f)).withRightLegPose(new Rotations(40.0f,0.0f,0.0f)).withLeftLegPose(new Rotations(-40.0f,0.0f,0.0f));
    public static final ArmorStandPose POINTING = new ArmorStandPose("pointing", VANILLA_TWEAKS_SOURCE).withHeadPose(new Rotations(0.0f,20.0f,0.0f)).withRightArmPose(new Rotations(-90.0f,18.0f,0.0f)).withLeftArmPose(new Rotations(0.0f,0.0f,-10.0f));
    public static final ArmorStandPose BLOCKING = new ArmorStandPose("blocking", VANILLA_TWEAKS_SOURCE).withRightArmPose(new Rotations(-20.0f,-20.0f,0.0f)).withLeftArmPose(new Rotations(-50.0f,50.0f,0.0f)).withRightLegPose(new Rotations(-20.0f,0.0f,0.0f)).withLeftLegPose(new Rotations(20.0f,0.0f,0.0f));
    public static final ArmorStandPose LUNGEING = new ArmorStandPose("lungeing", VANILLA_TWEAKS_SOURCE).withBodyPose(new Rotations(15.0f,0.0f,0.0f)).withRightArmPose(new Rotations(-60.0f,-10.0f,0.0f)).withLeftArmPose(new Rotations(10.0f,0.0f,-10.0f)).withRightLegPose(new Rotations(-15.0f,0.0f,0.0f)).withLeftLegPose(new Rotations(30.0f,0.0f,0.0f));
    public static final ArmorStandPose WINNING = new ArmorStandPose("winning", VANILLA_TWEAKS_SOURCE).withHeadPose(new Rotations(-15.0f,0.0f,0.0f)).withRightArmPose(new Rotations(-120.0f,-10.0f,0.0f)).withLeftArmPose(new Rotations(10.0f,0.0f,-10.0f)).withLeftLegPose(new Rotations(15.0f,0.0f,0.0f));
    public static final ArmorStandPose SITTING = new ArmorStandPose("sitting", VANILLA_TWEAKS_SOURCE).withRightArmPose(new Rotations(-80.0f,20.0f,0.0f)).withLeftArmPose(new Rotations(-80.0f,-20.0f,0.0f)).withRightLegPose(new Rotations(-90.0f,10.0f,0.0f)).withLeftLegPose(new Rotations(-90.0f,-10.0f,0.0f));
    public static final ArmorStandPose ARABESQUE = new ArmorStandPose("arabesque", VANILLA_TWEAKS_SOURCE).withHeadPose(new Rotations(-15.0f,0.0f,0.0f)).withBodyPose(new Rotations(10.0f,0.0f,0.0f)).withRightArmPose(new Rotations(-140.0f,-10.0f,0.0f)).withLeftArmPose(new Rotations(70.0f,0.0f,-10.0f)).withLeftLegPose(new Rotations(75.0f,0.0f,0.0f));
    public static final ArmorStandPose CUPID = new ArmorStandPose("cupid", VANILLA_TWEAKS_SOURCE).withBodyPose(new Rotations(10.0f,0.0f,0.0f)).withRightArmPose(new Rotations(-90.0f,-10.0f,0.0f)).withLeftArmPose(new Rotations(-75.0f,0.0f,10.0f)).withLeftLegPose(new Rotations(75.0f,0.0f,0.0f));
    public static final ArmorStandPose CONFIDENT = new ArmorStandPose("confident", VANILLA_TWEAKS_SOURCE).withHeadPose(new Rotations(-10.0f,20.0f,0.0f)).withBodyPose(new Rotations(-2.0f,0.0f,0.0f)).withRightArmPose(new Rotations(5.0f,0.0f,0.0f)).withLeftArmPose(new Rotations(5.0f,0.0f,0.0f)).withRightLegPose(new Rotations(16.0f,2.0f,10.0f)).withLeftLegPose(new Rotations(0.0f,-10.0f,-4.0f));
    public static final ArmorStandPose DEATH = new ArmorStandPose("death", VANILLA_TWEAKS_SOURCE).withHeadPose(new Rotations(-85.0f,0.0f,0.0f)).withBodyPose(new Rotations(-90.0f,0.0f,0.0f)).withRightArmPose(new Rotations(-90.0f,10.0f,0.0f)).withLeftArmPose(new Rotations(-90.0f,-10.0f,0.0f));
    public static final ArmorStandPose FACEPALM = new ArmorStandPose("facepalm", VANILLA_TWEAKS_SOURCE).withHeadPose(new Rotations(45.0f,-4.0f,1.0f)).withBodyPose(new Rotations(10.0f,0.0f,0.0f)).withRightArmPose(new Rotations(18.0f,-14.0f,0.0f)).withLeftArmPose(new Rotations(-72.0f,24.0f,47.0f)).withRightLegPose(new Rotations(25.0f,-2.0f,0.0f)).withLeftLegPose(new Rotations(-4.0f,-6.0f,-2.0f));
    public static final ArmorStandPose LAZING = new ArmorStandPose("lazing", VANILLA_TWEAKS_SOURCE).withHeadPose(new Rotations(14.0f,-12.0f,6.0f)).withBodyPose(new Rotations(5.0f,0.0f,0.0f)).withRightArmPose(new Rotations(-40.0f,20.0f,0.0f)).withLeftArmPose(new Rotations(-4.0f,-20.0f,-10.0f)).withRightLegPose(new Rotations(-88.0f,71.0f,0.0f)).withLeftLegPose(new Rotations(-88.0f,46.0f,0.0f));
    public static final ArmorStandPose CONFUSED = new ArmorStandPose("confused", VANILLA_TWEAKS_SOURCE).withHeadPose(new Rotations(0.0f,30.0f,0f)).withBodyPose(new Rotations(0.0f,13.0f,0.0f)).withRightArmPose(new Rotations(-22.0f,31.0f,10.0f)).withLeftArmPose(new Rotations(145.0f,22.0f,-49.0f)).withRightLegPose(new Rotations(6.0f,-20.0f,0.0f)).withLeftLegPose(new Rotations(-6.0f,0.0f,0.0f));
    public static final ArmorStandPose FORMAL = new ArmorStandPose("formal", VANILLA_TWEAKS_SOURCE).withHeadPose(new Rotations(4.0f,0.0f,0.0f)).withBodyPose(new Rotations(4.0f,0.0f,0.0f)).withRightArmPose(new Rotations(30.0f,22.0f,-20.0f)).withLeftArmPose(new Rotations(30.0f,-20.0f,21.0f)).withRightLegPose(new Rotations(0.0f,0.0f,5.0f)).withLeftLegPose(new Rotations(0.0f,0.0f,-5.0f));
    public static final ArmorStandPose SAD = new ArmorStandPose("sad", VANILLA_TWEAKS_SOURCE).withHeadPose(new Rotations(63.0f,0.0f,0.0f)).withBodyPose(new Rotations(10.0f,0.0f,0.0f)).withRightArmPose(new Rotations(-5.0f,0.0f,5.0f)).withLeftArmPose(new Rotations(-5.0f,0.0f,-5.0f)).withRightLegPose(new Rotations(-5.0f,-10.0f,5.0f)).withLeftLegPose(new Rotations(-5.0f,16.0f,-5.0f));
    public static final ArmorStandPose JOYOUS = new ArmorStandPose("joyous", VANILLA_TWEAKS_SOURCE).withHeadPose(new Rotations(-11.0f,0.0f,0.0f)).withBodyPose(new Rotations(-4.0f,0.0f,0.0f)).withRightArmPose(new Rotations(0.0f,0.0f,100.0f)).withLeftArmPose(new Rotations(0.0f,0.0f,-100.0f)).withRightLegPose(new Rotations(-8.0f,0.0f,60.0f)).withLeftLegPose(new Rotations(-8.0f,0.0f,-60.0f));
    public static final ArmorStandPose STARGAZING = new ArmorStandPose("stargazing", VANILLA_TWEAKS_SOURCE).withHeadPose(new Rotations(-22.0f,25.0f,0.0f)).withBodyPose(new Rotations(-4.0f,10.0f,0.0f)).withRightArmPose(new Rotations(-153.0f,34.0f,-3.0f)).withLeftArmPose(new Rotations(4.0f,18.0f,0.0f)).withRightLegPose(new Rotations(-4.0f,17.0f,2.0f)).withLeftLegPose(new Rotations(6.0f,24.0f,0.0f));

    @Nullable
    private final String name;
    @Nullable
    private final String source;
    private final Rotations headPose;
    private final Rotations bodyPose;
    private final Rotations leftArmPose;
    private final Rotations rightArmPose;
    private final Rotations leftLegPose;
    private final Rotations rightLegPose;

    private ArmorStandPose(@Nullable String name, @Nullable String source) {
        this(name, source, new Rotations(0.0F, 0.0F, 0.0F), new Rotations(0.0F, 0.0F, 0.0F), new Rotations(0.0F, 0.0F, 0.0F), new Rotations(0.0F, 0.0F, 0.0F), new Rotations(0.0F, 0.0F, 0.0F), new Rotations(0.0F, 0.0F, 0.0F));
    }

    private ArmorStandPose(@Nullable String name, @Nullable String source, Rotations headPose, Rotations bodyPose, Rotations leftArmPose, Rotations rightArmPose, Rotations leftLegPose, Rotations rightLegPose) {
        this.name = name;
        this.source = source;
        this.headPose = headPose;
        this.bodyPose = bodyPose;
        this.leftArmPose = leftArmPose;
        this.rightArmPose = rightArmPose;
        this.leftLegPose = leftLegPose;
        this.rightLegPose = rightLegPose;
    }
    
    public static ArmorStandPose empty() {
        return new ArmorStandPose(null, null);
    }

    @Override
    public String toString() {
        return this.name != null ? this.name.toUpperCase(Locale.ROOT) : "POSE";
    }

    public String getTranslationKey() {
        return this.name != null ? "armorstatues.entity.armor_stand.pose." + this.name : null;
    }

    @Nullable
    public String getSource() {
        return this.source;
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
        return new ArmorStandPose(this.name, this.source, rotation, this.bodyPose, this.leftArmPose, this.rightArmPose, this.leftLegPose, this.rightLegPose);
    }

    public ArmorStandPose withBodyPose(Rotations rotation) {
        return new ArmorStandPose(this.name, this.source, this.headPose, rotation, this.leftArmPose, this.rightArmPose, this.leftLegPose, this.rightLegPose);
    }

    public ArmorStandPose withLeftArmPose(Rotations rotation) {
        return new ArmorStandPose(this.name, this.source, this.headPose, this.bodyPose, rotation, this.rightArmPose, this.leftLegPose, this.rightLegPose);
    }

    public ArmorStandPose withRightArmPose(Rotations rotation) {
        return new ArmorStandPose(this.name, this.source, this.headPose, this.bodyPose, this.leftArmPose, rotation, this.leftLegPose, this.rightLegPose);
    }

    public ArmorStandPose withLeftLegPose(Rotations rotation) {
        return new ArmorStandPose(this.name, this.source, this.headPose, this.bodyPose, this.leftArmPose, this.rightArmPose, rotation, this.rightLegPose);
    }

    public ArmorStandPose withRightLegPose(Rotations rotation) {
        return new ArmorStandPose(this.name, this.source, this.headPose, this.bodyPose, this.leftArmPose, this.rightArmPose, this.leftLegPose, rotation);
    }

    public ArmorStandPose mirror() {
        return new ArmorStandPose(this.name, this.source, mirrorRotation(this.headPose), mirrorRotation(this.bodyPose), mirrorRotation(this.rightArmPose), mirrorRotation(this.leftArmPose), mirrorRotation(this.rightLegPose), mirrorRotation(this.leftLegPose));
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
        return new ArmorStandPose(null, null, armorStand.getHeadPose(), armorStand.getBodyPose(), armorStand.getLeftArmPose(), armorStand.getRightArmPose(), armorStand.getLeftLegPose(), armorStand.getRightLegPose());
    }

    public static void applyTagToEntity(ArmorStand armorStand, CompoundTag tag) {
        ((ArmorStandAccessor) armorStand).callReadPose(tag);
    }

    public static ArmorStandPose random(PosePartMutator[] mutators, boolean clampRotations) {
        checkMutatorsSize(mutators);
        return new ArmorStandPose(null, null, mutators[0].randomRotations(clampRotations), mutators[1].randomRotations(clampRotations), mutators[2].randomRotations(clampRotations), mutators[3].randomRotations(clampRotations), mutators[4].randomRotations(clampRotations), mutators[5].randomRotations(clampRotations));
    }

    public static ArmorStandPose[] values() {
        return new ArmorStandPose[]{DEFAULT, SOLEMN, ATHENA, BRANDISH, HONOR, ENTERTAIN, SALUTE, HERO, RIPOSTE, ZOMBIE, CANCAN, WALKING, RUNNING, POINTING, BLOCKING, LUNGEING, WINNING, SITTING, ARABESQUE, CUPID, CONFIDENT, DEATH, FACEPALM, LAZING, CONFUSED, FORMAL, SAD, JOYOUS, STARGAZING};
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
