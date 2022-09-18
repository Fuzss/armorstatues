package fuzs.armorstatues.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ArmorStandEquipmentScreen extends AbstractContainerScreen<ArmorStandMenu> implements ArmorStandScreen {
    private static final ResourceLocation ARMOR_STAND_EQUIPMENT_LOCATION = new ResourceLocation(ArmorStatues.MOD_ID, "textures/gui/container/armor_stand/equipment.png");

    private final Inventory inventory;
    private float mouseX;
    private float mouseY;

    public ArmorStandEquipmentScreen(ArmorStandMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
        this.inventory = inventory;
        this.imageWidth = 210;
        this.imageHeight = 188;
    }

    @Override
    public Screen createTabScreen(ArmorStandScreenType<?> screenType) {
        return screenType.createTabScreen(this.menu, this.inventory, this.title);
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(AbstractArmorStandScreen.makeCloseButton(this, this.leftPos, this.imageWidth, this.topPos));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            if (AbstractArmorStandScreen.handleTabClicked((int) mouseX, (int) mouseY, this.leftPos, this.topPos, this.imageHeight, this)) {
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float pPartialTick) {
        this.renderBackground(poseStack);
        this.renderBg(poseStack, pPartialTick, mouseX, mouseY);
        super.render(poseStack, mouseX, mouseY, pPartialTick);
        this.renderTooltip(poseStack, mouseX, mouseY);
        if (this.menu.getCarried().isEmpty()) {
            AbstractArmorStandScreen.findHoveredTab(this.leftPos, this.topPos, this.imageHeight, mouseX, mouseY).ifPresent(hoveredTab -> {
                this.renderTooltip(poseStack, hoveredTab.getComponent(), mouseX, mouseY);
            });
        }
        this.mouseX = (float) mouseX;
        this.mouseY = (float) mouseY;
    }

    @Override
    protected void renderBg(PoseStack poseStack, float pPartialTick, int pX, int pY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ARMOR_STAND_EQUIPMENT_LOCATION);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(poseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
        AbstractArmorStandScreen.drawTabs(poseStack, this.leftPos, this.topPos, this.imageHeight, this);
        InventoryScreen.renderEntityInInventory(i + 104, j + 84, 30, (float) (i + 104 - 10) - this.mouseX, (float) (j + 84 - 44) - this.mouseY, this.menu.getArmorStand());
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {

    }

    @Override
    public ArmorStandScreenType<?> getScreenType() {
        return ArmorStandScreenType.EQUIPMENT;
    }
}
