package fuzs.armorstatues.client.gui.screens.armorstand;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.client.gui.components.NewTextureButton;
import fuzs.armorstatues.client.gui.components.NewTextureSliderButton;
import fuzs.armorstatues.client.gui.screens.armorstand.data.ArmorStandScreenType;
import fuzs.armorstatues.network.client.data.DataSyncHandler;
import fuzs.armorstatues.world.inventory.ArmorStandHolder;
import fuzs.puzzleslib.util.PuzzlesUtil;
import net.minecraft.Util;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ArmorStandPositionScreen extends ArmorStandWidgetsScreen {
    private static final DecimalFormat BLOCK_INCREMENT_FORMAT = Util.make(new DecimalFormat("#.####"), (decimalFormat) -> {
        decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
    });
    public static final DecimalFormat ROTATION_FORMAT = Util.make(new DecimalFormat("#.##"), (decimalFormat) -> {
        decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
    });
    private static final double[] INCREMENTS = {0.0625, 0.25, 0.5, 1.0};

    private static double currentIncrement = INCREMENTS[0];

    public ArmorStandPositionScreen(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        super(holder, inventory, component, dataSyncHandler);
    }

    @Override
    protected List<ArmorStandWidgetsScreen.PositionScreenWidget> buildWidgets(ArmorStand armorStand) {
        // only move server-side to prevent rubber banding
        return Lists.newArrayList(
                new RotationWidget(armorStand::getYRot, this.dataSyncHandler::sendRotation),
                new PositionIncrementWidget(),
                new PositionComponentWidget("x", armorStand::getX, x -> {
                    this.dataSyncHandler.sendPosition(x, armorStand.getY(), armorStand.getZ());
                }),
                new PositionComponentWidget("y", armorStand::getY, y -> {
                    this.dataSyncHandler.sendPosition(armorStand.getX(), y, armorStand.getZ());
                }),
                new PositionComponentWidget("z", armorStand::getZ, z -> {
                    this.dataSyncHandler.sendPosition(armorStand.getX(), armorStand.getY(), z);
                })
        );
    }

    @Override
    public ArmorStandScreenType getScreenType() {
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

    public static double fromWrappedDegrees(double value) {
        return (Mth.wrapDegrees(value) + 180.0) / 360.0;
    }

    public static float toWrappedDegrees(double value) {
        return (float) Mth.wrapDegrees(value * 360.0 - 180.0);
    }

    private class RotationWidget extends AbstractPositionScreenWidget {
        private final Supplier<Float> currentRotation;
        private final Consumer<Float> newRotation;

        public RotationWidget(Supplier<Float> currentRotation, Consumer<Float> newRotation) {
            super(Component.translatable("armorstatues.screen.position.rotation"));
            this.currentRotation = currentRotation;
            this.newRotation = newRotation;
        }

        @Override
        public void init(int posX, int posY) {
            super.init(posX, posY);
            NewTextureSliderButton sliderButton = ArmorStandPositionScreen.this.addRenderableWidget(new NewTextureSliderButton(posX + 76, posY + 1, 90, 20, 0, 174, ARMOR_STAND_WIDGETS_LOCATION, CommonComponents.EMPTY, fromWrappedDegrees(this.currentRotation.get()), (button, poseStack, mouseX, mouseY) -> {
                double mouseValue = NewTextureSliderButton.snapValue((mouseX - button.x) / (double) button.getWidth(), 0.125);
                ArmorStandPositionScreen.this.renderTooltip(poseStack, Component.translatable("armorstatues.screen.position.degrees", ROTATION_FORMAT.format(toWrappedDegrees(mouseValue))), mouseX, mouseY);
            }) {
                private boolean dirty;

                @Override
                protected void updateMessage() {

                }

                @Override
                protected void applyValue() {
                    this.dirty = true;
                }

                @Override
                public void onRelease(double mouseX, double mouseY) {
                    super.onRelease(mouseX, mouseY);
                    // we use #onRelease instead of directly applying in #applyValue as the armor stand will otherwise glitch out visually since the server constantly sends outdated values
                    if (this.isDirty()) {
                        this.dirty = false;
                        RotationWidget.this.newRotation.accept(toWrappedDegrees(this.value));
                    }
                }

                @Override
                public boolean isDirty() {
                    return this.dirty;
                }
            });
            sliderButton.snapInterval = 0.125;
            this.children.add(sliderButton);
            this.children.add(ArmorStandPositionScreen.this.addRenderableWidget(new ImageButton(posX + 174, posY + 1, 20, 20, 236, 64, ARMOR_STAND_WIDGETS_LOCATION, button -> {
                ArmorStandPositionScreen.this.setActiveWidget(this);
            })));
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
                AbstractWidget widget = ArmorStandPositionScreen.this.addRenderableWidget(new NewTextureButton(posX + 76 + i * 24 + (i > 1 ? 1 : 0), posY + 1, 20, 20, 0, 174, ARMOR_STAND_WIDGETS_LOCATION, Component.literal(String.valueOf(getBlockPixelIncrement(increment))), button -> {
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
            return !(activeWidget instanceof RotationWidget);
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
}
