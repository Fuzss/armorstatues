package fuzs.armorstatues.api.network.client.data;

import com.google.common.collect.ImmutableSortedMap;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandPose;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandStyleOption;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandStyleOptions;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Rotations;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.NavigableMap;

public class VanillaTweaksDataSyncHandler extends CommandDataSyncHandler {
    private static final int MAX_INCREMENTAL_OPERATIONS = 12;
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
    private static final int[] POSE_ADJUSTMENT_HEAD = new int[]{POSE_ADJUSTMENT_HEAD_X_NEGATIVE, POSE_ADJUSTMENT_HEAD_X_POSITIVE, POSE_ADJUSTMENT_HEAD_Y_NEGATIVE, POSE_ADJUSTMENT_HEAD_Y_POSITIVE, POSE_ADJUSTMENT_HEAD_Z_NEGATIVE, POSE_ADJUSTMENT_HEAD_Z_POSITIVE};
    private static final int[] POSE_ADJUSTMENT_BODY = new int[]{POSE_ADJUSTMENT_BODY_X_POSITIVE, POSE_ADJUSTMENT_BODY_X_NEGATIVE, POSE_ADJUSTMENT_BODY_Y_NEGATIVE, POSE_ADJUSTMENT_BODY_Y_POSITIVE, POSE_ADJUSTMENT_BODY_Z_NEGATIVE, POSE_ADJUSTMENT_BODY_Z_POSITIVE};
    private static final int[] POSE_ADJUSTMENT_RIGHT_ARM = new int[]{POSE_ADJUSTMENT_RIGHT_ARM_X_NEGATIVE, POSE_ADJUSTMENT_RIGHT_ARM_X_POSITIVE, POSE_ADJUSTMENT_RIGHT_ARM_Y_NEGATIVE, POSE_ADJUSTMENT_RIGHT_ARM_Y_POSITIVE, POSE_ADJUSTMENT_RIGHT_ARM_Z_POSITIVE, POSE_ADJUSTMENT_RIGHT_ARM_Z_NEGATIVE};
    private static final int[] POSE_ADJUSTMENT_LEFT_ARM = new int[]{POSE_ADJUSTMENT_LEFT_ARM_X_NEGATIVE, POSE_ADJUSTMENT_LEFT_ARM_X_POSITIVE, POSE_ADJUSTMENT_LEFT_ARM_Y_POSITIVE, POSE_ADJUSTMENT_LEFT_ARM_Y_NEGATIVE, POSE_ADJUSTMENT_LEFT_ARM_Z_NEGATIVE, POSE_ADJUSTMENT_LEFT_ARM_Z_POSITIVE};
    private static final int[] POSE_ADJUSTMENT_RIGHT_LEG = new int[]{POSE_ADJUSTMENT_RIGHT_LEG_X_NEGATIVE, POSE_ADJUSTMENT_RIGHT_LEG_X_POSITIVE, POSE_ADJUSTMENT_RIGHT_LEG_Y_POSITIVE, POSE_ADJUSTMENT_RIGHT_LEG_Y_NEGATIVE, POSE_ADJUSTMENT_RIGHT_LEG_Z_POSITIVE, POSE_ADJUSTMENT_RIGHT_LEG_Z_NEGATIVE};
    private static final int[] POSE_ADJUSTMENT_LEFT_LEG = new int[]{POSE_ADJUSTMENT_LEFT_LEG_X_NEGATIVE, POSE_ADJUSTMENT_LEFT_LEG_X_POSITIVE, POSE_ADJUSTMENT_LEFT_LEG_Y_NEGATIVE, POSE_ADJUSTMENT_LEFT_LEG_Y_POSITIVE, POSE_ADJUSTMENT_LEFT_LEG_Z_NEGATIVE, POSE_ADJUSTMENT_LEFT_LEG_Z_POSITIVE};
    private static final NavigableMap<Double, Integer> NUDGE_POSITIONS_X_NEGATIVE = ImmutableSortedMap.of(1.0 / 16.0, NUDGE_POSITION_X1_NEGATIVE, 3.0 / 16.0, NUDGE_POSITION_X3_NEGATIVE, 8.0 / 16.0, NUDGE_POSITION_X8_NEGATIVE);
    private static final NavigableMap<Double, Integer> NUDGE_POSITIONS_X_POSITIVE = ImmutableSortedMap.of(1.0 / 16.0, NUDGE_POSITION_X1_POSITIVE, 3.0 / 16.0, NUDGE_POSITION_X3_POSITIVE, 8.0 / 16.0, NUDGE_POSITION_X8_POSITIVE);
    private static final NavigableMap<Double, Integer> NUDGE_POSITIONS_Y_NEGATIVE = ImmutableSortedMap.of(1.0 / 16.0, NUDGE_POSITION_Y1_NEGATIVE, 3.0 / 16.0, NUDGE_POSITION_Y3_NEGATIVE, 8.0 / 16.0, NUDGE_POSITION_Y8_NEGATIVE);
    private static final NavigableMap<Double, Integer> NUDGE_POSITIONS_Y_POSITIVE = ImmutableSortedMap.of(1.0 / 16.0, NUDGE_POSITION_Y1_POSITIVE, 3.0 / 16.0, NUDGE_POSITION_Y3_POSITIVE, 8.0 / 16.0, NUDGE_POSITION_Y8_POSITIVE);
    private static final NavigableMap<Double, Integer> NUDGE_POSITIONS_Z_NEGATIVE = ImmutableSortedMap.of(1.0 / 16.0, NUDGE_POSITION_Z1_NEGATIVE, 3.0 / 16.0, NUDGE_POSITION_Z3_NEGATIVE, 8.0 / 16.0, NUDGE_POSITION_Z8_NEGATIVE);
    private static final NavigableMap<Double, Integer> NUDGE_POSITIONS_Z_POSITIVE = ImmutableSortedMap.of(1.0 / 16.0, NUDGE_POSITION_Z1_POSITIVE, 3.0 / 16.0, NUDGE_POSITION_Z3_POSITIVE, 8.0 / 16.0, NUDGE_POSITION_Z8_POSITIVE);
    private static final NavigableMap<Float, Integer> ADJUST_ROTATION_ANGLE_STEPS = ImmutableSortedMap.of(1.0F, ADJUST_ROTATION_ANGLE_STEP_1, 5.0F, ADJUST_ROTATION_ANGLE_STEP_5, 15.0F, ADJUST_ROTATION_ANGLE_STEP_15, 45.0F, ADJUST_ROTATION_ANGLE_STEP_45);

    public VanillaTweaksDataSyncHandler(ArmorStand armorStand, LocalPlayer player) {
        super(armorStand, player);
    }

    @Override
    public void sendPose(ArmorStandPose pose, boolean finalize) {
        $1: {
            if (!this.tryApplyPoseIncrements(this.lastSyncedPose.getHeadPose(), pose.getNullableHeadPose(), POSE_ADJUSTMENT_HEAD)) break $1;
            if (!this.tryApplyPoseIncrements(this.lastSyncedPose.getBodyPose(), pose.getNullableBodyPose(), POSE_ADJUSTMENT_BODY)) break $1;
            if (!this.tryApplyPoseIncrements(this.lastSyncedPose.getRightArmPose(), pose.getNullableRightArmPose(), POSE_ADJUSTMENT_RIGHT_ARM)) break $1;
            if (!this.tryApplyPoseIncrements(this.lastSyncedPose.getLeftArmPose(), pose.getNullableLeftArmPose(), POSE_ADJUSTMENT_LEFT_ARM)) break $1;
            if (!this.tryApplyPoseIncrements(this.lastSyncedPose.getRightLegPose(), pose.getNullableRightLegPose(), POSE_ADJUSTMENT_RIGHT_LEG)) break $1;
            if (!this.tryApplyPoseIncrements(this.lastSyncedPose.getLeftLegPose(), pose.getNullableLeftLegPose(), POSE_ADJUSTMENT_LEFT_LEG)) break $1;
        }
        this.lastSyncedPose = pose.copyAndFillFrom(this.lastSyncedPose);
        if (finalize) this.finalizeCurrentOperation();
    }

    private boolean tryApplyPoseIncrements(Rotations oldPose, @Nullable Rotations newPose, int[] poseAdjustment) {
        if (newPose == null || oldPose.equals(newPose)) return true;
        if (!this.applyIncrementsFromSteps(oldPose.getX(), newPose.getX(), poseAdjustment[0], poseAdjustment[1])) return false;
        if (!this.applyIncrementsFromSteps(oldPose.getY(), newPose.getY(), poseAdjustment[2], poseAdjustment[3])) return false;
        if (!this.applyIncrementsFromSteps(oldPose.getZ(), newPose.getZ(), poseAdjustment[4], poseAdjustment[5])) return false;
        return true;
    }

    @Override
    public void sendPosition(double posX, double posY, double posZ, boolean finalize) {
        this.applyPositionIncrements(this.getArmorStand().getX(), posX, NUDGE_POSITIONS_X_POSITIVE, NUDGE_POSITIONS_X_NEGATIVE);
        this.applyPositionIncrements(this.getArmorStand().getY(), posY, NUDGE_POSITIONS_Y_POSITIVE, NUDGE_POSITIONS_Y_NEGATIVE);
        this.applyPositionIncrements(this.getArmorStand().getZ(), posZ, NUDGE_POSITIONS_Z_POSITIVE, NUDGE_POSITIONS_Z_NEGATIVE);
        if (finalize) this.finalizeCurrentOperation();
    }

    private void applyPositionIncrements(double oldValue, double newValue, NavigableMap<Double, Integer> positiveNudgePositions, NavigableMap<Double, Integer> negativeNudgePositions) {
        double value = newValue - oldValue;
        double signum = Math.signum(value);
        value = Math.abs(value);
        for (int i = 0; i < MAX_INCREMENTAL_OPERATIONS; i++) {
            Map.Entry<Double, Integer> entry = (signum == -1.0F ? negativeNudgePositions : positiveNudgePositions).floorEntry(value);
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
        this.applyIncrementsFromSteps(this.getArmorStand().getYRot(), rotation, ADJUST_ROTATION_ROTATE_RIGHT, ADJUST_ROTATION_ROTATE_LEFT);
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
        this.enqueueTriggerValue(triggerValue);
        if (finalize) this.finalizeCurrentOperation();
    }

    @Override
    protected int getDequeueDelayTicks() {
        return 20;
    }

    private boolean enqueueTriggerValue(int triggerValue) {
        return this.enqueueClientCommand("trigger as_trigger set %s".formatted(triggerValue));
    }
}
