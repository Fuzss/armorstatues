package fuzs.armorstatues.api.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.api.client.gui.screens.armorstand.AbstractArmorStandScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;

public class NewTextureTickButton extends NewTextureButton implements TickingButton {
    private final int imageTextureX;
    private final int imageTextureY;
    private final ResourceLocation imageTextureLocation;
    private int lastClickedTicks;
    protected int lastClickedTicksDelay = 20;

    public NewTextureTickButton(int x, int y, int width, int height, int imageTextureX, int imageTextureY, ResourceLocation imageTextureLocation, OnPress onPress) {
        this(x, y, width, height, imageTextureX, imageTextureY, imageTextureLocation, onPress, NO_TOOLTIP);
    }

    public NewTextureTickButton(int x, int y, int width, int height, int imageTextureX, int imageTextureY, ResourceLocation imageTextureLocation, OnPress onPress, OnTooltip onTooltip) {
        super(x, y, width, height, 0, 184, AbstractArmorStandScreen.ARMOR_STAND_WIDGETS_LOCATION, CommonComponents.EMPTY, onPress, onTooltip);
        this.imageTextureX = imageTextureX;
        this.imageTextureY = imageTextureY;
        this.imageTextureLocation = imageTextureLocation;
    }

    @Override
    protected int getYImage(boolean isHovered) {
        int i = 1;
        if (!this.active) {
            i = 0;
        } else if (isHovered) {
            i = 2;
        }
        return i;
    }

    @Override
    public void onPress() {
        super.onPress();
        this.lastClickedTicks = this.lastClickedTicksDelay;
    }

    @Override
    public void tick() {
        if (this.lastClickedTicks > 0) this.lastClickedTicks--;
    }

    @Override
    protected void renderBg(PoseStack poseStack, Minecraft minecraft, int mouseX, int mouseY) {
        super.renderBg(poseStack, minecraft, mouseX, mouseY);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.lastClickedTicks > 0 ? this.textureLocation : this.imageTextureLocation);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        final int i = this.getYImage(this.isHoveredOrFocused());
        if (this.lastClickedTicks > 0) {
            this.blit(poseStack, this.x + this.width / 2 - 8, this.y + this.height / 2 - 8, 176, 124 + i * 16, 16, 16);
        } else {
            this.blit(poseStack, this.x + this.width / 2 - 8, this.y + this.height / 2 - 8, this.imageTextureX, this.imageTextureY + i * 16, 16, 16);
        }
    }
}
