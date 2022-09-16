package fuzs.armorstatues.client.gui.screens.inventory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.client.gui.components.TickBoxButton;
import fuzs.armorstatues.network.client.C2SArmorStandNameMessage;
import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class ArmorStandStyleScreen extends AbstractArmorStandScreen {
    private static final List<Component> STYLE_SETTING_DESCRIPTIONS = Lists.newArrayList("showName", "showArms", "small", "invisible", "noBasePlate", "noGravity").stream().map(s -> "armorstatues.screen.style." + s).map(Component::translatable).collect(ImmutableList.toImmutableList());
    private static final List<Component> STYLE_SETTING_TOOLTIPS = Lists.newArrayList("showName", "showArms", "small", "invisible", "noBasePlate", "noGravity").stream().map(s -> "armorstatues.screen.style." + s + ".description").map(Component::translatable).collect(ImmutableList.toImmutableList());

    private EditBox name;

    public ArmorStandStyleScreen(ArmorStandMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    public void tick() {
        super.tick();
        this.name.tick();
    }

    @Override
    protected void init() {
        super.init();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        this.name = new EditBox(this.font, this.leftPos + 16, this.topPos + 33, 66, 9, EntityType.ARMOR_STAND.getDescription());
        this.name.setTextColor(16777215);
        this.name.setBordered(false);
        this.name.setMaxLength(50);
        this.name.setResponder(this::onNameChanged);
        this.name.setValue(this.menu.getArmorStand().getName().getString());
        this.addWidget(this.name);
        this.setInitialFocus(this.name);
        final int buttonStartY = (this.imageHeight - STYLE_SETTING_DESCRIPTIONS.size() * 20 - (STYLE_SETTING_DESCRIPTIONS.size() - 1) * 2) / 2;
        for (int i = 0; i < STYLE_SETTING_DESCRIPTIONS.size(); i++) {
            Component description = STYLE_SETTING_TOOLTIPS.get(i);
            this.addRenderableWidget(new TickBoxButton(this.leftPos + 98, this.topPos + buttonStartY + i * 22, STYLE_SETTING_DESCRIPTIONS.get(i), (Button button, PoseStack poseStack, int mouseX, int mouseY) -> {
                this.renderTooltip(poseStack, description, mouseX, mouseY);
            }));
        }
    }

    private void onNameChanged(String input) {
        if (!input.equals(this.menu.getArmorStand().getName().getString())) {
            C2SArmorStandNameMessage.setCustomNameArmorStand(input, this.menu.getArmorStand());
            ArmorStatues.NETWORK.sendToServer(new C2SArmorStandNameMessage(input));
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
        // armor stand background
        this.blit(poseStack, this.leftPos + 14, this.topPos + 50, 0, 0, 76, 108);
        // name edit box background
        this.blit(poseStack, this.leftPos + 14, this.topPos + 31, 0, 108, 76, 12);
        this.name.render(poseStack, mouseX, mouseY, partialTick);
        InventoryScreen.renderEntityInInventory(this.leftPos + 52, this.topPos + 148, 45, (float) (this.leftPos + 52 - 5) - this.mouseX, (float) (this.topPos + 148 - 66) - this.mouseY, this.menu.getArmorStand());
    }

    @Override
    public ArmorStandScreenType<?> getScreenType() {
        return ArmorStandScreenType.STYLE;
    }
}
