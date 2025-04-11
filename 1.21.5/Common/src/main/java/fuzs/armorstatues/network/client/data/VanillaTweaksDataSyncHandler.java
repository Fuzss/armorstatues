package fuzs.armorstatues.network.client.data;

import com.google.common.collect.ImmutableSortedMap;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Unit;
import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.config.ClientConfig;
import fuzs.armorstatues.init.ModRegistry;
import fuzs.statuemenus.api.v1.world.inventory.ArmorStandHolder;
import fuzs.statuemenus.api.v1.world.inventory.data.*;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Rotations;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class VanillaTweaksDataSyncHandler extends CommandDataSyncHandler {
    private static final int MAX_INCREMENTAL_OPERATIONS = 12;
    public static final int CHECK_TARGET = 999;
    public static final int SWAP_SLOTS_MAINHAND_AND_OFFHAND = 161;
    public static final int SWAP_SLOTS_MAINHAND_AND_HEAD = 162;
    public static final int MIRROR_ARMS_LEFT_TO_RIGHT = 131;
    public static final int MIRROR_ARMS_RIGHT_TO_LEFT = 132;
    public static final int MIRROR_LEGS_LEFT_TO_RIGHT = 133;
    public static final int MIRROR_LEGS_RIGHT_TO_LEFT = 134;
    public static final int UTILITIES_LOCK = 1000;
    public static final int UTILITIES_UNLOCK = 1001;
    public static final int MIRROR_AND_FLIP_FLIP = 135;
    public static final int SHOW_BASE_PLATE_YES = 1;
    public static final int SHOW_BASE_PLATE_NO = 2;
    public static final int SHOW_ARMS_YES = 3;
    public static final int SHOW_ARMS_NO = 4;
    public static final int SMALL_STAND_YES = 5;
    public static final int SMALL_STAND_NO = 6;
    public static final int APPLY_GRAVITY_YES = 7;
    public static final int APPLY_GRAVITY_NO = 8;
    public static final int STAND_VISIBLE_YES = 9;
    public static final int STAND_VISIBLE_NO = 10;
    public static final int DISPLAY_NAME_YES = 11;
    public static final int DISPLAY_NAME_NO = 12;
    public static final int NUDGE_POSITION_X8_NEGATIVE = 40;
    public static final int NUDGE_POSITION_X3_NEGATIVE = 101;
    public static final int NUDGE_POSITION_X1_NEGATIVE = 102;
    public static final int NUDGE_POSITION_X1_POSITIVE = 103;
    public static final int NUDGE_POSITION_X3_POSITIVE = 104;
    public static final int NUDGE_POSITION_X8_POSITIVE = 43;
    public static final int NUDGE_POSITION_Y8_NEGATIVE = 44;
    public static final int NUDGE_POSITION_Y3_NEGATIVE = 105;
    public static final int NUDGE_POSITION_Y1_NEGATIVE = 106;
    public static final int NUDGE_POSITION_Y1_POSITIVE = 107;
    public static final int NUDGE_POSITION_Y3_POSITIVE = 108;
    public static final int NUDGE_POSITION_Y8_POSITIVE = 47;
    public static final int NUDGE_POSITION_Z8_NEGATIVE = 48;
    public static final int NUDGE_POSITION_Z3_NEGATIVE = 109;
    public static final int NUDGE_POSITION_Z1_NEGATIVE = 110;
    public static final int NUDGE_POSITION_Z1_POSITIVE = 111;
    public static final int NUDGE_POSITION_Z3_POSITIVE = 112;
    public static final int NUDGE_POSITION_Z8_POSITIVE = 51;
    public static final int ADJUST_ROTATION_ANGLE_STEP_45 = 120;
    public static final int ADJUST_ROTATION_ANGLE_STEP_15 = 121;
    public static final int ADJUST_ROTATION_ANGLE_STEP_5 = 122;
    public static final int ADJUST_ROTATION_ANGLE_STEP_1 = 123;
    public static final int ADJUST_ROTATION_ROTATE_RIGHT = 56;
    public static final int ADJUST_ROTATION_ROTATE_LEFT = 57;
    public static final int POSE_PRESETS_ATTENTION = 20;
    public static final int POSE_PRESETS_WALKING = 21;
    public static final int POSE_PRESETS_RUNNING = 22;
    public static final int POSE_PRESETS_POINTING = 23;
    public static final int POSE_PRESETS_BLOCKING = 24;
    public static final int POSE_PRESETS_LUNGEING = 25;
    public static final int POSE_PRESETS_WINNING = 26;
    public static final int POSE_PRESETS_SITTING = 27;
    public static final int POSE_PRESETS_ARABESQUE = 28;
    public static final int POSE_PRESETS_CUPID = 29;
    public static final int POSE_PRESETS_CONFIDENT = 30;
    public static final int POSE_PRESETS_SALUTE = 31;
    public static final int POSE_PRESETS_DEATH = 32;
    public static final int POSE_PRESETS_FACEPALM = 33;
    public static final int POSE_PRESETS_LAZING = 34;
    public static final int POSE_PRESETS_CONFUSED = 35;
    public static final int POSE_PRESETS_FORMAL = 36;
    public static final int POSE_PRESETS_SAD = 37;
    public static final int POSE_PRESETS_JOYOUS = 38;
    public static final int POSE_PRESETS_STARGAZING = 39;
    public static final int AUTO_ALIGNMENT_BLOCK_ON_SURFACE = 151;
    public static final int AUTO_ALIGNMENT_ITEM_ON_SURFACE = 152;
    public static final int AUTO_ALIGNMENT_ITEM_FLAT_ON_SURFACE = 153;
    public static final int AUTO_ALIGNMENT_TOOL_FLAT_ON_SURFACE = 154;
    public static final int AUTO_ALIGNMENT_TOOL_RACK = 155;
    public static final int POSE_ADJUSTMENT_HEAD_X_NEGATIVE = 60;
    public static final int POSE_ADJUSTMENT_HEAD_X_POSITIVE = 61;
    public static final int POSE_ADJUSTMENT_HEAD_Y_NEGATIVE = 62;
    public static final int POSE_ADJUSTMENT_HEAD_Y_POSITIVE = 63;
    public static final int POSE_ADJUSTMENT_HEAD_Z_NEGATIVE = 64;
    public static final int POSE_ADJUSTMENT_HEAD_Z_POSITIVE = 65;
    public static final int POSE_ADJUSTMENT_BODY_X_NEGATIVE = 67;
    public static final int POSE_ADJUSTMENT_BODY_X_POSITIVE = 66;
    public static final int POSE_ADJUSTMENT_BODY_Y_NEGATIVE = 68;
    public static final int POSE_ADJUSTMENT_BODY_Y_POSITIVE = 69;
    public static final int POSE_ADJUSTMENT_BODY_Z_NEGATIVE = 70;
    public static final int POSE_ADJUSTMENT_BODY_Z_POSITIVE = 71;
    public static final int POSE_ADJUSTMENT_RIGHT_ARM_X_NEGATIVE = 72;
    public static final int POSE_ADJUSTMENT_RIGHT_ARM_X_POSITIVE = 73;
    public static final int POSE_ADJUSTMENT_RIGHT_ARM_Y_NEGATIVE = 74;
    public static final int POSE_ADJUSTMENT_RIGHT_ARM_Y_POSITIVE = 75;
    public static final int POSE_ADJUSTMENT_RIGHT_ARM_Z_NEGATIVE = 77;
    public static final int POSE_ADJUSTMENT_RIGHT_ARM_Z_POSITIVE = 76;
    public static final int POSE_ADJUSTMENT_LEFT_ARM_X_NEGATIVE = 78;
    public static final int POSE_ADJUSTMENT_LEFT_ARM_X_POSITIVE = 79;
    public static final int POSE_ADJUSTMENT_LEFT_ARM_Y_NEGATIVE = 81;
    public static final int POSE_ADJUSTMENT_LEFT_ARM_Y_POSITIVE = 80;
    public static final int POSE_ADJUSTMENT_LEFT_ARM_Z_NEGATIVE = 82;
    public static final int POSE_ADJUSTMENT_LEFT_ARM_Z_POSITIVE = 83;
    public static final int POSE_ADJUSTMENT_RIGHT_LEG_X_NEGATIVE = 84;
    public static final int POSE_ADJUSTMENT_RIGHT_LEG_X_POSITIVE = 85;
    public static final int POSE_ADJUSTMENT_RIGHT_LEG_Y_NEGATIVE = 87;
    public static final int POSE_ADJUSTMENT_RIGHT_LEG_Y_POSITIVE = 86;
    public static final int POSE_ADJUSTMENT_RIGHT_LEG_Z_NEGATIVE = 89;
    public static final int POSE_ADJUSTMENT_RIGHT_LEG_Z_POSITIVE = 88;
    public static final int POSE_ADJUSTMENT_LEFT_LEG_X_NEGATIVE = 90;
    public static final int POSE_ADJUSTMENT_LEFT_LEG_X_POSITIVE = 91;
    public static final int POSE_ADJUSTMENT_LEFT_LEG_Y_NEGATIVE = 92;
    public static final int POSE_ADJUSTMENT_LEFT_LEG_Y_POSITIVE = 93;
    public static final int POSE_ADJUSTMENT_LEFT_LEG_Z_NEGATIVE = 94;
    public static final int POSE_ADJUSTMENT_LEFT_LEG_Z_POSITIVE = 95;
    private static final int[] POSE_ADJUSTMENT_HEAD = new int[]{
            POSE_ADJUSTMENT_HEAD_X_NEGATIVE,
            POSE_ADJUSTMENT_HEAD_X_POSITIVE,
            POSE_ADJUSTMENT_HEAD_Y_NEGATIVE,
            POSE_ADJUSTMENT_HEAD_Y_POSITIVE,
            POSE_ADJUSTMENT_HEAD_Z_NEGATIVE,
            POSE_ADJUSTMENT_HEAD_Z_POSITIVE
    };
    private static final int[] POSE_ADJUSTMENT_BODY = new int[]{
            POSE_ADJUSTMENT_BODY_X_POSITIVE,
            POSE_ADJUSTMENT_BODY_X_NEGATIVE,
            POSE_ADJUSTMENT_BODY_Y_NEGATIVE,
            POSE_ADJUSTMENT_BODY_Y_POSITIVE,
            POSE_ADJUSTMENT_BODY_Z_NEGATIVE,
            POSE_ADJUSTMENT_BODY_Z_POSITIVE
    };
    private static final int[] POSE_ADJUSTMENT_RIGHT_ARM = new int[]{
            POSE_ADJUSTMENT_RIGHT_ARM_X_NEGATIVE,
            POSE_ADJUSTMENT_RIGHT_ARM_X_POSITIVE,
            POSE_ADJUSTMENT_RIGHT_ARM_Y_NEGATIVE,
            POSE_ADJUSTMENT_RIGHT_ARM_Y_POSITIVE,
            POSE_ADJUSTMENT_RIGHT_ARM_Z_POSITIVE,
            POSE_ADJUSTMENT_RIGHT_ARM_Z_NEGATIVE
    };
    private static final int[] POSE_ADJUSTMENT_LEFT_ARM = new int[]{
            POSE_ADJUSTMENT_LEFT_ARM_X_NEGATIVE,
            POSE_ADJUSTMENT_LEFT_ARM_X_POSITIVE,
            POSE_ADJUSTMENT_LEFT_ARM_Y_POSITIVE,
            POSE_ADJUSTMENT_LEFT_ARM_Y_NEGATIVE,
            POSE_ADJUSTMENT_LEFT_ARM_Z_NEGATIVE,
            POSE_ADJUSTMENT_LEFT_ARM_Z_POSITIVE
    };
    private static final int[] POSE_ADJUSTMENT_RIGHT_LEG = new int[]{
            POSE_ADJUSTMENT_RIGHT_LEG_X_NEGATIVE,
            POSE_ADJUSTMENT_RIGHT_LEG_X_POSITIVE,
            POSE_ADJUSTMENT_RIGHT_LEG_Y_POSITIVE,
            POSE_ADJUSTMENT_RIGHT_LEG_Y_NEGATIVE,
            POSE_ADJUSTMENT_RIGHT_LEG_Z_POSITIVE,
            POSE_ADJUSTMENT_RIGHT_LEG_Z_NEGATIVE
    };
    private static final int[] POSE_ADJUSTMENT_LEFT_LEG = new int[]{
            POSE_ADJUSTMENT_LEFT_LEG_X_NEGATIVE,
            POSE_ADJUSTMENT_LEFT_LEG_X_POSITIVE,
            POSE_ADJUSTMENT_LEFT_LEG_Y_NEGATIVE,
            POSE_ADJUSTMENT_LEFT_LEG_Y_POSITIVE,
            POSE_ADJUSTMENT_LEFT_LEG_Z_NEGATIVE,
            POSE_ADJUSTMENT_LEFT_LEG_Z_POSITIVE
    };
    private static final NavigableMap<Double, Integer> NUDGE_POSITIONS_X_NEGATIVE = ImmutableSortedMap.of(1.0 / 16.0,
            NUDGE_POSITION_X1_NEGATIVE,
            3.0 / 16.0,
            NUDGE_POSITION_X3_NEGATIVE,
            8.0 / 16.0,
            NUDGE_POSITION_X8_NEGATIVE);
    private static final NavigableMap<Double, Integer> NUDGE_POSITIONS_X_POSITIVE = ImmutableSortedMap.of(1.0 / 16.0,
            NUDGE_POSITION_X1_POSITIVE,
            3.0 / 16.0,
            NUDGE_POSITION_X3_POSITIVE,
            8.0 / 16.0,
            NUDGE_POSITION_X8_POSITIVE);
    private static final NavigableMap<Double, Integer> NUDGE_POSITIONS_Y_NEGATIVE = ImmutableSortedMap.of(1.0 / 16.0,
            NUDGE_POSITION_Y1_NEGATIVE,
            3.0 / 16.0,
            NUDGE_POSITION_Y3_NEGATIVE,
            8.0 / 16.0,
            NUDGE_POSITION_Y8_NEGATIVE);
    private static final NavigableMap<Double, Integer> NUDGE_POSITIONS_Y_POSITIVE = ImmutableSortedMap.of(1.0 / 16.0,
            NUDGE_POSITION_Y1_POSITIVE,
            3.0 / 16.0,
            NUDGE_POSITION_Y3_POSITIVE,
            8.0 / 16.0,
            NUDGE_POSITION_Y8_POSITIVE);
    private static final NavigableMap<Double, Integer> NUDGE_POSITIONS_Z_NEGATIVE = ImmutableSortedMap.of(1.0 / 16.0,
            NUDGE_POSITION_Z1_NEGATIVE,
            3.0 / 16.0,
            NUDGE_POSITION_Z3_NEGATIVE,
            8.0 / 16.0,
            NUDGE_POSITION_Z8_NEGATIVE);
    private static final NavigableMap<Double, Integer> NUDGE_POSITIONS_Z_POSITIVE = ImmutableSortedMap.of(1.0 / 16.0,
            NUDGE_POSITION_Z1_POSITIVE,
            3.0 / 16.0,
            NUDGE_POSITION_Z3_POSITIVE,
            8.0 / 16.0,
            NUDGE_POSITION_Z8_POSITIVE);
    private static final NavigableMap<Float, Integer> ADJUST_ROTATION_ANGLE_STEPS = ImmutableSortedMap.of(1.0F,
            ADJUST_ROTATION_ANGLE_STEP_1,
            5.0F,
            ADJUST_ROTATION_ANGLE_STEP_5,
            15.0F,
            ADJUST_ROTATION_ANGLE_STEP_15,
            45.0F,
            ADJUST_ROTATION_ANGLE_STEP_45);

    public VanillaTweaksDataSyncHandler(ArmorStandHolder holder, LocalPlayer player) {
        super(holder, player);
    }

    @Override
    public void sendPose(ArmorStandPose pose, boolean finalize) {
        if (!this.isEditingAllowed()) return;
        int triggerValue = this.getTriggerValueFromPose(pose);
        if (triggerValue != -1) {
            if (this.enqueueTriggerValue(triggerValue)) {
                this.lastSyncedPose = pose.copyAndFillFrom(this.lastSyncedPose);
                pose.applyToEntity(this.getArmorStand());
            }
        } else {
            this.tryApplyAllPoseParts(pose);
        }
        if (finalize) this.finalizeCurrentOperation();
    }

    private int getTriggerValueFromPose(ArmorStandPose pose) {
        if (pose.getSourceType() == ArmorStandPose.SourceType.EMPTY) return POSE_PRESETS_ATTENTION;
        if (pose.getSourceType() == ArmorStandPose.SourceType.MIRRORED) return MIRROR_AND_FLIP_FLIP;
        if (pose.getSourceType() != ArmorStandPose.SourceType.VANILLA_TWEAKS) return -1;
        if (pose == ArmorStandPose.WALKING) return POSE_PRESETS_WALKING;
        if (pose == ArmorStandPose.RUNNING) return POSE_PRESETS_RUNNING;
        if (pose == ArmorStandPose.POINTING) return POSE_PRESETS_POINTING;
        if (pose == ArmorStandPose.BLOCKING) return POSE_PRESETS_BLOCKING;
        if (pose == ArmorStandPose.LUNGEING) return POSE_PRESETS_LUNGEING;
        if (pose == ArmorStandPose.WINNING) return POSE_PRESETS_WINNING;
        if (pose == ArmorStandPose.SITTING) return POSE_PRESETS_SITTING;
        if (pose == ArmorStandPose.ARABESQUE) return POSE_PRESETS_ARABESQUE;
        if (pose == ArmorStandPose.CUPID) return POSE_PRESETS_CUPID;
        if (pose == ArmorStandPose.CONFIDENT) return POSE_PRESETS_CONFIDENT;
        if (pose == ArmorStandPose.SALUTE) return POSE_PRESETS_SALUTE;
        if (pose == ArmorStandPose.DEATH) return POSE_PRESETS_DEATH;
        if (pose == ArmorStandPose.FACEPALM) return POSE_PRESETS_FACEPALM;
        if (pose == ArmorStandPose.LAZING) return POSE_PRESETS_LAZING;
        if (pose == ArmorStandPose.CONFUSED) return POSE_PRESETS_CONFUSED;
        if (pose == ArmorStandPose.FORMAL) return POSE_PRESETS_FORMAL;
        if (pose == ArmorStandPose.SAD) return POSE_PRESETS_SAD;
        if (pose == ArmorStandPose.JOYOUS) return POSE_PRESETS_JOYOUS;
        if (pose == ArmorStandPose.STARGAZING) return POSE_PRESETS_STARGAZING;
        return -1;
    }

    private void tryApplyAllPoseParts(ArmorStandPose pose) {
        if (!this.tryApplyPosePart(this.lastSyncedPose.getHeadPose(),
                pose.getNullableHeadPose(),
                POSE_ADJUSTMENT_HEAD,
                this.lastSyncedPose::withHeadPose)) {
            return;
        }
        if (!this.tryApplyPosePart(this.lastSyncedPose.getBodyPose(),
                pose.getNullableBodyPose(),
                POSE_ADJUSTMENT_BODY,
                this.lastSyncedPose::withBodyPose)) {
            return;
        }
        if (!this.tryApplyPosePart(this.lastSyncedPose.getRightArmPose(),
                pose.getNullableRightArmPose(),
                POSE_ADJUSTMENT_RIGHT_ARM,
                this.lastSyncedPose::withRightArmPose)) {
            return;
        }
        if (!this.tryApplyPosePart(this.lastSyncedPose.getLeftArmPose(),
                pose.getNullableLeftArmPose(),
                POSE_ADJUSTMENT_LEFT_ARM,
                this.lastSyncedPose::withLeftArmPose)) {
            return;
        }
        if (!this.tryApplyPosePart(this.lastSyncedPose.getRightLegPose(),
                pose.getNullableRightLegPose(),
                POSE_ADJUSTMENT_RIGHT_LEG,
                this.lastSyncedPose::withRightLegPose)) {
            return;
        }
        if (!this.tryApplyPosePart(this.lastSyncedPose.getLeftLegPose(),
                pose.getNullableLeftLegPose(),
                POSE_ADJUSTMENT_LEFT_LEG,
                this.lastSyncedPose::withLeftLegPose)) {
            return;
        }
    }

    private boolean tryApplyPosePart(Rotations oldPose, @Nullable Rotations newPose, int[] poseAdjustment, Function<Rotations, ArmorStandPose> function) {
        if (this.tryApplyPoseAdjustment(oldPose, newPose, poseAdjustment)) {
            this.lastSyncedPose = function.apply(newPose != null ? newPose : oldPose);
            return true;
        } else {
            return false;
        }
    }

    private boolean tryApplyPoseAdjustment(Rotations oldPose, @Nullable Rotations newPose, int[] poseAdjustment) {
        if (newPose == null || oldPose.equals(newPose)) {
            return true;
        } else if (!this.applyIncrementsFromSteps(oldPose.x(), newPose.x(), poseAdjustment[0], poseAdjustment[1])) {
            return false;
        } else if (!this.applyIncrementsFromSteps(oldPose.y(), newPose.y(), poseAdjustment[2], poseAdjustment[3])) {
            return false;
        } else if (!this.applyIncrementsFromSteps(oldPose.z(), newPose.z(), poseAdjustment[4], poseAdjustment[5])) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void sendPosition(double posX, double posY, double posZ, boolean finalize) {
        if (!this.isEditingAllowed()) return;
        this.applyPositionIncrements(this.getArmorStand().getX(),
                posX,
                NUDGE_POSITIONS_X_POSITIVE,
                NUDGE_POSITIONS_X_NEGATIVE);
        this.applyPositionIncrements(this.getArmorStand().getY(),
                posY,
                NUDGE_POSITIONS_Y_POSITIVE,
                NUDGE_POSITIONS_Y_NEGATIVE);
        this.applyPositionIncrements(this.getArmorStand().getZ(),
                posZ,
                NUDGE_POSITIONS_Z_POSITIVE,
                NUDGE_POSITIONS_Z_NEGATIVE);
        if (finalize) this.finalizeCurrentOperation();
    }

    private void applyPositionIncrements(double oldValue, double newValue, NavigableMap<Double, Integer> positiveNudgePositions, NavigableMap<Double, Integer> negativeNudgePositions) {
        double value = newValue - oldValue;
        double signum = Math.signum(value);
        value = Math.abs(value);
        for (int i = 0; i < MAX_INCREMENTAL_OPERATIONS; i++) {
            Map.Entry<Double, Integer> entry = (signum == -1.0F ? negativeNudgePositions :
                    positiveNudgePositions).floorEntry(value);
            if (entry != null) {
                value -= entry.getKey();
                if (!this.enqueueTriggerValue(entry.getValue())) {
                    return;
                }
            } else {
                break;
            }
        }
    }

    @Override
    public void sendRotation(float rotation, boolean finalize) {
        if (!this.isEditingAllowed()) return;
        this.applyIncrementsFromSteps(this.getArmorStand().getYRot(),
                rotation,
                ADJUST_ROTATION_ROTATE_RIGHT,
                ADJUST_ROTATION_ROTATE_LEFT);
        if (finalize) this.finalizeCurrentOperation();
    }

    private boolean applyIncrementsFromSteps(float oldValue, float newValue, int triggerValueNegative, int triggerValuePositive) {
        float value = newValue - oldValue;
        float signum = Math.signum(value);
        value = Math.abs(value);
        float lastIncrement = 0.0F;
        for (int i = 0; i < MAX_INCREMENTAL_OPERATIONS; i++) {
            Map.Entry<Float, Integer> entry = ADJUST_ROTATION_ANGLE_STEPS.floorEntry(value);
            if (entry != null) {
                float currentIncrement = entry.getKey();
                value -= currentIncrement;
                if (currentIncrement != lastIncrement) {
                    lastIncrement = currentIncrement;
                    if (!this.enqueueTriggerValue(entry.getValue())) {
                        return false;
                    }
                }
                if (!this.enqueueTriggerValue(signum == -1.0F ? triggerValuePositive : triggerValueNegative)) {
                    return false;
                }
            } else {
                break;
            }
        }
        return true;
    }

    @Override
    public void sendStyleOption(ArmorStandStyleOption styleOption, boolean value, boolean finalize) {
        if (!this.isEditingAllowed()) return;
        int triggerValue;
        if (styleOption == ArmorStandStyleOptions.SHOW_NAME) {
            triggerValue = value ? DISPLAY_NAME_YES : DISPLAY_NAME_NO;
        } else if (styleOption == ArmorStandStyleOptions.SHOW_ARMS) {
            triggerValue = value ? SHOW_ARMS_YES : SHOW_ARMS_NO;
        } else if (styleOption == ArmorStandStyleOptions.SMALL) {
            triggerValue = value ? SMALL_STAND_YES : SMALL_STAND_NO;
        } else if (styleOption == ArmorStandStyleOptions.INVISIBLE) {
            triggerValue = value ? STAND_VISIBLE_NO : STAND_VISIBLE_YES;
        } else if (styleOption == ArmorStandStyleOptions.NO_BASE_PLATE) {
            triggerValue = value ? SHOW_BASE_PLATE_NO : SHOW_BASE_PLATE_YES;
        } else if (styleOption == ArmorStandStyleOptions.NO_GRAVITY) {
            triggerValue = value ? APPLY_GRAVITY_NO : APPLY_GRAVITY_YES;
        } else {
            super.sendStyleOption(styleOption, value, finalize);
            return;
        }
        if (this.sendSingleTriggerValue(triggerValue, finalize)) {
            styleOption.setOption(this.getArmorStand(), value);
        }
    }

    @Override
    public void sendAlignment(ArmorStandAlignment alignment) {
        if (!this.isEditingAllowed()) return;
        int triggerValue = switch (alignment) {
            case BLOCK -> AUTO_ALIGNMENT_BLOCK_ON_SURFACE;
            case FLOATING_ITEM -> AUTO_ALIGNMENT_ITEM_ON_SURFACE;
            case FLAT_ITEM -> AUTO_ALIGNMENT_ITEM_FLAT_ON_SURFACE;
            case TOOL -> AUTO_ALIGNMENT_TOOL_FLAT_ON_SURFACE;
        };
        this.sendSingleTriggerValue(triggerValue, true);
    }

    public void sendSingleTriggerValue(int triggerValue) {
        if (!this.isEditingAllowed()) return;
        this.sendSingleTriggerValue(triggerValue, true);
    }

    private boolean sendSingleTriggerValue(int triggerValue, boolean finalize) {
        boolean result = this.enqueueTriggerValue(triggerValue);
        if (finalize) this.finalizeCurrentOperation();
        return result;
    }

    @Override
    public ArmorStandScreenType[] getScreenTypes() {
        return Stream.concat(Stream.of(super.getScreenTypes()), Stream.of(ModRegistry.VANILLA_TWEAKS_SCREEN_TYPE))
                .toArray(ArmorStandScreenType[]::new);
    }

    @Override
    protected boolean isEditingAllowed() {
        return this.isEditingAllowed(false);
    }

    @Override
    protected Either<Component, Unit> testArmorStand(ArmorStand armorStand) {
        return super.testArmorStand(armorStand).<Optional<Component>>map(Optional::of, $ -> {
            if (this.player.distanceToSqr(armorStand) < 9.0) {
                return Optional.empty();
            } else {
                return Optional.of(Component.translatable(OUT_OF_RANGE_TRANSLATION_KEY));
            }
        }).<Either<Component, Unit>>map(Either::left).orElse(Either.right(Unit.INSTANCE));
    }

    @Override
    protected int getDequeueDelayTicks() {
        return ArmorStatues.CONFIG.get(ClientConfig.class).clientCommandDelay;
    }

    private boolean enqueueTriggerValue(int triggerValue) {
        return this.enqueueClientCommand("trigger as_trigger set %s".formatted(triggerValue));
    }
}
