package fuzs.armorstatues.api.client.gui.screens.armorstand;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.api.network.client.data.DataSyncHandler;
import fuzs.armorstatues.api.world.inventory.ArmorStandHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;

public abstract class ArmorStandTickBoxScreen<T> extends AbstractArmorStandScreen {
    private EditBox name;
    private int inputUpdateTicks;

    public ArmorStandTickBoxScreen(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        super(holder, inventory, component, dataSyncHandler);
        this.inventoryEntityX = 14;
        this.inventoryEntityY = 50;
    }

    @Override
    public void tick() {
        super.tick();
        this.name.tick();
        if (this.inputUpdateTicks > 0) {
            this.inputUpdateTicks--;
        }
        this.testNameInputChanged(true);
    }

    private void testNameInputChanged(boolean testEquality) {
        if (this.inputUpdateTicks == 0 || !testEquality && this.inputUpdateTicks != -1) {
            String name = this.name.getValue().trim();
            if (!name.equals(this.getNameDefaultValue())) {
                this.syncNameChange(name);
            }
            this.inputUpdateTicks = -1;
        }
    }

    protected abstract void syncNameChange(String input);

    @Override
    protected void init() {
        super.init();
        ArmorStand armorStand = this.holder.getArmorStand();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        this.name = new EditBox(this.font, this.leftPos + 16, this.topPos + 32, 66, 9, EntityType.ARMOR_STAND.getDescription());
        this.name.setTextColor(16777215);
        this.name.setBordered(false);
        this.name.setMaxLength(this.getNameMaxLength());
        this.name.setValue(this.getNameDefaultValue());
        this.name.setResponder(input -> this.inputUpdateTicks = 20);
        this.addWidget(this.name);
        this.inputUpdateTicks = -1;
        T[] values = this.getAllTickBoxValues();
        final int buttonStartY = (this.imageHeight - values.length * 20 - (values.length - 1) * 2) / 2;
        for (int i = 0; i < values.length; i++) {
            this.addRenderableWidget(this.makeTickBoxWidget(armorStand, buttonStartY, i, values[i]));
        }
    }

    protected abstract int getNameMaxLength();

    protected abstract String getNameDefaultValue();

    protected abstract T[] getAllTickBoxValues();

    protected abstract AbstractWidget makeTickBoxWidget(ArmorStand armorStand, int buttonStartY, int index, T option);

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);
        if (this.name.isMouseOver(mouseX, mouseY)) {
            this.renderTooltip(poseStack, this.font.split(this.getNameComponent(), 175), mouseX, mouseY);
        }
    }

    protected abstract Component getNameComponent();

    @Override
    public void resize(Minecraft pMinecraft, int pWidth, int pHeight) {
        this.testNameInputChanged(false);
        String s = this.name.getValue();
        this.init(pMinecraft, pWidth, pHeight);
        this.name.setValue(s);
    }

    @Override
    public void removed() {
        this.testNameInputChanged(false);
        super.removed();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    @Override
    public void onClose() {
        this.testNameInputChanged(false);
        super.onClose();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256 && this.shouldCloseOnEsc()) {
            this.onClose();
            return true;
        }
        return this.name.keyPressed(keyCode, scanCode, modifiers) || this.name.canConsumeInput() || super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        super.renderBg(poseStack, partialTick, mouseX, mouseY);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, getArmorStandWidgetsLocation());
        // name edit box background
        this.blit(poseStack, this.leftPos + 14, this.topPos + 30, 0, 108, 76, 12);
        this.name.render(poseStack, mouseX, mouseY, partialTick);
    }
}
