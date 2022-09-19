package fuzs.armorstatues.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.function.Consumer;

public abstract class NewTextureSliderButton extends AbstractSliderButton {
    private final int textureX;
    private final int textureY;
    protected final ResourceLocation textureLocation;
    protected final OnTooltip onTooltip;
    public double snapInterval;

    public NewTextureSliderButton(int x, int y, int width, int height, int textureX, int textureY, ResourceLocation textureLocation, Component component, double value) {
        this(x, y, width, height, textureX, textureY, textureLocation, component, value, (button, poseStack, mouseX, mouseY) -> {});
    }

    public NewTextureSliderButton(int x, int y, int width, int height, int textureX, int textureY, ResourceLocation textureLocation, Component component, double value, OnTooltip onTooltip) {
        super(x, y, width, height, component, value);
        this.textureX = textureX;
        this.textureY = textureY;
        this.textureLocation = textureLocation;
        this.onTooltip = onTooltip;
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.textureLocation);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        int i = this.getYImage(this.isHoveredOrFocused());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(poseStack, this.x, this.y, this.textureX, this.textureY + i * 20, this.width / 2, this.height);
        this.blit(poseStack, this.x + this.width / 2, this.y, this.textureX + 200 - this.width / 2, this.textureY + i * 20, this.width / 2, this.height);
        this.renderBg(poseStack, minecraft, mouseX, mouseY);
        int j = this.active ? 16777215 : 10526880;
        drawCenteredString(poseStack, font, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);
        if (this.isHoveredOrFocused()) {
            this.renderToolTip(poseStack, mouseX, mouseY);
        }
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, Minecraft pMinecraft, int pMouseX, int pMouseY) {
        RenderSystem.setShaderTexture(0, this.textureLocation);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = (this.isHoveredOrFocused() ? 2 : 1) * 20;
        this.blit(pPoseStack, this.x + (int)(this.value * (double)(this.width - 8)), this.y, this.textureX, this.textureY + i, 4, 20);
        this.blit(pPoseStack, this.x + (int)(this.value * (double)(this.width - 8)) + 4, this.y, this.textureX + 196, this.textureY + i, 4, 20);
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.setValueFromMouse(mouseX);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean bl = keyCode == 263;
        if (bl || keyCode == 262) {
            float f = bl ? -1.0F : 1.0F;
            this.setValue(this.value + (double)(f / (float)(this.width - 8)));
        }

        return false;
    }

    private void setValueFromMouse(double mouseX) {
        this.setValue((mouseX - (double)(this.x + 4)) / (double)(this.width - 8));
    }

    private void setValue(double value) {
        double oldValue = this.value;
        this.value = Mth.clamp(value, 0.0, 1.0);
        if (this.snapInterval > 0.0 && this.snapInterval < 1.0) {
            double currentSnap = 0.0;
            while (currentSnap < 1.0) {
                double snapRegion = this.snapInterval * 0.1;
                if (this.value >= currentSnap - snapRegion && this.value < currentSnap + snapRegion) {
                    this.value = currentSnap;
                    break;
                }
                currentSnap += this.snapInterval;
            }
        }
        if (oldValue != this.value) {
            this.applyValue();
        }
        this.updateMessage();
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
        this.setValueFromMouse(mouseX);
    }

    @Override
    public void renderToolTip(PoseStack poseStack, int relativeMouseX, int relativeMouseY) {
        this.onTooltip.onTooltip(this, poseStack, relativeMouseX, relativeMouseY);
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
        super.updateNarration(narrationElementOutput);
        this.onTooltip.narrateTooltip((component) -> {
            narrationElementOutput.add(NarratedElementType.HINT, component);
        });
    }

    public interface OnTooltip {

        void onTooltip(AbstractSliderButton button, PoseStack poseStack, int mouseX, int mouseY);

        default void narrateTooltip(Consumer<Component> contents) {

        }
    }
}
