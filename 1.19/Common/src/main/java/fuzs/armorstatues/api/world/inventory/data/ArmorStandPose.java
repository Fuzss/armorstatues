package fuzs.armorstatues.api.world.inventory.data;

import fuzs.armorstatues.api.StatuesApi;
import fuzs.armorstatues.mixin.accessor.ArmorStandAccessor;
import net.minecraft.Util;
import net.minecraft.core.Rotations;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ArmorStandPose {
    private static final Rotations ZERO_ROTATIONS = new Rotations(0.0F, 0.0F, 0.0F);
    public static final double DEGREES_SNAP_INTERVAL = 0.125;
    public static final DecimalFormat ROTATION_FORMAT = Util.make(new DecimalFormat("#.##"), (decimalFormat) -> {
        decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
    });
    public static final ArmorStandPose EMPTY = new ArmorStandPose(null).withSourceType(SourceType.EMPTY);
    public static final ArmorStandPose ATHENA = new ArmorStandPose("athena").withBodyPose(new Rotations(0.0F, 0.0F, 2.0F)).withHeadPose(new Rotations(-5.0F, 0.0F, 0.0F)).withLeftArmPose(new Rotations(10.0F, 0.0F, -5.0F)).withLeftLegPose(new Rotations(-3.0F, -3.0F, -3.0F)).withRightArmPose(new Rotations(-60.0F, 20.0F, -10.0F)).withRightLegPose(new Rotations(3.0F, 3.0F, 3.0F)).withSourceType(SourceType.MINECRAFT);
    public static final ArmorStandPose BRANDISH = new ArmorStandPose("brandish").withBodyPose(new Rotations(0.0F, 0.0F, -2.0F)).withHeadPose(new Rotations(-15.0F, 0.0F, 0.0F)).withLeftArmPose(new Rotations(20.0F, 0.0F, -10.0F)).withLeftLegPose(new Rotations(5.0F, -3.0F, -3.0F)).withRightArmPose(new Rotations(-110.0F, 50.0F, 0.0F)).withRightLegPose(new Rotations(-5.0F, 3.0F, 3.0F)).withSourceType(SourceType.MINECRAFT);
    public static final ArmorStandPose CANCAN = new ArmorStandPose("cancan").withBodyPose(new Rotations(0.0F, 22.0F, 0.0F)).withHeadPose(new Rotations(-5.0F, 18.0F, 0.0F)).withLeftArmPose(new Rotations(8.0F, 0.0F, -114.0F)).withLeftLegPose(new Rotations(-111.0F, 55.0F, 0.0F)).withRightArmPose(new Rotations(0.0F, 84.0F, 111.0F)).withRightLegPose(new Rotations(0.0F, 23.0F, -13.0F)).withSourceType(SourceType.MINECRAFT);
    public static final ArmorStandPose DEFAULT = new ArmorStandPose("default").withLeftArmPose(new Rotations(-10.0F, 0.0F, -10.0F)).withLeftLegPose(new Rotations(-1.0F, 0.0F, -1.0F)).withRightArmPose(new Rotations(-15.0F, 0.0F, 10.0F)).withRightLegPose(new Rotations(1.0F, 0.0F, 1.0F)).withSourceType(SourceType.MINECRAFT);
    public static final ArmorStandPose ENTERTAIN = new ArmorStandPose("entertain").withHeadPose(new Rotations(-15.0F, 0.0F, 0.0F)).withLeftArmPose(new Rotations(-110.0F, -35.0F, 0.0F)).withLeftLegPose(new Rotations(5.0F, -3.0F, -3.0F)).withRightArmPose(new Rotations(-110.0F, 35.0F, 0.0F)).withRightLegPose(new Rotations(-5.0F, 3.0F, 3.0F)).withSourceType(SourceType.MINECRAFT);
    public static final ArmorStandPose HERO = new ArmorStandPose("hero").withBodyPose(new Rotations(0.0F, 8.0F, 0.0F)).withHeadPose(new Rotations(-4.0F, 67.0F, 0.0F)).withLeftArmPose(new Rotations(16.0F, 32.0F, -8.0F)).withLeftLegPose(new Rotations(0.0F, -75.0F, -8.0F)).withRightArmPose(new Rotations(-99.0F, 63.0F, 0.0F)).withRightLegPose(new Rotations(4.0F, 63.0F, 8.0F)).withSourceType(SourceType.MINECRAFT);
    public static final ArmorStandPose HONOR = new ArmorStandPose("honor").withHeadPose(new Rotations(-15.0F, 0.0F, 0.0F)).withLeftArmPose(new Rotations(-110.0F, 35.0F, 0.0F)).withLeftLegPose(new Rotations(5.0F, -3.0F, -3.0F)).withRightArmPose(new Rotations(-110.0F, -35.0F, 0.0F)).withRightLegPose(new Rotations(-5.0F, 3.0F, 3.0F)).withSourceType(SourceType.MINECRAFT);
    public static final ArmorStandPose RIPOSTE = new ArmorStandPose("riposte").withHeadPose(new Rotations(16.0F, 20.0F, 0.0F)).withLeftArmPose(new Rotations(4.0F, 8.0F, 237.0F)).withLeftLegPose(new Rotations(-14.0F, -18.0F, -16.0F)).withRightArmPose(new Rotations(246.0F, 0.0F, 89.0F)).withRightLegPose(new Rotations(8.0F, 20.0F, 4.0F)).withSourceType(SourceType.MINECRAFT);
    public static final ArmorStandPose SALUTE = new ArmorStandPose("salute").withLeftArmPose(new Rotations(10.0F, 0.0F, -5.0F)).withLeftLegPose(new Rotations(-1.0F, 0.0F, -1.0F)).withRightArmPose(new Rotations(-70.0F, -40.0F, 0.0F)).withRightLegPose(new Rotations(1.0F, 0.0F, 1.0F)).withSourceType(SourceType.MINECRAFT);
    public static final ArmorStandPose SOLEMN = new ArmorStandPose("solemn").withBodyPose(new Rotations(0.0F, 0.0F, 2.0F)).withHeadPose(new Rotations(15.0F, 0.0F, 0.0F)).withLeftArmPose(new Rotations(-30.0F, 15.0F, 15.0F)).withLeftLegPose(new Rotations(-1.0F, 0.0F, -1.0F)).withRightArmPose(new Rotations(-60.0F, -20.0F, -10.0F)).withRightLegPose(new Rotations(1.0F, 0.0F, 1.0F)).withSourceType(SourceType.MINECRAFT);
    public static final ArmorStandPose ZOMBIE = new ArmorStandPose("zombie").withHeadPose(new Rotations(-10.0F, 0.0F, -5.0F)).withLeftArmPose(new Rotations(-105.0F, 0.0F, 0.0F)).withLeftLegPose(new Rotations(7.0F, 0.0F, 0.0F)).withRightArmPose(new Rotations(-100.0F, 0.0F, 0.0F)).withRightLegPose(new Rotations(-46.0F, 0.0F, 0.0F)).withSourceType(SourceType.MINECRAFT);
    public static final ArmorStandPose WALKING = new ArmorStandPose("walking").withRightArmPose(new Rotations(20.0F,0.0F,10.0F)).withLeftArmPose(new Rotations(-20.0F,0.0F,-10.0F)).withRightLegPose(new Rotations(-20.0F,0.0F,0.0F)).withLeftLegPose(new Rotations(20.0F,0.0F,0.0F)).withSourceType(SourceType.VANILLA_TWEAKS);
    public static final ArmorStandPose RUNNING = new ArmorStandPose("running").withRightArmPose(new Rotations(-40.0F,0.0F,10.0F)).withLeftArmPose(new Rotations(40.0F,0.0F,-10.0F)).withRightLegPose(new Rotations(40.0F,0.0F,0.0F)).withLeftLegPose(new Rotations(-40.0F,0.0F,0.0F)).withSourceType(SourceType.VANILLA_TWEAKS);
    public static final ArmorStandPose POINTING = new ArmorStandPose("pointing").withHeadPose(new Rotations(0.0F,20.0F,0.0F)).withRightArmPose(new Rotations(-90.0F,18.0F,0.0F)).withLeftArmPose(new Rotations(0.0F,0.0F,-10.0F)).withSourceType(SourceType.VANILLA_TWEAKS);
    public static final ArmorStandPose BLOCKING = new ArmorStandPose("blocking").withRightArmPose(new Rotations(-20.0F,-20.0F,0.0F)).withLeftArmPose(new Rotations(-50.0F,50.0F,0.0F)).withRightLegPose(new Rotations(-20.0F,0.0F,0.0F)).withLeftLegPose(new Rotations(20.0F,0.0F,0.0F)).withSourceType(SourceType.VANILLA_TWEAKS);
    public static final ArmorStandPose LUNGEING = new ArmorStandPose("lungeing").withBodyPose(new Rotations(15.0F,0.0F,0.0F)).withRightArmPose(new Rotations(-60.0F,-10.0F,0.0F)).withLeftArmPose(new Rotations(10.0F,0.0F,-10.0F)).withRightLegPose(new Rotations(-15.0F,0.0F,0.0F)).withLeftLegPose(new Rotations(30.0F,0.0F,0.0F)).withSourceType(SourceType.VANILLA_TWEAKS);
    public static final ArmorStandPose WINNING = new ArmorStandPose("winning").withHeadPose(new Rotations(-15.0F,0.0F,0.0F)).withRightArmPose(new Rotations(-120.0F,-10.0F,0.0F)).withLeftArmPose(new Rotations(10.0F,0.0F,-10.0F)).withLeftLegPose(new Rotations(15.0F,0.0F,0.0F)).withSourceType(SourceType.VANILLA_TWEAKS);
    public static final ArmorStandPose SITTING = new ArmorStandPose("sitting").withRightArmPose(new Rotations(-80.0F,20.0F,0.0F)).withLeftArmPose(new Rotations(-80.0F,-20.0F,0.0F)).withRightLegPose(new Rotations(-90.0F,10.0F,0.0F)).withLeftLegPose(new Rotations(-90.0F,-10.0F,0.0F)).withSourceType(SourceType.VANILLA_TWEAKS);
    public static final ArmorStandPose ARABESQUE = new ArmorStandPose("arabesque").withHeadPose(new Rotations(-15.0F,0.0F,0.0F)).withBodyPose(new Rotations(10.0F,0.0F,0.0F)).withRightArmPose(new Rotations(-140.0F,-10.0F,0.0F)).withLeftArmPose(new Rotations(70.0F,0.0F,-10.0F)).withLeftLegPose(new Rotations(75.0F,0.0F,0.0F)).withSourceType(SourceType.VANILLA_TWEAKS);
    public static final ArmorStandPose CUPID = new ArmorStandPose("cupid").withBodyPose(new Rotations(10.0F,0.0F,0.0F)).withRightArmPose(new Rotations(-90.0F,-10.0F,0.0F)).withLeftArmPose(new Rotations(-75.0F,0.0F,10.0F)).withLeftLegPose(new Rotations(75.0F,0.0F,0.0F)).withSourceType(SourceType.VANILLA_TWEAKS);
    public static final ArmorStandPose CONFIDENT = new ArmorStandPose("confident").withHeadPose(new Rotations(-10.0F,20.0F,0.0F)).withBodyPose(new Rotations(-2.0F,0.0F,0.0F)).withRightArmPose(new Rotations(5.0F,0.0F,0.0F)).withLeftArmPose(new Rotations(5.0F,0.0F,0.0F)).withRightLegPose(new Rotations(16.0F,2.0F,10.0F)).withLeftLegPose(new Rotations(0.0F,-10.0F,-4.0F)).withSourceType(SourceType.VANILLA_TWEAKS);
    public static final ArmorStandPose DEATH = new ArmorStandPose("death").withHeadPose(new Rotations(-85.0F,0.0F,0.0F)).withBodyPose(new Rotations(-90.0F,0.0F,0.0F)).withRightArmPose(new Rotations(-90.0F,10.0F,0.0F)).withLeftArmPose(new Rotations(-90.0F,-10.0F,0.0F)).withSourceType(SourceType.VANILLA_TWEAKS);
    public static final ArmorStandPose FACEPALM = new ArmorStandPose("facepalm").withHeadPose(new Rotations(45.0F,-4.0F,1.0F)).withBodyPose(new Rotations(10.0F,0.0F,0.0F)).withRightArmPose(new Rotations(18.0F,-14.0F,0.0F)).withLeftArmPose(new Rotations(-72.0F,24.0F,47.0F)).withRightLegPose(new Rotations(25.0F,-2.0F,0.0F)).withLeftLegPose(new Rotations(-4.0F,-6.0F,-2.0F)).withSourceType(SourceType.VANILLA_TWEAKS);
    public static final ArmorStandPose LAZING = new ArmorStandPose("lazing").withHeadPose(new Rotations(14.0F,-12.0F,6.0F)).withBodyPose(new Rotations(5.0F,0.0F,0.0F)).withRightArmPose(new Rotations(-40.0F,20.0F,0.0F)).withLeftArmPose(new Rotations(-4.0F,-20.0F,-10.0F)).withRightLegPose(new Rotations(-88.0F,71.0F,0.0F)).withLeftLegPose(new Rotations(-88.0F,46.0F,0.0F)).withSourceType(SourceType.VANILLA_TWEAKS);
    public static final ArmorStandPose CONFUSED = new ArmorStandPose("confused").withHeadPose(new Rotations(0.0F,30.0F,0f)).withBodyPose(new Rotations(0.0F,13.0F,0.0F)).withRightArmPose(new Rotations(-22.0F,31.0F,10.0F)).withLeftArmPose(new Rotations(145.0F,22.0F,-49.0F)).withRightLegPose(new Rotations(6.0F,-20.0F,0.0F)).withLeftLegPose(new Rotations(-6.0F,0.0F,0.0F)).withSourceType(SourceType.VANILLA_TWEAKS);
    public static final ArmorStandPose FORMAL = new ArmorStandPose("formal").withHeadPose(new Rotations(4.0F,0.0F,0.0F)).withBodyPose(new Rotations(4.0F,0.0F,0.0F)).withRightArmPose(new Rotations(30.0F,22.0F,-20.0F)).withLeftArmPose(new Rotations(30.0F,-20.0F,21.0F)).withRightLegPose(new Rotations(0.0F,0.0F,5.0F)).withLeftLegPose(new Rotations(0.0F,0.0F,-5.0F)).withSourceType(SourceType.VANILLA_TWEAKS);
    public static final ArmorStandPose SAD = new ArmorStandPose("sad").withHeadPose(new Rotations(63.0F,0.0F,0.0F)).withBodyPose(new Rotations(10.0F,0.0F,0.0F)).withRightArmPose(new Rotations(-5.0F,0.0F,5.0F)).withLeftArmPose(new Rotations(-5.0F,0.0F,-5.0F)).withRightLegPose(new Rotations(-5.0F,-10.0F,5.0F)).withLeftLegPose(new Rotations(-5.0F,16.0F,-5.0F)).withSourceType(SourceType.VANILLA_TWEAKS);
    public static final ArmorStandPose JOYOUS = new ArmorStandPose("joyous").withHeadPose(new Rotations(-11.0F,0.0F,0.0F)).withBodyPose(new Rotations(-4.0F,0.0F,0.0F)).withRightArmPose(new Rotations(0.0F,0.0F,100.0F)).withLeftArmPose(new Rotations(0.0F,0.0F,-100.0F)).withRightLegPose(new Rotations(-8.0F,0.0F,60.0F)).withLeftLegPose(new Rotations(-8.0F,0.0F,-60.0F)).withSourceType(SourceType.VANILLA_TWEAKS);
    public static final ArmorStandPose STARGAZING = new ArmorStandPose("stargazing").withHeadPose(new Rotations(-22.0F,25.0F,0.0F)).withBodyPose(new Rotations(-4.0F,10.0F,0.0F)).withRightArmPose(new Rotations(-153.0F,34.0F,-3.0F)).withLeftArmPose(new Rotations(4.0F,18.0F,0.0F)).withRightLegPose(new Rotations(-4.0F,17.0F,2.0F)).withLeftLegPose(new Rotations(6.0F,24.0F,0.0F)).withSourceType(SourceType.VANILLA_TWEAKS);
    private static final ArmorStandPose[] VALUES = {DEFAULT, SOLEMN, ATHENA, BRANDISH, HONOR, ENTERTAIN, SALUTE, HERO, RIPOSTE, ZOMBIE, CANCAN, WALKING, RUNNING, POINTING, BLOCKING, LUNGEING, WINNING, SITTING, ARABESQUE, CUPID, CONFIDENT, DEATH, FACEPALM, LAZING, CONFUSED, FORMAL, SAD, JOYOUS, STARGAZING};

    @Nullable
    private final String name;
    @Nullable
    private final SourceType sourceType;
    @Nullable
    private final Rotations headPose;
    @Nullable
    private final Rotations bodyPose;
    @Nullable
    private final Rotations leftArmPose;
    @Nullable
    private final Rotations rightArmPose;
    @Nullable
    private final Rotations leftLegPose;
    @Nullable
    private final Rotations rightLegPose;

    private ArmorStandPose(@Nullable String name) {
        this(name, null, ZERO_ROTATIONS, ZERO_ROTATIONS, ZERO_ROTATIONS, ZERO_ROTATIONS, ZERO_ROTATIONS, ZERO_ROTATIONS);
    }

    private ArmorStandPose(@Nullable String name, @Nullable SourceType sourceType, @Nullable Rotations headPose, @Nullable Rotations bodyPose, @Nullable Rotations leftArmPose, @Nullable Rotations rightArmPose, @Nullable Rotations leftLegPose, @Nullable Rotations rightLegPose) {
        this.name = name;
        this.sourceType = sourceType;
        this.headPose = headPose;
        this.bodyPose = bodyPose;
        this.leftArmPose = leftArmPose;
        this.rightArmPose = rightArmPose;
        this.leftLegPose = leftLegPose;
        this.rightLegPose = rightLegPose;
    }
    
    public static ArmorStandPose empty() {
        return new ArmorStandPose(null, null, null, null, null, null, null, null);
    }

    @Override
    public String toString() {
        return this.name != null ? this.name.toUpperCase(Locale.ROOT) : "POSE";
    }

    public String getTranslationKey() {
        return this.name != null ? StatuesApi.MOD_ID + ".screen.pose." + this.name : null;
    }

    public SourceType getSourceType() {
        return this.sourceType != null ? this.sourceType : SourceType.TRANSIENT;
    }

    public Rotations getHeadPose() {
        return this.headPose != null ? this.headPose : ZERO_ROTATIONS;
    }

    public Rotations getBodyPose() {
        return this.bodyPose != null ? this.bodyPose : ZERO_ROTATIONS;
    }

    public Rotations getLeftArmPose() {
        return this.leftArmPose != null ? this.leftArmPose : ZERO_ROTATIONS;
    }

    public Rotations getRightArmPose() {
        return this.rightArmPose != null ? this.rightArmPose : ZERO_ROTATIONS;
    }

    public Rotations getLeftLegPose() {
        return this.leftLegPose != null ? this.leftLegPose : ZERO_ROTATIONS;
    }

    public Rotations getRightLegPose() {
        return this.rightLegPose != null ? this.rightLegPose : ZERO_ROTATIONS;
    }

    @Nullable
    public Rotations getNullableHeadPose() {
        return this.headPose;
    }

    @Nullable
    public Rotations getNullableBodyPose() {
        return this.bodyPose;
    }

    @Nullable
    public Rotations getNullableLeftArmPose() {
        return this.leftArmPose;
    }

    @Nullable
    public Rotations getNullableRightArmPose() {
        return this.rightArmPose;
    }

    @Nullable
    public Rotations getNullableLeftLegPose() {
        return this.leftLegPose;
    }

    @Nullable
    public Rotations getNullableRightLegPose() {
        return this.rightLegPose;
    }

    public ArmorStandPose withHeadPose(Rotations rotation) {
        return new ArmorStandPose(this.name, null, rotation, this.bodyPose, this.leftArmPose, this.rightArmPose, this.leftLegPose, this.rightLegPose);
    }

    public ArmorStandPose withBodyPose(Rotations rotation) {
        return new ArmorStandPose(this.name, null, this.headPose, rotation, this.leftArmPose, this.rightArmPose, this.leftLegPose, this.rightLegPose);
    }

    public ArmorStandPose withLeftArmPose(Rotations rotation) {
        return new ArmorStandPose(this.name, null, this.headPose, this.bodyPose, rotation, this.rightArmPose, this.leftLegPose, this.rightLegPose);
    }

    public ArmorStandPose withRightArmPose(Rotations rotation) {
        return new ArmorStandPose(this.name, null, this.headPose, this.bodyPose, this.leftArmPose, rotation, this.leftLegPose, this.rightLegPose);
    }

    public ArmorStandPose withLeftLegPose(Rotations rotation) {
        return new ArmorStandPose(this.name, null, this.headPose, this.bodyPose, this.leftArmPose, this.rightArmPose, rotation, this.rightLegPose);
    }

    public ArmorStandPose withRightLegPose(Rotations rotation) {
        return new ArmorStandPose(this.name, null, this.headPose, this.bodyPose, this.leftArmPose, this.rightArmPose, this.leftLegPose, rotation);
    }

    public ArmorStandPose withSourceType(SourceType sourceType) {
        return new ArmorStandPose(this.name, sourceType, this.headPose, this.bodyPose, this.leftArmPose, this.rightArmPose, this.leftLegPose, this.rightLegPose);
    }

    public ArmorStandPose mirror() {
        return new ArmorStandPose(this.name, SourceType.MIRRORED, mirrorRotations(this.headPose), mirrorRotations(this.bodyPose), mirrorRotations(this.rightArmPose), mirrorRotations(this.leftArmPose), mirrorRotations(this.rightLegPose), mirrorRotations(this.leftLegPose));
    }

    @Nullable
    private static Rotations mirrorRotations(@Nullable Rotations rotations) {
        return rotations != null ? new Rotations(rotations.getX(), -rotations.getY(), -rotations.getZ()) : null;
    }

    public ArmorStandPose copyAndFillFrom(ArmorStandPose fillFrom) {
        return new ArmorStandPose(this.name, this.sourceType, this.headPose != null ? this.headPose : fillFrom.headPose, this.bodyPose != null ? this.bodyPose : fillFrom.bodyPose, this.leftArmPose != null ? this.leftArmPose : fillFrom.leftArmPose, this.rightArmPose != null ? this.rightArmPose : fillFrom.rightArmPose, this.leftLegPose != null ? this.leftLegPose : fillFrom.leftLegPose, this.rightLegPose != null ? this.rightLegPose : fillFrom.rightLegPose);
    }

    public void applyToEntity(ArmorStand armorStand) {
        armorStand.setHeadPose(this.getHeadPose());
        armorStand.setBodyPose(this.getBodyPose());
        armorStand.setLeftArmPose(this.getLeftArmPose());
        armorStand.setRightArmPose(this.getRightArmPose());
        armorStand.setLeftLegPose(this.getLeftLegPose());
        armorStand.setRightLegPose(this.getRightLegPose());
    }

    public void serializeAllPoses(CompoundTag tag) {
        this.serializeBodyPoses(tag, null);
        this.serializeArmPoses(tag, null);
        this.serializeLegPoses(tag, null);
    }

    public boolean serializeBodyPoses(CompoundTag tag, @Nullable ArmorStandPose lastSentPose) {
        boolean hasChanged = false;
        if (this.headPose != null && (lastSentPose == null || !this.headPose.equals(lastSentPose.headPose))) {
            tag.put("Head", this.headPose.save());
            hasChanged = true;
        }
        if (this.bodyPose != null && (lastSentPose == null || !this.bodyPose.equals(lastSentPose.bodyPose))) {
            tag.put("Body", this.bodyPose.save());
            hasChanged = true;
        }
        return hasChanged;
    }

    public boolean serializeArmPoses(CompoundTag tag, @Nullable ArmorStandPose lastSentPose) {
        boolean hasChanged = false;
        if (this.leftArmPose != null && (lastSentPose == null || !this.leftArmPose.equals(lastSentPose.leftArmPose))) {
            tag.put("LeftArm", this.leftArmPose.save());
            hasChanged = true;
        }
        if (this.rightArmPose != null && (lastSentPose == null || !this.rightArmPose.equals(lastSentPose.rightArmPose))) {
            tag.put("RightArm", this.rightArmPose.save());
            hasChanged = true;
        }
        return hasChanged;
    }

    public boolean serializeLegPoses(CompoundTag tag, @Nullable ArmorStandPose lastSentPose) {
        boolean hasChanged = false;
        if (this.leftLegPose != null && (lastSentPose == null || !this.leftLegPose.equals(lastSentPose.leftLegPose))) {
            tag.put("LeftLeg", this.leftLegPose.save());
            hasChanged = true;
        }
        if (this.rightLegPose != null && (lastSentPose == null || !this.rightLegPose.equals(lastSentPose.rightLegPose))) {
            tag.put("RightLeg", this.rightLegPose.save());
            hasChanged = true;
        }
        return hasChanged;
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
        return VALUES.clone();
    }

    public static int valuesLength() {
        return VALUES.length;
    }

    public static ArmorStandPose randomValue() {
        List<ArmorStandPose> poses = Arrays.asList(VALUES);
        Collections.shuffle(poses);
        return poses.stream().findAny().orElseThrow();
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

    public enum SourceType {
        MINECRAFT, VANILLA_TWEAKS, TRANSIENT, EMPTY, MIRRORED;

        @Nullable
        public String getDisplayName() {
            if (this == MINECRAFT) return "Minecraft";
            if (this == VANILLA_TWEAKS) return "Vanilla Tweaks";
            return null;
        }
    }
}
