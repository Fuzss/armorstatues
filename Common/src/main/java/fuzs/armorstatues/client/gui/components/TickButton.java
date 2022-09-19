package fuzs.armorstatues.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.client.gui.screens.inventory.AbstractArmorStandScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;

public class TickButton extends NewTextureButton {
    private final Component title;
    private final Component clickedTitle;
    private int lastClickedTicks;
    public int lastClickedTicksDelay = 30;

    public TickButton(int x, int y, int width, int height, Component title, Component clickedTitle, OnPress onPress) {
        super(x, y, width, height, 0, 174, AbstractArmorStandScreen.ARMOR_STAND_WIDGETS_LOCATION, title, onPress);
        this.title = title;
        this.clickedTitle = clickedTitle;
    }

    public TickButton(int x, int y, int width, int height, Component title, Component clickedTitle, OnPress onPress, OnTooltip onTooltip) {
        super(x, y, width, height, 0, 174, AbstractArmorStandScreen.ARMOR_STAND_WIDGETS_LOCATION, title, onPress, onTooltip);
        this.title = title;
        this.clickedTitle = clickedTitle;
    }

    @Override
    public void onPress() {
        super.onPress();
        this.lastClickedTicks = this.lastClickedTicksDelay;
        this.setMessage(this.clickedTitle);
    }

    public void tick() {
        if (this.lastClickedTicks > 0) this.lastClickedTicks--;
        if (this.lastClickedTicks == 0) {
            this.setMessage(this.title);
        }
    }

    @Override
    protected void renderBg(PoseStack poseStack, Minecraft minecraft, int mouseX, int mouseY) {
        super.renderBg(poseStack, minecraft, mouseX, mouseY);
        if (this.lastClickedTicks > 0) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, this.textureLocation);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
            final int i = this.getYImage(this.isHoveredOrFocused());
            int titleWidth = minecraft.font.width(this.clickedTitle);
            final int startX = (this.width - titleWidth - (titleWidth > 0 ? 4 : 0) - 16) / 2;
            this.blit(poseStack, this.x + startX, this.y + 2, 196, 16 + i * 16, 16, 16);
        }
    }

    @Override
    protected int getMessageXOffset() {
        if (this.lastClickedTicks > 0) {
            return (16 + 4) / 2;
        }
        return super.getMessageXOffset();
    }
}
