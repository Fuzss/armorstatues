package fuzs.armorstatues.api.network.client.data;

import fuzs.armorstatues.api.world.inventory.data.ArmorStandStyleOption;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandStyleOptions;
import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
import it.unimi.dsi.fastutil.ints.IntPriorityQueue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.decoration.ArmorStand;

public class VanillaTweaksDataSyncHandler extends CommandDataSyncHandler {
    private static final Component NOT_FINISHED_COMPONENT = Component.translatable("armorstatues.screen.failure.notFinished");
    private static final Component FINISHED_COMPONENT = Component.translatable("armorstatues.screen.finished");
    private static final int DEFAULT_COMMAND_SEND_TICKS = 20;
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
    public static final int ADJUST_ROTATION_ANGLE_STEP_45 = 120;
    public static final int ADJUST_ROTATION_ANGLE_STEP_15 = 121;
    public static final int ADJUST_ROTATION_ANGLE_STEP_5 = 122;
    public static final int ADJUST_ROTATION_ANGLE_STEP_1 = 123;
    public static final int ADJUST_ROTATION_ROTATE_RIGHT = 56;
    public static final int ADJUST_ROTATION_ROTATE_LEFT = 57;

    private static final IntPriorityQueue QUEUE = new IntArrayFIFOQueue();
    private static boolean queueLocked;
    private static int commandSentTicks;

    public VanillaTweaksDataSyncHandler(ArmorStand armorStand, LocalPlayer player) {
        super(armorStand, player);
    }

    @Override
    public void sendRotation(float rotation) {
        this.getCloseWithIncrements(this.getArmorStand().getYRot(), rotation, new float[]{1.0F, 5.0F, 15.0F, 45.0F}, new int[]{ADJUST_ROTATION_ANGLE_STEP_1, ADJUST_ROTATION_ANGLE_STEP_5, ADJUST_ROTATION_ANGLE_STEP_15, ADJUST_ROTATION_ANGLE_STEP_45});
        this.finalizeCurrentOperation();
    }

    private void getCloseWithIncrements(float oldValue, float newValue, float[] increments, int[] triggerValues) {
        float value = newValue - oldValue;
        float signum = Math.signum(value);
        value = Math.abs(value);
        float lastIncrement = 0.0F;
        for (int i = 0; i < MAX_INCREMENTAL_OPERATIONS; i++) {
            if (value >= increments[0]) {
                for (int j = increments.length - 1; j >= 0; j--) {
                    float currentIncrement = increments[j];
                    if (currentIncrement < value) {
                        value -= currentIncrement;
                        if (currentIncrement != lastIncrement) {
                            lastIncrement = currentIncrement;
                            if (!this.enqueueTriggerValue(triggerValues[j])) return;
                        }
                        if (!this.enqueueTriggerValue(signum == -1.0F ? ADJUST_ROTATION_ROTATE_LEFT : ADJUST_ROTATION_ROTATE_RIGHT)) return;
                        break;
                    }
                }
            } else {
                break;
            }
        }
    }

    @Override
    public void sendStyleOption(ArmorStandStyleOption styleOption, boolean value) {
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
            super.sendStyleOption(styleOption, value);
            return;
        }
        if (this.enqueueTriggerValue(triggerValue)) {
            styleOption.setOption(this.getArmorStand(), value);
        }
        this.finalizeCurrentOperation();
    }

    @Override
    public void tick() {
        if (commandSentTicks > 0) commandSentTicks--;
        if (commandSentTicks == 0 && !QUEUE.isEmpty()) {
            this.sendTriggerCommand(QUEUE.dequeueInt());
            if (QUEUE.isEmpty()) {
                this.sendDisplayMessage(FINISHED_COMPONENT, false);
            }
        }
    }

    @Override
    public boolean shouldContinueTicking() {
        return !QUEUE.isEmpty();
    }

    private boolean enqueueTriggerValue(int triggerValue) {
        if (QUEUE.isEmpty()) {
            queueLocked = false;
        } else if (queueLocked) {
            this.sendFailureMessage(NOT_FINISHED_COMPONENT);
            return false;
        }
        QUEUE.enqueue(triggerValue);
        return true;
    }

    private void finalizeCurrentOperation() {
        if (!QUEUE.isEmpty()) {
            queueLocked = true;
            Screen screen = Minecraft.getInstance().screen;
            if (screen != null) screen.onClose();
        }
    }

    private void sendTriggerCommand(int triggerValue) {
        this.player.commandSigned("trigger as_trigger set %s".formatted(triggerValue), null);
        commandSentTicks = DEFAULT_COMMAND_SEND_TICKS;
    }
}
