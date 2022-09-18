package fuzs.armorstatues.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

public class NewTextureButton extends Button {
    private final int textureX;
    private final int textureY;
    protected final ResourceLocation textureLocation;

    public NewTextureButton(int x, int y, int width, int height, int textureX, int textureY, ResourceLocation textureLocation, Component component, OnPress onPress) {
        super(x, y, width, height, component, onPress);
        this.textureX = textureX;
        this.textureY = textureY;
        this.textureLocation = textureLocation;
    }

    public NewTextureButton(int x, int y, int width, int height, int textureX, int textureY, ResourceLocation textureLocation, Component component, OnPress onPress, OnTooltip onTooltip) {
        super(x, y, width, height, component, onPress, onTooltip);
        this.textureX = textureX;
        this.textureY = textureY;
        this.textureLocation = textureLocation;
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
        final int j = this.active && this.isHoveredOrFocused() ? ChatFormatting.YELLOW.getColor() : 4210752;
        drawCenteredString(poseStack, font, this.getMessage(), this.x + this.width / 2 + this.getMessageXOffset(), this.y + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24, false);
        if (this.isHoveredOrFocused()) {
            this.renderToolTip(poseStack, mouseX, mouseY);
        }
    }

    protected int getMessageXOffset() {
        return 0;
    }

    public static void drawCenteredString(PoseStack poseStack, Font font, Component text, int x, int y, int color, boolean dropShadow) {
        FormattedCharSequence formattedCharSequence = text.getVisualOrderText();
        if (dropShadow) {
            font.drawShadow(poseStack, formattedCharSequence, (float) (x - font.width(formattedCharSequence) / 2), (float)y, color);
        } else {
            font.draw(poseStack, formattedCharSequence, (float) (x - font.width(formattedCharSequence) / 2), (float)y, color);
        }
    }
}
