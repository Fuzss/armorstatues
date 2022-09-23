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
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.function.Consumer;
import java.util.function.DoubleSupplier;

public abstract class VerticalSliderButton extends AbstractWidget implements UnboundedSliderButton, LiveSliderButton {
    private final int sliderSize = 13;
    private final DoubleSupplier currentValue;
    protected final OnTooltip onTooltip;
    protected double value;

    public VerticalSliderButton(int x, int y, DoubleSupplier currentValue) {
        this(x, y, currentValue, (button, poseStack, mouseX, mouseY) -> {});
    }

    public VerticalSliderButton(int x, int y, DoubleSupplier currentValue, OnTooltip onTooltip) {
        super(x, y, 15, 54, CommonComponents.EMPTY);
        this.currentValue = currentValue;
        this.onTooltip = onTooltip;
        this.refreshValues();
    }

    @Override
    public void refreshValues() {
        this.value = this.currentValue.getAsDouble();
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
        RenderSystem.setShaderTexture(0, AbstractArmorStandScreen.ARMOR_STAND_WIDGETS_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(poseStack, this.x, this.y, 54, 120, this.width, this.height);
        int i = this.getYImage(this.isHoveredOrFocused());
        this.blit(poseStack, this.x + 1, this.y + 1 + (int) (this.value * (double) (this.height - this.sliderSize - 2)), 151, i * this.sliderSize, this.sliderSize, this.sliderSize);
        if (this.isHoveredOrFocused()) {
            this.renderToolTip(poseStack, mouseX, mouseY);
        }
    }

    @Override
    public void renderToolTip(PoseStack poseStack, int relativeMouseX, int relativeMouseY) {
        this.onTooltip.onTooltip(this, poseStack, relativeMouseX, relativeMouseY);
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.setValueFromMouse(mouseY);
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
        this.setValueFromMouse(mouseY);
    }

    private void setValueFromMouse(double mouseY) {
        this.setValue((mouseY - (double) (this.y + 8)) / (double) (this.height - this.sliderSize - 2));
    }

    private void setValue(double value) {
        this.setValue(value, true);
    }

    private void setValue(double value, boolean snapValue) {
        double oldValue = this.value;
        this.value = Mth.clamp(value, 0.0, 1.0);
        if (snapValue) {
            this.value = ArmorStandPose.snapValue(this.value, ArmorStandPose.DEGREES_SNAP_INTERVAL);
        }
        if (oldValue != this.value) {
            this.applyValue();
        }
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
                case InputConstants.KEY_UP -> {
                    this.setValue(this.value - BoxedSliderButton.VALUE_KEY_INTERVAL, false);
                    return true;
                }
                case InputConstants.KEY_DOWN -> {
                    this.setValue(this.value + BoxedSliderButton.VALUE_KEY_INTERVAL, false);
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

        void onTooltip(VerticalSliderButton button, PoseStack poseStack, int mouseX, int mouseY);

        default void narrateTooltip(Consumer<Component> contents) {

        }
    }
}
