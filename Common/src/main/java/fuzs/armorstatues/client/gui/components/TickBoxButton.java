package fuzs.armorstatues.client.gui.components;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.client.gui.screens.armorstand.AbstractArmorStandScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class TickBoxButton extends Button {
    private final int textMargin;
    private boolean selected;

    public TickBoxButton(int posX, int posY, boolean selected, OnPress onPress, OnTooltip onTooltip) {
        this(posX, posY, 0, 0, CommonComponents.EMPTY, selected, onPress, onTooltip);
    }

    public TickBoxButton(int posX, int posY, int textMargin, int textWidth, Component component, boolean selected, OnPress onPress, OnTooltip onTooltip) {
        super(posX, posY, 20 + textMargin + textWidth, 20, component, onPress, onTooltip);
        this.textMargin = textMargin;
        this.selected = selected;
    }

    @Override
    public void onPress() {
        this.selected = !this.selected;
        super.onPress();
    }

    public boolean isSelected() {
        return this.selected;
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.setShaderTexture(0, AbstractArmorStandScreen.ARMOR_STAND_WIDGETS_LOCATION);
        RenderSystem.enableDepthTest();
        Font font = minecraft.font;
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        blit(poseStack, this.x + 2, this.y + 2, 196.0F, this.isHoveredOrFocused() ? 16.0F : 0.0F, 16, 16, 256, 256);
        if (this.selected) {
            blit(poseStack, this.x + 2, this.y + 2, 196.0F, 32.0F + (this.isHoveredOrFocused() ? 16.0F : 0.0F), 16, 16, 256, 256);
        }
        final int textColor = this.active ? (this.isHoveredOrFocused() ? ChatFormatting.YELLOW.getColor() : 16777215) : 10526880;
        drawString(poseStack, font, this.getMessage(), this.x + 20 + this.textMargin, this.y + 2 + 4, textColor | Mth.ceil(this.alpha * 255.0F) << 24);
        if (this.isHoveredOrFocused()) {
            this.renderToolTip(poseStack, mouseX, mouseY);
        }
    }
}
