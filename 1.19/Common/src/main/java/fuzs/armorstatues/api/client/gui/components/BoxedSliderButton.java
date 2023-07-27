package fuzs.armorstatues.api.client.gui.components;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.api.client.gui.screens.armorstand.AbstractArmorStandScreen;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandPose;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.function.Consumer;
import java.util.function.DoubleSupplier;

public abstract class BoxedSliderButton extends AbstractWidget implements UnboundedSliderButton, LiveSliderButton {
    static final double VALUE_KEY_INTERVAL = 0.035;
    private static final int SLIDER_SIZE = 13;
    
    private final DoubleSupplier currentHorizontalValue;
    private final DoubleSupplier currentVerticalValue;
    protected final OnTooltip onTooltip;
    protected double horizontalValue;
    protected double verticalValue;

    public BoxedSliderButton(int x, int y, DoubleSupplier currentHorizontalValue, DoubleSupplier currentVerticalValue) {
        this(x, y, currentHorizontalValue, currentVerticalValue, (button, poseStack, mouseX, mouseY) -> {});
    }

    public BoxedSliderButton(int x, int y, DoubleSupplier currentHorizontalValue, DoubleSupplier currentVerticalValue, OnTooltip onTooltip) {
        super(x, y, 54, 54, CommonComponents.EMPTY);
        this.onTooltip = onTooltip;
        this.currentHorizontalValue = currentHorizontalValue;
        this.currentVerticalValue = currentVerticalValue;
        this.refreshValues();
    }

    @Override
    public void refreshValues() {
        this.horizontalValue = this.currentHorizontalValue.getAsDouble();
        this.verticalValue = this.currentVerticalValue.getAsDouble();
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
        if (this.active) {
            if (this.isFocused()) {
                narrationElementOutput.add(NarratedElementType.USAGE, Component.translatable("narration.slider.usage.focused"));
            } else {
                narrationElementOutput.add(NarratedElementType.USAGE, Component.translatable("narration.slider.usage.hovered"));
            }
            this.onTooltip.narrateTooltip((component) -> {
                narrationElementOutput.add(NarratedElementType.HINT, component);
            });
        }
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, AbstractArmorStandScreen.getArmorStandWidgetsLocation());
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        final int sliderX = (int) (this.horizontalValue * (double) (this.width - SLIDER_SIZE - 2));
        final int sliderY = (int) (this.verticalValue * (double) (this.height - SLIDER_SIZE - 2));
        boolean hoveredOrFocused = this.isHoveredOrFocused();
        boolean horizontalValueLocked = this.horizontalValueLocked();
        boolean verticalValueLocked = this.verticalValueLocked();
        if (!this.active || !hoveredOrFocused || !horizontalValueLocked && !verticalValueLocked) {
            this.blit(poseStack, this.x, this.y, 0, 120, this.width, this.height);
        } else if (horizontalValueLocked && verticalValueLocked) {
            this.blit(poseStack, this.x + sliderX, this.y + sliderY, 164, 0, SLIDER_SIZE + 2, SLIDER_SIZE + 2);
        } else if (horizontalValueLocked) {
            this.blit(poseStack, this.x + sliderX, this.y, 54, 120, SLIDER_SIZE + 2, this.height);
        } else {
            this.blit(poseStack, this.x, this.y + sliderY, 136, 49, this.width, SLIDER_SIZE + 2);
        }
        int i = this.getYImage(hoveredOrFocused);
        this.blit(poseStack, this.x + 1 + sliderX, this.y + 1 + sliderY, 151, i * SLIDER_SIZE, SLIDER_SIZE, SLIDER_SIZE);
        if (hoveredOrFocused) {
            this.renderToolTip(poseStack, mouseX, mouseY);
        }
    }

    @Override
    public void renderToolTip(PoseStack poseStack, int relativeMouseX, int relativeMouseY) {
        this.onTooltip.onTooltip(this, poseStack, relativeMouseX, relativeMouseY);
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.setValueFromMouse(mouseX, mouseY);
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
        this.setValueFromMouse(mouseX, mouseY);
    }

    private void setValueFromMouse(double mouseX, double mouseY) {
        this.setHorizontalValue((mouseX - (double) (this.x + 8)) / (double) (this.width - SLIDER_SIZE - 2), true);
        this.setVerticalValue((mouseY - (double) (this.y + 8)) / (double) (this.height - SLIDER_SIZE - 2), true);
    }

    private void setHorizontalValue(double horizontalValue, boolean snapValue) {
        double oldHorizontalValue = this.horizontalValue;
        if (!this.horizontalValueLocked()) {
            this.horizontalValue = Mth.clamp(horizontalValue, 0.0, 1.0);
            if (snapValue) {
                this.horizontalValue = ArmorStandPose.snapValue(this.horizontalValue, ArmorStandPose.DEGREES_SNAP_INTERVAL);
            }
        }
        if (oldHorizontalValue != this.horizontalValue) {
            this.applyValue();
        }
    }

    private void setVerticalValue(double verticalValue, boolean snapValue) {
        double oldVerticalValue = this.verticalValue;
        if (!this.verticalValueLocked()) {
            this.verticalValue = Mth.clamp(verticalValue, 0.0, 1.0);
            if (snapValue) {
                this.verticalValue = ArmorStandPose.snapValue(this.verticalValue, ArmorStandPose.DEGREES_SNAP_INTERVAL);
            }
        }
        if (oldVerticalValue != this.verticalValue) {
            this.applyValue();
        }
    }

    protected boolean verticalValueLocked() {
        return Screen.hasShiftDown();
    }

    protected boolean horizontalValueLocked() {
        return Screen.hasAltDown();
    }

    @Override
    public void playDownSound(SoundManager handler) {

    }

    @Override
    public void onRelease(double mouseX, double mouseY) {
        super.playDownSound(Minecraft.getInstance().getSoundManager());
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.active && this.visible) {
            switch (keyCode) {
                case InputConstants.KEY_LEFT -> {
                    this.setHorizontalValue(this.horizontalValue - VALUE_KEY_INTERVAL, false);
                    return true;
                }
                case InputConstants.KEY_RIGHT -> {
                    this.setHorizontalValue(this.horizontalValue + VALUE_KEY_INTERVAL, false);
                    return true;
                }
                case InputConstants.KEY_UP -> {
                    this.setVerticalValue(this.verticalValue - VALUE_KEY_INTERVAL, false);
                    return true;
                }
                case InputConstants.KEY_DOWN -> {
                    this.setVerticalValue(this.verticalValue + VALUE_KEY_INTERVAL, false);
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    protected abstract void applyValue();

    public interface OnTooltip {

        void onTooltip(BoxedSliderButton button, PoseStack poseStack, int mouseX, int mouseY);

        default void narrateTooltip(Consumer<Component> contents) {

        }
    }
}
