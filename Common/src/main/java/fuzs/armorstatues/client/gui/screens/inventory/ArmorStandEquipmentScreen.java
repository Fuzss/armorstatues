package fuzs.armorstatues.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;

public class ArmorStandEquipmentScreen extends AbstractContainerScreen<ArmorStandMenu> implements ArmorStandScreen {
    private static final ResourceLocation ARMOR_STAND_EQUIPMENT_LOCATION = new ResourceLocation(ArmorStatues.MOD_ID, "textures/gui/container/armor_stand/equipment.png");

    private final ArmorStand armorStand;
    private float xMouse;
    private float yMouse;

    public ArmorStandEquipmentScreen(ArmorStandMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
        this.armorStand = this.getMenu().getArmorStand();
        this.imageWidth = 210;
        this.imageHeight = 188;
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        this.renderBg(pPoseStack, pPartialTick, pMouseX, pMouseY);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
        this.xMouse = (float) pMouseX;
        this.yMouse = (float) pMouseY;
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pX, int pY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ARMOR_STAND_EQUIPMENT_LOCATION);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(pPoseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
        InventoryScreen.renderEntityInInventory(i + 104, j + 84, 30, (float) (i + 104 - 10) - this.xMouse, (float) (j + 84 - 44) - this.yMouse, this.armorStand);
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {

    }

    @Override
    public ArmorStandScreenType<?> getScreenType() {
        return ArmorStandScreenType.EQUIPMENT;
    }
}
