package fuzs.armorstatues.client.gui.screens.inventory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.network.client.C2SArmorStandPositionMessage;
import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import fuzs.puzzleslib.util.PuzzlesUtil;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

public class ArmorStandPositionScreen extends AbstractArmorStandScreen {
    public static final ResourceLocation QUESTION_MARK_LOCATION = new ResourceLocation(ArmorStatues.MOD_ID, "item/question_mark");

    private final List<PositionComponentWidget> positionComponents;

    @Nullable
    private PositionComponentWidget activeWidget;

    public ArmorStandPositionScreen(ArmorStandMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        ArmorStand armorStand = menu.getArmorStand();
        this.positionComponents = ImmutableList.<PositionComponentWidget>builder()
                .add(new PositionComponentWidget("x", armorStand::getX, x -> {
                    moveAndSync(x, armorStand.getY(), armorStand.getZ());
                }))
                .add(new PositionComponentWidget("y", armorStand::getY, y -> {
                    moveAndSync(armorStand.getX(), y, armorStand.getZ());
                }))
                .add(new PositionComponentWidget("z", armorStand::getZ, z -> {
                    moveAndSync(armorStand.getX(), armorStand.getY(), z);
                }))
                .build();
    }

    private static void moveAndSync(double posX, double posY, double posZ) {
        // only move server-side to prevent rubber banding
        ArmorStatues.NETWORK.sendToServer(new C2SArmorStandPositionMessage(posX, posY, posZ));
    }

    private Collection<PositionComponentWidget> getActivePositionComponentWidgets() {
        if (this.activeWidget != null) return Lists.newArrayList(this.activeWidget);
        return this.positionComponents;
    }

    void setActiveWidget(PositionComponentWidget widget) {
        if (this.activeWidget == widget) {
            this.toggleMenuRendering(false);
            this.activeWidget = null;
        } else {
            this.activeWidget = widget;
            this.toggleMenuRendering(true);
        }
    }

    @Override
    protected boolean disableMenuRendering() {
        return this.activeWidget != null;
    }

    @Override
    protected void toggleMenuRendering(boolean disableMenuRendering) {
        super.toggleMenuRendering(disableMenuRendering);
        for (PositionComponentWidget widget : this.positionComponents) {
            if (widget != this.activeWidget) widget.setVisible(!disableMenuRendering);
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.getActivePositionComponentWidgets().forEach(PositionComponentWidget::tick);
    }

    @Override
    protected void init() {
        super.init();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        int startY = (this.imageHeight - this.positionComponents.size() * 22 - (this.positionComponents.size() - 1) * 12) / 2;
        for (int i = 0; i < this.positionComponents.size(); i++) {
            this.positionComponents.get(i).init(this.leftPos + 8, this.topPos + startY + i * 34);
        }
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
        for (PositionComponentWidget widget : this.getActivePositionComponentWidgets()) {
            if (widget.keyPressed(keyCode, pScanCode, pModifiers)) {
                return true;
            }
        }

        return super.keyPressed(keyCode, pScanCode, pModifiers);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);
        if (mouseX > this.leftPos + 8 && mouseX <= this.leftPos + 8 + 16 && mouseY > this.topPos + 8 && mouseY <= this.topPos + 8 + 16) {
            this.renderTooltip(poseStack, Component.literal("Hold shift, alt or control to change increments."), mouseX, mouseY);
        }
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        super.renderBg(poseStack, partialTick, mouseX, mouseY);
        TextureAtlasSprite textureatlassprite = this.minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(QUESTION_MARK_LOCATION);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, textureatlassprite.atlas().location());
        blit(poseStack, this.leftPos + 8, this.topPos + 8, this.getBlitOffset(), 16, 16, textureatlassprite);
        for (PositionComponentWidget widget : this.getActivePositionComponentWidgets()) {
            widget.render(poseStack, mouseX, mouseY, partialTick);
        }
    }

    @Override
    public ArmorStandScreenType<?> getScreenType() {
        return ArmorStandScreenType.POSITION;
    }

    private class PositionComponentWidget {
        private final Component component;
        private final DoubleSupplier currentValue;
        private final DoubleConsumer newValue;

        private int posX;
        private int posY;
        private EditBox editBox;
        private List<AbstractWidget> widgets;

        public PositionComponentWidget(String translationId, DoubleSupplier currentValue, DoubleConsumer newValue) {
            this.component = Component.translatable("armorstatues.screen.position." + translationId);
            this.currentValue = currentValue;
            this.newValue = newValue;
        }

        public void tick() {
            this.editBox.tick();
        }

        public void init(int posX, int posY) {
            this.posX = posX;
            this.posY = posY;
            this.editBox = new EditBox(ArmorStandPositionScreen.this.font, posX + 78, posY, 66, 22, EntityType.ARMOR_STAND.getDescription());
            this.editBox.setMaxLength(50);
            this.editBox.setFilter(s -> {
                try {
                    double v = Double.parseDouble(s);
                    return PuzzlesUtil.round(v, 4) == v;
                } catch (NumberFormatException ignored) {

                }
                return false;
            });
            this.editBox.setValue(String.valueOf(this.getPositionValue()));
            this.editBox.setResponder(s -> this.setPositionValue(Double.parseDouble(s), true));
            ArmorStandPositionScreen.this.addWidget(this.editBox);
            this.widgets = Lists.newArrayList();
            this.widgets.add(ArmorStandPositionScreen.this.addRenderableWidget(new ImageButton(posX + 149, posY + 1, 20, 10, 136, 82, 20, ARMOR_STAND_WIDGETS_LOCATION, 256, 256, button -> {
                this.setPositionValue(this.getPositionValue() + getBlockPixelIncrement() / 16.0, false);
            }, (Button button, PoseStack poseStack, int mouseX, int mouseY) -> {
                ArmorStandPositionScreen.this.renderTooltip(poseStack, Component.translatable("armorstatues.screen.position.increment", getBlockPixelIncrement()), mouseX, mouseY);
            }, CommonComponents.EMPTY)));
            this.widgets.add(ArmorStandPositionScreen.this.addRenderableWidget(new ImageButton(posX + 149, posY + 11, 20, 10, 156, 92, 20, ARMOR_STAND_WIDGETS_LOCATION, 256, 256, button -> {
                this.setPositionValue(this.getPositionValue() - getBlockPixelIncrement() / 16.0, false);
            }, (Button button, PoseStack poseStack, int mouseX, int mouseY) -> {
                ArmorStandPositionScreen.this.renderTooltip(poseStack, Component.translatable("armorstatues.screen.position.decrement", getBlockPixelIncrement()), mouseX, mouseY);
            }, CommonComponents.EMPTY)));
            this.widgets.add(ArmorStandPositionScreen.this.addRenderableWidget(new ImageButton(posX + 174, posY + 1, 20, 20, 176, 82, ARMOR_STAND_WIDGETS_LOCATION, button -> {
                ArmorStandPositionScreen.this.setActiveWidget(this);
            })));
        }

        private static int getBlockPixelIncrement() {
            if (Screen.hasControlDown()) return 16;
            if (Screen.hasShiftDown()) return 8;
            if (Screen.hasAltDown()) return 4;
            return 1;
        }

        private double getPositionValue() {
            return PuzzlesUtil.round(Math.round(this.currentValue.getAsDouble() * 16.0) / 16.0, 4);
        }

        private void setPositionValue(double newValue, boolean fromEditBox) {
            newValue = Math.round(newValue * 16.0) / 16.0;
            if (this.getPositionValue() != newValue) {
                if (!fromEditBox) this.editBox.setValue(String.valueOf(newValue));
                this.newValue.accept(newValue);
            }
        }

        public void setVisible(boolean visible) {
            this.editBox.visible = visible;
            for (AbstractWidget widget : this.widgets) {
                widget.visible = visible;
            }
        }

        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            return this.editBox.keyPressed(keyCode, scanCode, modifiers) || this.editBox.canConsumeInput();
        }

        public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
            GuiComponent.drawCenteredString(poseStack, ArmorStandPositionScreen.this.font, this.component, this.posX + 36, this.posY + 6, -1);
            this.editBox.render(poseStack, mouseX, mouseY, partialTick);
        }
    }
}
