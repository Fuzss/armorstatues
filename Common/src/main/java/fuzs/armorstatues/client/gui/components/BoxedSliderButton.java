package fuzs.armorstatues.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.client.gui.screens.armorstand.AbstractArmorStandScreen;
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

public abstract class BoxedSliderButton extends AbstractWidget implements UnboundedSliderButton, LiveSliderButton {
    private final int sliderSize = 13;
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
        RenderSystem.setShaderTexture(0, AbstractArmorStandScreen.ARMOR_STAND_WIDGETS_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(poseStack, this.x, this.y, 0, 120, this.width, this.height);
        int i = this.getYImage(this.isHoveredOrFocused());
        this.blit(poseStack, this.x + 1 + (int) (this.horizontalValue * (double) (this.width - this.sliderSize - 2)), this.y + 1 + (int) (this.verticalValue * (double) (this.height - this.sliderSize - 2)), 151, i * this.sliderSize, this.sliderSize, this.sliderSize);
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
        this.setValueFromMouse(mouseX, mouseY);
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
        this.setValueFromMouse(mouseX, mouseY);
    }

    private void setValueFromMouse(double mouseX, double mouseY) {
        this.setValue((mouseX - (double) (this.x + 8)) / (double) (this.width - this.sliderSize - 2), (mouseY - (double) (this.y + 8)) / (double) (this.height - this.sliderSize - 2));
    }

    private void setValue(double horizontalValue, double verticalValue) {
        double oldHorizontalValue = this.horizontalValue;
        this.horizontalValue = Mth.clamp(horizontalValue, 0.0, 1.0);
        double oldVerticalValue = this.verticalValue;
        this.verticalValue = Mth.clamp(verticalValue, 0.0, 1.0);
        if (oldHorizontalValue != this.horizontalValue || oldVerticalValue != this.verticalValue) {
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

    protected abstract void applyValue();

    public interface OnTooltip {

        void onTooltip(BoxedSliderButton button, PoseStack poseStack, int mouseX, int mouseY);

        default void narrateTooltip(Consumer<Component> contents) {

        }
    }
}
