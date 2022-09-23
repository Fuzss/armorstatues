package fuzs.armorstatues.api.world.inventory.data;

import fuzs.armorstatues.mixin.accessor.ArmorStandAccessor;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.core.Rotations;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class ArmorStandPose {
    public static final ArmorStandPose ATHENA = new ArmorStandPose("athena", new Builder().bodyPose(new Rotations(0.0F, 0.0F, 2.0F)).headPose(new Rotations(-5.0F, 0.0F, 0.0F)).leftArmPose(new Rotations(10.0F, 0.0F, -5.0F)).leftLegPose(new Rotations(-3.0F, -3.0F, -3.0F)).rightArmPose(new Rotations(-60.0F, 20.0F, -10.0F)).rightLegPose(new Rotations(3.0F, 3.0F, 3.0F)));
    public static final ArmorStandPose BRANDISH = new ArmorStandPose("brandish", new Builder().bodyPose(new Rotations(0.0F, 0.0F, -2.0F)).headPose(new Rotations(-15.0F, 0.0F, 0.0F)).leftArmPose(new Rotations(20.0F, 0.0F, -10.0F)).leftLegPose(new Rotations(5.0F, -3.0F, -3.0F)).rightArmPose(new Rotations(-110.0F, 50.0F, 0.0F)).rightLegPose(new Rotations(-5.0F, 3.0F, 3.0F)));
    public static final ArmorStandPose CANCAN_A = new ArmorStandPose("cancanA", new Builder().bodyPose(new Rotations(0.0F, 22.0F, 0.0F)).headPose(new Rotations(-5.0F, 18.0F, 0.0F)).leftArmPose(new Rotations(8.0F, 0.0F, -114.0F)).leftLegPose(new Rotations(-111.0F, 55.0F, 0.0F)).rightArmPose(new Rotations(0.0F, 84.0F, 111.0F)).rightLegPose(new Rotations(0.0F, 23.0F, -13.0F)));
    public static final ArmorStandPose CANCAN_B = new ArmorStandPose("cancanB", new Builder().bodyPose(new Rotations(0.0F, -18.0F, 0.0F)).headPose(new Rotations(-10.0F, -20.0F, 0.0F)).leftArmPose(new Rotations(0.0F, 0.0F, -112.0F)).leftLegPose(new Rotations(0.0F, 0.0F, 13.0F)).rightArmPose(new Rotations(8.0F, 90.0F, 111.0F)).rightLegPose(new Rotations(-119.0F, -42.0F, 0.0F)));
    public static final ArmorStandPose DEFAULT = new ArmorStandPose("default", new Builder().leftArmPose(new Rotations(-10.0F, 0.0F, -10.0F)).leftLegPose(new Rotations(-1.0F, 0.0F, -1.0F)).rightArmPose(new Rotations(-15.0F, 0.0F, 10.0F)).rightLegPose(new Rotations(1.0F, 0.0F, 1.0F)));
    public static final ArmorStandPose ENTERTAIN = new ArmorStandPose("entertain", new Builder().headPose(new Rotations(-15.0F, 0.0F, 0.0F)).leftArmPose(new Rotations(-110.0F, -35.0F, 0.0F)).leftLegPose(new Rotations(5.0F, -3.0F, -3.0F)).rightArmPose(new Rotations(-110.0F, 35.0F, 0.0F)).rightLegPose(new Rotations(-5.0F, 3.0F, 3.0F)));
    public static final ArmorStandPose HERO = new ArmorStandPose("hero", new Builder().bodyPose(new Rotations(0.0F, 8.0F, 0.0F)).headPose(new Rotations(-4.0F, 67.0F, 0.0F)).leftArmPose(new Rotations(16.0F, 32.0F, -8.0F)).leftLegPose(new Rotations(0.0F, -75.0F, -8.0F)).rightArmPose(new Rotations(-99.0F, 63.0F, 0.0F)).rightLegPose(new Rotations(4.0F, 63.0F, 8.0F)));
    public static final ArmorStandPose HONOR = new ArmorStandPose("honor", new Builder().headPose(new Rotations(-15.0F, 0.0F, 0.0F)).leftArmPose(new Rotations(-110.0F, 35.0F, 0.0F)).leftLegPose(new Rotations(5.0F, -3.0F, -3.0F)).rightArmPose(new Rotations(-110.0F, -35.0F, 0.0F)).rightLegPose(new Rotations(-5.0F, 3.0F, 3.0F)));
    public static final ArmorStandPose NONE = new ArmorStandPose("none", new Builder());
    public static final ArmorStandPose RIPOSTE = new ArmorStandPose("riposte", new Builder().headPose(new Rotations(16.0F, 20.0F, 0.0F)).leftArmPose(new Rotations(4.0F, 8.0F, 237.0F)).leftLegPose(new Rotations(-14.0F, -18.0F, -16.0F)).rightArmPose(new Rotations(246.0F, 0.0F, 89.0F)).rightLegPose(new Rotations(8.0F, 20.0F, 4.0F)));
    public static final ArmorStandPose SALUTE = new ArmorStandPose("salute", new Builder().leftArmPose(new Rotations(10.0F, 0.0F, -5.0F)).leftLegPose(new Rotations(-1.0F, 0.0F, -1.0F)).rightArmPose(new Rotations(-70.0F, -40.0F, 0.0F)).rightLegPose(new Rotations(1.0F, 0.0F, 1.0F)));
    public static final ArmorStandPose SOLEMN = new ArmorStandPose("solemn", new Builder().bodyPose(new Rotations(0.0F, 0.0F, 2.0F)).headPose(new Rotations(15.0F, 0.0F, 0.0F)).leftArmPose(new Rotations(-30.0F, 15.0F, 15.0F)).leftLegPose(new Rotations(-1.0F, 0.0F, -1.0F)).rightArmPose(new Rotations(-60.0F, -20.0F, -10.0F)).rightLegPose(new Rotations(1.0F, 0.0F, 1.0F)));
    public static final ArmorStandPose ZOMBIE = new ArmorStandPose("zombie", new Builder().headPose(new Rotations(-10.0F, 0.0F, -5.0F)).leftArmPose(new Rotations(-105.0F, 0.0F, 0.0F)).leftLegPose(new Rotations(7.0F, 0.0F, 0.0F)).rightArmPose(new Rotations(-100.0F, 0.0F, 0.0F)).rightLegPose(new Rotations(-46.0F, 0.0F, 0.0F)));
    public static final double DEGREES_SNAP_INTERVAL = 0.125;
    public static final DecimalFormat ROTATION_FORMAT = Util.make(new DecimalFormat("#.##"), (decimalFormat) -> {
        decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
    });

    @Nullable
    private final String name;
    private final Rotations headPose;
    private final Rotations bodyPose;
    private final Rotations leftArmPose;
    private final Rotations rightArmPose;
    private final Rotations leftLegPose;
    private final Rotations rightLegPose;

    private ArmorStandPose(@Nullable String name, Builder builder) {
        this(name, builder.headPose, builder.bodyPose, builder.leftArmPose, builder.rightArmPose, builder.leftLegPose, builder.rightLegPose);
    }

    private ArmorStandPose(Rotations headPose, Rotations bodyPose, Rotations leftArmPose, Rotations rightArmPose, Rotations leftLegPose, Rotations rightLegPose) {
        this(null, headPose, bodyPose, leftArmPose, rightArmPose, leftLegPose, rightLegPose);
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

    @Override
    public String toString() {
        if (this.name != null) {
            return this.name;
        }
        return super.toString();
    }

    public Component getComponent() {
        Objects.requireNonNull(this.name, "Trying to get component for transient armor stand pose");
        return Component.translatable("armorstatues.entity.armor_stand.pose." + this.name);
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

    public ArmorStandPose setHeadPose(Rotations rotation) {
        return this.setPose(rotation, Builder::headPose);
    }

    public ArmorStandPose setBodyPose(Rotations rotation) {
        return this.setPose(rotation, Builder::bodyPose);
    }

    public ArmorStandPose setLeftArmPose(Rotations rotation) {
        return this.setPose(rotation, Builder::leftArmPose);
    }

    public ArmorStandPose setRightArmPose(Rotations rotation) {
        return this.setPose(rotation, Builder::rightArmPose);
    }

    public ArmorStandPose setLeftLegPose(Rotations rotation) {
        return this.setPose(rotation, Builder::leftLegPose);
    }

    public ArmorStandPose setRightLegPose(Rotations rotation) {
        return this.setPose(rotation, Builder::rightLegPose);
    }

    private ArmorStandPose setPose(Rotations rotation, BiFunction<Builder, Rotations, Builder> function) {
        return new ArmorStandPose(this.name, function.apply(new Builder(this), rotation));
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
        return new ArmorStandPose(armorStand.getHeadPose(), armorStand.getBodyPose(), armorStand.getLeftArmPose(), armorStand.getRightArmPose(), armorStand.getLeftLegPose(), armorStand.getRightLegPose());
    }

    public static void applyTagToEntity(ArmorStand armorStand, CompoundTag tag) {
        ((ArmorStandAccessor) armorStand).callReadPose(tag);
    }

    public static ArmorStandPose random(boolean clampRotations) {
        return new ArmorStandPose(PosePartMutator.HEAD.randomRotations(clampRotations), PosePartMutator.BODY.randomRotations(clampRotations), PosePartMutator.LEFT_ARM.randomRotations(clampRotations), PosePartMutator.RIGHT_ARM.randomRotations(clampRotations), PosePartMutator.LEFT_LEG.randomRotations(clampRotations), PosePartMutator.RIGHT_LEG.randomRotations(clampRotations));
    }

    public static ArmorStandPose[] values() {
        return new ArmorStandPose[]{DEFAULT, SOLEMN, ATHENA, BRANDISH, HONOR, ENTERTAIN, SALUTE, HERO, RIPOSTE, ZOMBIE, CANCAN_A, CANCAN_B};
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

    private static class Builder {
        Rotations headPose;
        Rotations bodyPose;
        Rotations leftArmPose;
        Rotations rightArmPose;
        Rotations leftLegPose;
        Rotations rightLegPose;

        private Builder() {
            this.headPose = new Rotations(0.0F, 0.0F, 0.0F);
            this.bodyPose = new Rotations(0.0F, 0.0F, 0.0F);
            this.leftArmPose = new Rotations(0.0F, 0.0F, 0.0F);
            this.rightArmPose = new Rotations(0.0F, 0.0F, 0.0F);
            this.leftLegPose = new Rotations(0.0F, 0.0F, 0.0F);
            this.rightLegPose = new Rotations(0.0F, 0.0F, 0.0F);
        }

        private Builder(ArmorStandPose pose) {
            this.headPose = pose.headPose;
            this.bodyPose = pose.bodyPose;
            this.leftArmPose = pose.leftArmPose;
            this.rightArmPose = pose.rightArmPose;
            this.leftLegPose = pose.leftLegPose;
            this.rightLegPose = pose.rightLegPose;
        }

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

    public enum PosePartMutator {
        HEAD("head", ArmorStandPose::getHeadPose, ArmorStandPose::setHeadPose, range(-60.0F, 60.0F), range(-60.0F, 60.0F), range(-120.0, 120.0)),
        BODY("body", ArmorStandPose::getBodyPose, ArmorStandPose::setBodyPose, range(0.0F, 120.0F), range(-60.0F, 60.0F), range(-120.0, 120.0)),
        LEFT_ARM("leftArm", ArmorStand::isShowArms, ArmorStandPose::getRightArmPose, ArmorStandPose::setRightArmPose, range(-180.0, 0.0), range(-90.0, 45.0), range(-120.0, 120.0), new Direction.Axis[]{Direction.Axis.X, Direction.Axis.Y, Direction.Axis.Z}, Direction.Axis.Y),
        RIGHT_ARM("rightArm", ArmorStand::isShowArms, ArmorStandPose::getLeftArmPose, ArmorStandPose::setLeftArmPose, range(-180.0, 0.0), range(-45.0, 90.0), range(-120.0, 120.0), new Direction.Axis[]{Direction.Axis.X, Direction.Axis.Y, Direction.Axis.Z}, Direction.Axis.Y),
        LEFT_LEG("leftLeg", ArmorStandPose::getRightLegPose, ArmorStandPose::setRightLegPose, range(-120.0, 120.0), range(-90.0, 0.0), range(-120.0, 120.0)),
        RIGHT_LEG("rightLeg", ArmorStandPose::getLeftLegPose, ArmorStandPose::setLeftLegPose, range(-120.0, 120.0), range(0.0, 90.0), range(-120.0, 120.0));

        private final String translationId;
        private final Predicate<ArmorStand> filter;
        private final Function<ArmorStandPose, Rotations> getRotations;
        private final BiFunction<ArmorStandPose, Rotations, ArmorStandPose> setRotations;
        private final PosePartAxisRange[] axisRanges;
        private final Direction.Axis[] axisOrder;
        private final byte invertedIndices;

        PosePartMutator(String translationId, Function<ArmorStandPose, Rotations> getRotations, BiFunction<ArmorStandPose, Rotations, ArmorStandPose> setRotations, PosePartAxisRange rangeX, PosePartAxisRange rangeY, PosePartAxisRange rangeZ) {
            this(translationId, armorStand -> true, getRotations, setRotations, rangeX, rangeY, rangeZ, new Direction.Axis[]{Direction.Axis.X, Direction.Axis.Y, Direction.Axis.Z}, Direction.Axis.Y);
        }

        PosePartMutator(String translationId, Predicate<ArmorStand> filter, Function<ArmorStandPose, Rotations> getRotations, BiFunction<ArmorStandPose, Rotations, ArmorStandPose> setRotations, PosePartAxisRange rangeX, PosePartAxisRange rangeY, PosePartAxisRange rangeZ, Direction.Axis[] axisOrder, Direction.Axis... invertedAxes) {
            this.translationId = translationId;
            this.filter = filter;
            this.getRotations = getRotations;
            this.setRotations = setRotations;
            this.axisRanges = new PosePartAxisRange[]{rangeX, rangeY, rangeZ};
            this.axisOrder = axisOrder;
            this.invertedIndices = computeInvertedIndices(invertedAxes);
        }

        private static byte computeInvertedIndices(Direction.Axis[] invertedAxes) {
            byte invertedIndices = 0;
            for (Direction.Axis axis : invertedAxes) {
                invertedIndices |= 1 << axis.ordinal();
            }
            return invertedIndices;
        }

        public boolean test(ArmorStand armorStand) {
            return this.filter.test(armorStand);
        }

        public Component getComponent() {
            return Component.translatable("armorstatues.screen.rotations.pose." + this.translationId);
        }

        public Component getAxisComponent(ArmorStandPose pose, int index) {
            double value = ArmorStandPose.snapValue(this.getRotationsAtAxis(index, pose), DEGREES_SNAP_INTERVAL);
            return Component.translatable("armorstatues.screen.rotations." + this.getAxisAt(index), ROTATION_FORMAT.format(value));
        }

        public double getRotationsAtAxis(int index, ArmorStandPose pose) {
            return this.getRotationsAtAxis(index, this.getRotations.apply(pose));
        }

        private double getRotationsAtAxis(int index, Rotations rotations) {
            return switch (this.getAxisAt(index)) {
                case X -> this.invertAtAxis(Direction.Axis.X, rotations.getWrappedX());
                case Y -> this.invertAtAxis(Direction.Axis.Y, rotations.getWrappedY());
                case Z -> this.invertAtAxis(Direction.Axis.Z, rotations.getWrappedZ());
            };
        }

        public double getNormalizedRotationsAtAxis(int index, ArmorStandPose pose, boolean clampRotations) {
            return this.getNormalizedRotationsAtAxis(index, this.getRotations.apply(pose), clampRotations);
        }

        private double getNormalizedRotationsAtAxis(int index, Rotations rotations, boolean clampRotations) {
            return switch (this.getAxisAt(index)) {
                case X -> (float) this.getAxisRangeAtAxis(Direction.Axis.X, clampRotations).normalize(this.invertAtAxis(Direction.Axis.X, rotations.getWrappedX()));
                case Y -> (float) this.getAxisRangeAtAxis(Direction.Axis.Y, clampRotations).normalize(this.invertAtAxis(Direction.Axis.Y, rotations.getWrappedY()));
                case Z -> (float) this.getAxisRangeAtAxis(Direction.Axis.Z, clampRotations).normalize(this.invertAtAxis(Direction.Axis.Z, rotations.getWrappedZ()));
            };
        }

        private float invertAtAxis(Direction.Axis axis, float value) {
            return (this.invertedIndices >> axis.ordinal() & 1) == 1 ? value * -1.0F : value;
        }

        public ArmorStandPose setRotationsAtAxis(int index, ArmorStandPose pose, double newValue, boolean clampRotations) {
            return this.setRotations.apply(pose, this.setRotationsAtAxis(index, this.getRotations.apply(pose), (float) this.getAxisRangeAtAxis(index, clampRotations).expand(newValue)));
        }

        private PosePartAxisRange getAxisRangeAtAxis(int index, boolean clampRotations) {
            return this.getAxisRangeAtAxis(this.getAxisAt(index), clampRotations);
        }

        private PosePartAxisRange getAxisRangeAtAxis(Direction.Axis axis, boolean clampRotations) {
            return clampRotations ? this.axisRanges[axis.ordinal()] : fullRange();
        }

        private Rotations setRotationsAtAxis(int index, Rotations rotations, float newValue) {
            return switch (this.getAxisAt(index)) {
                case X -> new Rotations(this.invertAtAxis(Direction.Axis.X, newValue), rotations.getY(), rotations.getZ());
                case Y -> new Rotations(rotations.getX(), this.invertAtAxis(Direction.Axis.Y, newValue), rotations.getZ());
                case Z -> new Rotations(rotations.getX(), rotations.getY(), this.invertAtAxis(Direction.Axis.Z, newValue));
            };
        }

        private Direction.Axis getAxisAt(int index) {
            return this.axisOrder[index];
        }

        Rotations randomRotations(boolean clampRotations) {
            Rotations rotations = new Rotations((float) this.getAxisRangeAtAxis(Direction.Axis.X, clampRotations).random(), (float) this.getAxisRangeAtAxis(Direction.Axis.Y, clampRotations).random(), (float) this.getAxisRangeAtAxis(Direction.Axis.Z, clampRotations).random());
            return clampRotations ? this.setRotationsAtAxis(2, rotations, 0.0F) : rotations;
        }

        private static PosePartAxisRange fullRange() {
            return range(PosePartAxisRange.MIN_VALUE, PosePartAxisRange.MAX_VALUE);
        }

        private static PosePartAxisRange range(double min, double max) {
            return new PosePartAxisRange(min, max);
        }

        public record PosePartAxisRange(double min, double max) {
            public static final double MIN_VALUE = -180.0;
            public static final double MAX_VALUE = 180.0;
            private static final Random RANDOM = new Random();

            public PosePartAxisRange {
                if (min >= max) {
                    throw new IllegalArgumentException("Min must be smaller than max: %s >= %s".formatted(min, max));
                }
                if (Mth.clamp(min, MIN_VALUE, MAX_VALUE) != min) {
                    throw new IllegalArgumentException("Min out of bounds, must be between -180 and 180, was %s".formatted(min));
                }
                if (Mth.clamp(max, MIN_VALUE, MAX_VALUE) != max) {
                    throw new IllegalArgumentException("Max out of bounds, must be between -180 and 180, was %s".formatted(max));
                }
            }

            public double normalize(double expandedValue) {
                return (this.clamp(expandedValue) - this.min) / this.range();
            }

            public double expand(double normalizedValue) {
                return normalizedValue * this.range() + this.min;
            }

            public double clamp(double value) {
                return Mth.clamp(value, this.min, this.max);
            }

            public double random() {
                return RANDOM.nextDouble(this.range()) + this.min;
            }

            private double range() {
                return this.max - this.min;
            }
        }
    }
}
