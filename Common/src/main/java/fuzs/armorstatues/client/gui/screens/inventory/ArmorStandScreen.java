package fuzs.armorstatues.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.ArmorStatues;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ArmorStandScreen extends Screen {
    private static final ResourceLocation ARMOR_STAND_BACKGROUND_LOCATION = new ResourceLocation(ArmorStatues.MOD_ID, "textures/gui/container/armor_stand/background.png");
    private static final ResourceLocation ARMOR_STAND_EQUIPMENT_LOCATION = new ResourceLocation(ArmorStatues.MOD_ID, "textures/gui/container/armor_stand/equipment.png");
    public static final ResourceLocation ARMOR_STAND_WIDGETS_LOCATION = new ResourceLocation(ArmorStatues.MOD_ID, "textures/gui/container/armor_stand/widgets.png");

    private final int imageWidth = 210;
    private final int imageHeight = 188;
    private int leftPos;
    private int topPos;

    public ArmorStandScreen(Component component) {
        super(component);
    }

    @Override
    protected void init() {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = this.height / 4;
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 4 + 120, 200, 20, CommonComponents.GUI_DONE, (button) -> {
            // TODO save data
            this.onClose();
        }));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(poseStack);
        this.renderBg(poseStack, partialTick, mouseX, mouseY);
        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ARMOR_STAND_BACKGROUND_LOCATION);
        this.blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }
}
