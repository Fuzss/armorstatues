package fuzs.armorstatues.client.gui.components;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.client.gui.screens.inventory.AbstractArmorStandScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class BooleanSettingButton extends Checkbox {

    public BooleanSettingButton(int posX, int posY, Component component, boolean initialValue) {
        super(posX, posY, 109, 16, component, initialValue);
    }

    @Override
    public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.setShaderTexture(0, AbstractArmorStandScreen.ARMOR_STAND_WIDGETS_LOCATION);
        RenderSystem.enableDepthTest();
        Font font = minecraft.font;
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        blit(pPoseStack, this.x, this.y, 196.0F, this.isHoveredOrFocused() ? 16.0F : 0.0F, 16, 16, 256, 256);
        if (this.selected()) {
            blit(pPoseStack, this.x, this.y, 196.0F, 32.0F + (this.isHoveredOrFocused() ? 16.0F : 0.0F), 16, 16, 256, 256);
        }
        drawString(pPoseStack, font, this.getMessage(), this.x + 24, this.y + 4, 14737632 | Mth.ceil(this.alpha * 255.0F) << 24);
    }
}
