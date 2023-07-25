package fuzs.armorstatues.api.world.inventory.data;

import net.minecraft.core.Direction;
import net.minecraft.core.Rotations;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.Locale;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class PosePartMutator {
    public static final PosePartMutator HEAD = new PosePartMutator("head", ArmorStandPose::getHeadPose, ArmorStandPose::withHeadPose, PosePartAxisRange.range(-60.0F, 60.0F), PosePartAxisRange.range(-60.0F, 60.0F), PosePartAxisRange.range(-120.0, 120.0));
    public static final PosePartMutator BODY = new PosePartMutator("body", ArmorStandPose::getBodyPose, ArmorStandPose::withBodyPose, PosePartAxisRange.range(-30.0F, 30.0F), PosePartAxisRange.range(-30.0F, 30.0F), PosePartAxisRange.range(-120.0, 120.0));
    public static final PosePartMutator LEFT_ARM = new PosePartMutator("leftArm", ArmorStandPose::getRightArmPose, ArmorStandPose::withRightArmPose, PosePartAxisRange.range(-180.0, 0.0), PosePartAxisRange.range(-90.0, 45.0), PosePartAxisRange.range(-120.0, 120.0));
    public static final PosePartMutator RIGHT_ARM = new PosePartMutator("rightArm", ArmorStandPose::getLeftArmPose, ArmorStandPose::withLeftArmPose, PosePartAxisRange.range(-180.0, 0.0), PosePartAxisRange.range(-45.0, 90.0), PosePartAxisRange.range(-120.0, 120.0));
    public static final PosePartMutator LEFT_LEG = new PosePartMutator("leftLeg", ArmorStandPose::getRightLegPose, ArmorStandPose::withRightLegPose, PosePartAxisRange.range(-120.0, 120.0), PosePartAxisRange.range(-90.0, 0.0), PosePartAxisRange.range(-120.0, 120.0));
    public static final PosePartMutator RIGHT_LEG = new PosePartMutator("rightLeg", ArmorStandPose::getLeftLegPose, ArmorStandPose::withLeftLegPose, PosePartAxisRange.range(-120.0, 120.0), PosePartAxisRange.range(0.0, 90.0), PosePartAxisRange.range(-120.0, 120.0));

    private final String name;
    private final Function<ArmorStandPose, Rotations> getRotations;
    private final BiFunction<ArmorStandPose, Rotations, ArmorStandPose> setRotations;
    private final PosePartAxisRange[] axisRanges;
    private final Direction.Axis[] axisOrder;
    private final byte invertedIndices;

    public PosePartMutator(String name, Function<ArmorStandPose, Rotations> getRotations, BiFunction<ArmorStandPose, Rotations, ArmorStandPose> setRotations, PosePartAxisRange rangeX, PosePartAxisRange rangeY, PosePartAxisRange rangeZ) {
        this(name, getRotations, setRotations, rangeX, rangeY, rangeZ, new Direction.Axis[]{Direction.Axis.X, Direction.Axis.Y, Direction.Axis.Z}, Direction.Axis.Y);
    }

    public PosePartMutator(String name, Function<ArmorStandPose, Rotations> getRotations, BiFunction<ArmorStandPose, Rotations, ArmorStandPose> setRotations, PosePartAxisRange rangeX, PosePartAxisRange rangeY, PosePartAxisRange rangeZ, Direction.Axis[] axisOrder, Direction.Axis... invertedAxes) {
        this.name = name;
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

    @Override
    public String toString() {
        return this.name.toUpperCase(Locale.ROOT);
    }

    public String getTranslationKey() {
        return "armorstatues.screen.rotations.pose." + this.name;
    }

    public Component getAxisComponent(ArmorStandPose pose, int index) {
        double value = ArmorStandPose.snapValue(this.getRotationsAtAxis(index, pose), ArmorStandPose.DEGREES_SNAP_INTERVAL);
        return Component.translatable("armorstatues.screen.rotations." + this.getAxisAt(index), ArmorStandPose.ROTATION_FORMAT.format(value));
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
            case X ->
                    (float) this.getAxisRangeAtAxis(Direction.Axis.X, clampRotations).normalize(this.invertAtAxis(Direction.Axis.X, rotations.getWrappedX()));
            case Y ->
                    (float) this.getAxisRangeAtAxis(Direction.Axis.Y, clampRotations).normalize(this.invertAtAxis(Direction.Axis.Y, rotations.getWrappedY()));
            case Z ->
                    (float) this.getAxisRangeAtAxis(Direction.Axis.Z, clampRotations).normalize(this.invertAtAxis(Direction.Axis.Z, rotations.getWrappedZ()));
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
        return clampRotations ? this.axisRanges[axis.ordinal()] : PosePartAxisRange.fullRange();
    }

    private Rotations setRotationsAtAxis(int index, Rotations rotations, float newValue) {
        return switch (this.getAxisAt(index)) {
            case X -> new Rotations(this.invertAtAxis(Direction.Axis.X, newValue), rotations.getY(), rotations.getZ());
            case Y -> new Rotations(rotations.getX(), this.invertAtAxis(Direction.Axis.Y, newValue), rotations.getZ());
            case Z -> new Rotations(rotations.getX(), rotations.getY(), this.invertAtAxis(Direction.Axis.Z, newValue));
        };
    }

    public Direction.Axis getAxisAt(int index) {
        return this.axisOrder[index];
    }

    Rotations randomRotations(boolean clampRotations) {
        Rotations rotations = new Rotations((float) this.getAxisRangeAtAxis(Direction.Axis.X, clampRotations).random(), (float) this.getAxisRangeAtAxis(Direction.Axis.Y, clampRotations).random(), (float) this.getAxisRangeAtAxis(Direction.Axis.Z, clampRotations).random());
        return clampRotations ? this.setRotationsAtAxis(2, rotations, 0.0F) : rotations;
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
                throw new IllegalArgumentException("Min out of bounds, must be between %s and %s, was %s".formatted(MIN_VALUE, MAX_VALUE, min));
            }
            if (Mth.clamp(max, MIN_VALUE, MAX_VALUE) != max) {
                throw new IllegalArgumentException("Max out of bounds, must be between %s and %s, was %s".formatted(MIN_VALUE, MAX_VALUE, max));
            }
        }

        public static PosePartAxisRange fullRange() {
            return range(PosePartAxisRange.MIN_VALUE, PosePartAxisRange.MAX_VALUE);
        }

        public static PosePartAxisRange range(double min, double max) {
            return new PosePartAxisRange(min, max);
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

        public double range() {
            return this.max - this.min;
        }
    }
}
