package fuzs.armorstatues.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.client.gui.screens.inventory.AbstractArmorStandScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.function.Consumer;

public abstract class VerticalSliderButton extends AbstractWidget {
    private final int sliderSize = 13;
    protected final OnTooltip onTooltip;
    protected double value;

    public VerticalSliderButton(int x, int y, double value) {
        this(x, y, value, (button, poseStack, mouseX, mouseY) -> {});
    }

    public VerticalSliderButton(int x, int y, double value, OnTooltip onTooltip) {
        super(x, y, 15, 54, CommonComponents.EMPTY);
        this.value = value;
        this.onTooltip = onTooltip;
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
        double oldValue = this.value;
        this.value = Mth.clamp(value, 0.0, 1.0);
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

    protected abstract void applyValue();

    public interface OnTooltip {

        void onTooltip(VerticalSliderButton button, PoseStack poseStack, int mouseX, int mouseY);

        default void narrateTooltip(Consumer<Component> contents) {

        }
    }
}
