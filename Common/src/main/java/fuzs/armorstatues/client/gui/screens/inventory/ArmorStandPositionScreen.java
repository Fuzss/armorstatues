package fuzs.armorstatues.client.gui.screens.inventory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.client.gui.components.NewTextureButton;
import fuzs.armorstatues.client.gui.components.TickButton;
import fuzs.armorstatues.network.client.C2SArmorStandPositionMessage;
import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import fuzs.puzzleslib.util.PuzzlesUtil;
import net.minecraft.Util;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ArmorStandPositionScreen extends AbstractArmorStandScreen {
    private static final DecimalFormat BLOCK_INCREMENT_FORMAT = Util.make(new DecimalFormat("#.####"), (decimalFormat) -> {
        decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
    });
    private static final double[] INCREMENTS = {0.0625, 0.25, 0.5, 1.0};

    private static double currentIncrement = INCREMENTS[0];

    private final List<PositionScreenWidget> widgets;

    @Nullable
    private PositionScreenWidget activeWidget;

    public ArmorStandPositionScreen(ArmorStandMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        ArmorStand armorStand = menu.getArmorStand();
        this.widgets = ImmutableList.<PositionScreenWidget>builder()
                .add(new PositionIncrementWidget())
                .add(new PositionComponentWidget("x", armorStand::getX, x -> {
                    syncMove(x, armorStand.getY(), armorStand.getZ());
                }))
                .add(new PositionComponentWidget("y", armorStand::getY, y -> {
                    syncMove(armorStand.getX(), y, armorStand.getZ());
                }))
                .add(new PositionComponentWidget("z", armorStand::getZ, z -> {
                    syncMove(armorStand.getX(), armorStand.getY(), z);
                }))
                .add(new PositionAlignWidget(armorStand::position, vec3 -> {
                    syncMove(vec3.x(), vec3.y(), vec3.z());
                }))
                .build();
    }

    private static void syncMove(double posX, double posY, double posZ) {
        // only move server-side to prevent rubber banding
        ArmorStatues.NETWORK.sendToServer(new C2SArmorStandPositionMessage(posX, posY, posZ));
    }

    private Collection<PositionScreenWidget> getActivePositionComponentWidgets() {
        if (this.activeWidget != null) {
            List<PositionScreenWidget> activeWidgets = Lists.newArrayList(this.activeWidget);
            for (PositionScreenWidget widget : this.widgets) {
                if (widget.alwaysVisible(this.activeWidget)) activeWidgets.add(widget);
            }
            return activeWidgets;
        }
        return this.widgets;
    }

    void setActiveWidget(PositionScreenWidget widget) {
        if (this.activeWidget == widget) {
            this.toggleMenuRendering(false);
            this.activeWidget = null;
        } else {
            this.activeWidget = widget;
            this.toggleMenuRendering(true);
        }
    }

    @Override
    protected boolean renderInventoryEntity() {
        return false;
    }

    @Override
    protected boolean disableMenuRendering() {
        return this.activeWidget != null;
    }

    @Override
    protected void toggleMenuRendering(boolean disableMenuRendering) {
        super.toggleMenuRendering(disableMenuRendering);
        for (PositionScreenWidget widget : this.widgets) {
            widget.setVisible(!disableMenuRendering || widget.alwaysVisible(this.activeWidget));
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.getActivePositionComponentWidgets().forEach(PositionScreenWidget::tick);
    }

    @Override
    protected void init() {
        super.init();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        int startY = (this.imageHeight - this.widgets.size() * 22 - (this.widgets.size() - 1) * 10) / 2;
        for (int i = 0; i < this.widgets.size(); i++) {
            this.widgets.get(i).init(this.leftPos + 8, this.topPos + startY + 7 + i * 32);
        }
    }

    @Override
    public void removed() {
        super.removed();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        super.renderBg(poseStack, partialTick, mouseX, mouseY);
        for (PositionScreenWidget widget : this.getActivePositionComponentWidgets()) {
            widget.render(poseStack, mouseX, mouseY, partialTick);
        }
    }

    @Override
    public ArmorStandScreenType<?> getScreenType() {
        return ArmorStandScreenType.POSITION;
    }

    private static Component getPixelIncrementComponent(double increment) {
        return Component.translatable("armorstatues.screen.position.pixels", getBlockPixelIncrement(increment));
    }

    private static Component getBlockIncrementComponent(double increment) {
        return Component.translatable("armorstatues.screen.position.blocks", BLOCK_INCREMENT_FORMAT.format(increment));
    }

    private static int getBlockPixelIncrement(double increment) {
        return (int) Math.round(increment * 16.0);
    }

    private interface PositionScreenWidget {

        void tick();

        void init(int posX, int posY);

        void setVisible(boolean visible);

        void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick);

        boolean alwaysVisible(@Nullable PositionScreenWidget activeWidget);
    }

    private abstract class AbstractPositionScreenWidget implements PositionScreenWidget {
        protected final Component title;
        protected int posX;
        protected int posY;
        protected List<AbstractWidget> children;

        protected AbstractPositionScreenWidget(Component title) {
            this.title = title;
        }

        @Override
        public void tick() {

        }

        @Override
        public void init(int posX, int posY) {
            this.posX = posX;
            this.posY = posY;
            this.children = Lists.newArrayList();
        }

        @Override
        public final void setVisible(boolean visible) {
            for (AbstractWidget widget : this.children) {
                widget.visible = visible;
            }
        }

        @Override
        public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
            if (ArmorStandPositionScreen.this.disableMenuRendering()) {
                NewTextureButton.drawCenteredString(poseStack, ArmorStandPositionScreen.this.font, this.title, this.posX + 36, this.posY + 6, -1, true);
            } else {
                NewTextureButton.drawCenteredString(poseStack, ArmorStandPositionScreen.this.font, this.title, this.posX + 36, this.posY + 6, 4210752, false);
            }
        }

        @Override
        public boolean alwaysVisible(@Nullable PositionScreenWidget activeWidget) {
            return activeWidget == this;
        }
    }

    private class PositionIncrementWidget extends AbstractPositionScreenWidget {

        public PositionIncrementWidget() {
            super(Component.translatable("armorstatues.screen.position.moveBy"));
        }

        @Override
        public void init(int posX, int posY) {
            super.init(posX, posY);
            for (int i = 0; i < INCREMENTS.length; i++) {
                double increment = INCREMENTS[i];
                AbstractWidget widget = ArmorStandPositionScreen.this.addRenderableWidget(new NewTextureButton(posX + 76 + i * 24 + (i > 1 ? 1 : 0), posY + 1, 20, 20, 0, 189, ARMOR_STAND_WIDGETS_LOCATION, Component.literal(String.valueOf(getBlockPixelIncrement(increment))), button -> {
                    this.setActiveIncrement(button, increment);
                }, (Button button, PoseStack poseStack, int mouseX, int mouseY) -> {
                    List<Component> lines = Lists.newArrayList(getPixelIncrementComponent(increment), getBlockIncrementComponent(increment));
                    ArmorStandPositionScreen.this.renderTooltip(poseStack, lines.stream().map(Component::getVisualOrderText).collect(Collectors.toList()), mouseX, mouseY);
                }));
                this.children.add(widget);
                if (increment == currentIncrement) {
                    widget.active = false;
                }
            }
            this.children.add(ArmorStandPositionScreen.this.addRenderableWidget(new ImageButton(posX + 174, posY + 1, 20, 20, 236, 64, ARMOR_STAND_WIDGETS_LOCATION, button -> {
                ArmorStandPositionScreen.this.setActiveWidget(this);
            })));
        }

        private void setActiveIncrement(AbstractWidget source, double increment) {
            currentIncrement = increment;
            for (AbstractWidget widget : this.children) {
                widget.active = widget != source;
            }
        }

        @Override
        public boolean alwaysVisible(@Nullable PositionScreenWidget activeWidget) {
            return true;
        }
    }

    private class PositionComponentWidget extends AbstractPositionScreenWidget {
        private final DoubleSupplier currentValue;
        private final DoubleConsumer newValue;

        private EditBox editBox;
        private int ticks;

        public PositionComponentWidget(String translationId, DoubleSupplier currentValue, DoubleConsumer newValue) {
            super(Component.translatable("armorstatues.screen.position." + translationId));
            this.currentValue = currentValue;
            this.newValue = newValue;
        }

        @Override
        public void tick() {
            super.tick();
            // armor stand position might change externally, so we update occasionally
            if (this.ticks > 0) this.ticks--;
            if (this.ticks == 0 && this.editBox != null) {
                this.ticks = 10;
                this.editBox.setValue(BLOCK_INCREMENT_FORMAT.format(this.getPositionValue()));
            }
        }

        @Override
        public void init(int posX, int posY) {
            super.init(posX, posY);
            this.editBox = new EditBox(ArmorStandPositionScreen.this.font, posX + 77, posY, 66, 22, EntityType.ARMOR_STAND.getDescription());
            this.editBox.setMaxLength(50);
            this.editBox.setEditable(false);
            this.editBox.setTextColorUneditable(14737632);
            this.editBox.setValue(BLOCK_INCREMENT_FORMAT.format(this.getPositionValue()));
            this.children.add(this.editBox);
            this.children.add(ArmorStandPositionScreen.this.addRenderableWidget(new ImageButton(posX + 149, posY + 1, 20, 10, 196, 64, 20, ARMOR_STAND_WIDGETS_LOCATION, 256, 256, button -> {
                this.setPositionValue(this.getPositionValue() + currentIncrement);
            }, (Button button, PoseStack poseStack, int mouseX, int mouseY) -> {
                ArmorStandPositionScreen.this.renderTooltip(poseStack, Component.translatable("armorstatues.screen.position.increment", getPixelIncrementComponent(currentIncrement)), mouseX, mouseY);
            }, CommonComponents.EMPTY)));
            this.children.add(ArmorStandPositionScreen.this.addRenderableWidget(new ImageButton(posX + 149, posY + 11, 20, 10, 216, 74, 20, ARMOR_STAND_WIDGETS_LOCATION, 256, 256, button -> {
                this.setPositionValue(this.getPositionValue() - currentIncrement);
            }, (Button button, PoseStack poseStack, int mouseX, int mouseY) -> {
                ArmorStandPositionScreen.this.renderTooltip(poseStack, Component.translatable("armorstatues.screen.position.decrement", getPixelIncrementComponent(currentIncrement)), mouseX, mouseY);
            }, CommonComponents.EMPTY)));
            this.children.add(ArmorStandPositionScreen.this.addRenderableWidget(new ImageButton(posX + 174, posY + 1, 20, 20, 236, 64, ARMOR_STAND_WIDGETS_LOCATION, button -> {
                ArmorStandPositionScreen.this.setActiveWidget(this);
            })));
        }

        private double getPositionValue() {
            return PuzzlesUtil.round(Math.round(this.currentValue.getAsDouble() * 16.0) / 16.0, 4);
        }

        private void setPositionValue(double newValue) {
            this.ticks = 20;
            newValue = Math.round(newValue * 16.0) / 16.0;
            if (this.getPositionValue() != newValue) {
                this.editBox.setValue(BLOCK_INCREMENT_FORMAT.format(newValue));
                this.newValue.accept(newValue);
            }
        }

        @Override
        public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
            super.render(poseStack, mouseX, mouseY, partialTick);
            this.editBox.render(poseStack, mouseX, mouseY, partialTick);
        }

        @Override
        public boolean alwaysVisible(@Nullable PositionScreenWidget activeWidget) {
            return activeWidget instanceof PositionIncrementWidget || super.alwaysVisible(activeWidget);
        }
    }

    private class PositionAlignWidget extends AbstractPositionScreenWidget {
        private final Supplier<Vec3> currentPosition;
        private final Consumer<Vec3> newPosition;

        public PositionAlignWidget(Supplier<Vec3> currentPosition, Consumer<Vec3> newPosition) {
            super(Component.empty());
            this.currentPosition = currentPosition;
            this.newPosition = newPosition;
        }

        @Override
        public void init(int posX, int posY) {
            super.init(posX, posY);
            this.children.add(ArmorStandPositionScreen.this.addRenderableWidget(new TickButton(posX, posY + 1, 94, 20, Component.translatable("armorstatues.screen.position.centered"), Component.translatable("armorstatues.screen.position.aligned"), button -> {
                this.newPosition.accept(this.currentPosition.get().align(EnumSet.allOf(Direction.Axis.class)).add(0.5, 0.0, 0.5));
            })));
            this.children.add(ArmorStandPositionScreen.this.addRenderableWidget(new TickButton(posX + 100, posY + 1, 94, 20, Component.translatable("armorstatues.screen.position.cornered"), Component.translatable("armorstatues.screen.position.aligned"), button -> {
                this.newPosition.accept(this.currentPosition.get().align(EnumSet.allOf(Direction.Axis.class)));
            })));
        }

        @Override
        public void tick() {
            super.tick();
            for (AbstractWidget widget : this.children) {
                if (widget instanceof TickButton tickButton) {
                    tickButton.tick();
                }
            }
        }
    }
}
