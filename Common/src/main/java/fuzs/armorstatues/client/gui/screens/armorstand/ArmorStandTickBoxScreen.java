package fuzs.armorstatues.client.gui.screens.armorstand;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.network.client.data.DataSyncHandler;
import fuzs.armorstatues.world.inventory.ArmorStandHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;

public abstract class ArmorStandTickBoxScreen extends AbstractArmorStandScreen {
    private EditBox name;

    public ArmorStandTickBoxScreen(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        super(holder, inventory, component, dataSyncHandler);
        this.inventoryEntityX = 14;
        this.inventoryEntityY = 50;
    }

    @Override
    public void tick() {
        super.tick();
        this.name.tick();
    }

    @Override
    protected void init() {
        super.init();
        ArmorStand armorStand = this.holder.getArmorStand();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        this.name = new EditBox(this.font, this.leftPos + 16, this.topPos + 32, 66, 9, EntityType.ARMOR_STAND.getDescription());
        this.name.setTextColor(16777215);
        this.name.setBordered(false);
        this.name.setMaxLength(50);
        this.name.setResponder(this::onNameChanged);
        this.name.setValue(armorStand.getName().getString());
        this.addWidget(this.name);
        this.setInitialFocus(this.name);
    }

    private void onNameChanged(String input) {
        input = input.trim();
        ArmorStand armorStand = this.holder.getArmorStand();
        if (!input.equals(armorStand.getName().getString())) {
            this.dataSyncHandler.sendName(input);
        }
    }

    @Override
    public void resize(Minecraft pMinecraft, int pWidth, int pHeight) {
        String s = this.name.getValue();
        this.init(pMinecraft, pWidth, pHeight);
        this.name.setValue(s);
    }

    @Override
    public void removed() {
        super.removed();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    @Override
    public boolean keyPressed(int keyCode, int pScanCode, int pModifiers) {
        if (keyCode == 256 && this.shouldCloseOnEsc()) {
            this.onClose();
            return true;
        }
        return this.name.keyPressed(keyCode, pScanCode, pModifiers) || this.name.canConsumeInput() || super.keyPressed(keyCode, pScanCode, pModifiers);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        super.renderBg(poseStack, partialTick, mouseX, mouseY);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ARMOR_STAND_WIDGETS_LOCATION);
        // name edit box background
        this.blit(poseStack, this.leftPos + 14, this.topPos + 30, 0, 108, 76, 12);
        this.name.render(poseStack, mouseX, mouseY, partialTick);
    }
}
