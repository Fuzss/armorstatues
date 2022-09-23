package fuzs.armorstatues.api.client.gui.screens.armorstand;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.api.client.gui.components.BoxedSliderButton;
import fuzs.armorstatues.api.client.gui.components.LiveSliderButton;
import fuzs.armorstatues.api.client.gui.components.NewTextureTickButton;
import fuzs.armorstatues.api.client.gui.components.VerticalSliderButton;
import fuzs.armorstatues.api.network.client.data.DataSyncHandler;
import fuzs.armorstatues.api.world.inventory.ArmorStandHolder;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandPose;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandScreenType;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class ArmorStandRotationsScreen extends AbstractArmorStandScreen {
    private static boolean clampRotations = true;
    @Nullable
    private static ArmorStandPose clipboard;

    private final AbstractWidget[] lockButtons = new AbstractWidget[2];
    private ArmorStandPose currentPose;

    public ArmorStandRotationsScreen(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        super(holder, inventory, component, dataSyncHandler);
        this.inventoryEntityX = 80;
        this.inventoryEntityY = 58;
        this.smallInventoryEntity = true;
        this.currentPose = ArmorStandPose.fromEntity(holder.getArmorStand());
    }

    @Override
    protected void init() {
        super.init();
        this.lockButtons[0] = this.addRenderableWidget(new ImageButton(this.leftPos + 83, this.topPos + 10, 20, 20, 156, 124, 20, ARMOR_STAND_WIDGETS_LOCATION, 256, 256, button -> {
            clampRotations = true;
            this.toggleLockButtons();
            this.refreshLiveButtons();
        }, (button, poseStack, mouseX, mouseY) -> {
            this.renderTooltip(poseStack, Component.translatable("armorstatues.screen.rotations.unlimited"), mouseX, mouseY);
        }, Component.translatable("armorstatues.screen.rotations.unlimited")));
        this.lockButtons[1] = this.addRenderableWidget(new ImageButton(this.leftPos + 83, this.topPos + 10, 20, 20, 136, 124, 20, ARMOR_STAND_WIDGETS_LOCATION, 256, 256, button -> {
            clampRotations = false;
            this.toggleLockButtons();
            this.refreshLiveButtons();
        }, (button, poseStack, mouseX, mouseY) -> {
            this.renderTooltip(poseStack, Component.translatable("armorstatues.screen.rotations.limited"), mouseX, mouseY);
        }, Component.translatable("armorstatues.screen.rotations.limited")));
        this.addRenderableWidget(new ImageButton(this.leftPos + 107, this.topPos + 10, 20, 20, 136, 64, 20, ARMOR_STAND_WIDGETS_LOCATION, 256, 256, button -> {}, (button, poseStack, mouseX, mouseY) -> {
            this.renderTooltip(poseStack, Component.translatable("armorstatues.screen.rotations.tip"), mouseX, mouseY);
        }, CommonComponents.EMPTY) {

            @Override
            public void playDownSound(SoundManager handler) {

            }
        });
        this.addRenderableWidget(new NewTextureTickButton(this.leftPos + 83, this.topPos + 34, 20, 20, 240, 124, ARMOR_STAND_WIDGETS_LOCATION, button -> {
            this.setCurrentPose(ArmorStandPose.NONE);
        }, (button, poseStack, mouseX, mouseY) -> {
            this.renderTooltip(poseStack, Component.translatable("armorstatues.screen.rotations.reset"), mouseX, mouseY);
        }));
        this.addRenderableWidget(new NewTextureTickButton(this.leftPos + 107, this.topPos + 34, 20, 20, 192, 124, ARMOR_STAND_WIDGETS_LOCATION, button -> {
            this.setCurrentPose(this.holder.getDataProvider().getRandomPose(clampRotations));
        }, (button, poseStack, mouseX, mouseY) -> {
            this.renderTooltip(poseStack, Component.translatable("armorstatues.screen.rotations.randomize"), mouseX, mouseY);
        }));
        AbstractWidget pasteButton = this.addRenderableWidget(new NewTextureTickButton(this.leftPos + 83, this.topPos + 158, 44, 20, 224, 124, ARMOR_STAND_WIDGETS_LOCATION, button -> {
            if (clipboard != null) this.setCurrentPose(clipboard);
        }, (button, poseStack, mouseX, mouseY) -> {
            this.renderTooltip(poseStack, Component.translatable("armorstatues.screen.rotations.paste"), mouseX, mouseY);
        }));
        pasteButton.active = clipboard != null;
        this.addRenderableWidget(new NewTextureTickButton(this.leftPos + 83, this.topPos + 134, 44, 20, 208, 124, ARMOR_STAND_WIDGETS_LOCATION, button -> {
            clipboard = this.currentPose;
            pasteButton.active = true;
        }, (button, poseStack, mouseX, mouseY) -> {
            this.renderTooltip(poseStack, Component.translatable("armorstatues.screen.rotations.copy"), mouseX, mouseY);
        }));
        ArmorStand armorStand = this.holder.getArmorStand();
        for (int i = 0; i < ArmorStandPose.PosePartMutator.values().length; i++) {
            ArmorStandPose.PosePartMutator mutator = ArmorStandPose.PosePartMutator.values()[i];
            this.addRenderableWidget(new BoxedSliderButton(this.leftPos + 23 + i % 2 * 110, this.topPos + 7 + i / 2 * 60, () -> mutator.getNormalizedRotationsAtAxis(1, this.currentPose, clampRotations), () -> mutator.getNormalizedRotationsAtAxis(0, this.currentPose, clampRotations), (button, poseStack, mouseX, mouseY) -> {
                if (!button.isActive()) return;
                List<Component> lines = Lists.newArrayList();
                lines.add(mutator == ArmorStandPose.PosePartMutator.BODY ? this.holder.getDataProvider().getBodyComponent() : mutator.getComponent());
                lines.add(mutator.getAxisComponent(this.currentPose, 0));
                lines.add(mutator.getAxisComponent(this.currentPose, 1));
                this.renderTooltip(poseStack, lines.stream().map(Component::getVisualOrderText).collect(Collectors.toList()), mouseX, mouseY);
            }) {
                private boolean dirty;

                @Override
                protected void applyValue() {
                    this.dirty = true;
                    ArmorStandRotationsScreen.this.currentPose = mutator.setRotationsAtAxis(1, ArmorStandRotationsScreen.this.currentPose, this.horizontalValue, clampRotations);
                    ArmorStandRotationsScreen.this.currentPose = mutator.setRotationsAtAxis(0, ArmorStandRotationsScreen.this.currentPose, this.verticalValue, clampRotations);
                }

                @Override
                public void onRelease(double mouseX, double mouseY) {
                    super.onRelease(mouseX, mouseY);
                    if (this.isDirty()) {
                        this.dirty = false;
                        ArmorStandRotationsScreen.this.dataSyncHandler.sendPose(ArmorStandRotationsScreen.this.currentPose);
                    }
                }

                @Override
                public boolean isDirty() {
                    return this.dirty;
                }
            }).active = mutator.test(armorStand);
            this.addRenderableWidget(new VerticalSliderButton(this.leftPos + 6 + i % 2 * 183, this.topPos + 7 + i / 2 * 60, () -> mutator.getNormalizedRotationsAtAxis(2, this.currentPose, clampRotations), (button, poseStack, mouseX, mouseY) -> {
                if (!button.isActive()) return;
                List<Component> lines = Lists.newArrayList();
                lines.add(mutator == ArmorStandPose.PosePartMutator.BODY ? this.holder.getDataProvider().getBodyComponent() : mutator.getComponent());
                lines.add(mutator.getAxisComponent(this.currentPose, 2));
                this.renderTooltip(poseStack, lines.stream().map(Component::getVisualOrderText).collect(Collectors.toList()), mouseX, mouseY);
            }) {
                private boolean dirty;

                @Override
                protected void applyValue() {
                    this.dirty = true;
                    ArmorStandRotationsScreen.this.currentPose = mutator.setRotationsAtAxis(2, ArmorStandRotationsScreen.this.currentPose, this.value, clampRotations);
                }

                @Override
                public void onRelease(double mouseX, double mouseY) {
                    super.onRelease(mouseX, mouseY);
                    if (this.isDirty()) {
                        this.dirty = false;
                        ArmorStandRotationsScreen.this.dataSyncHandler.sendPose(ArmorStandRotationsScreen.this.currentPose);
                    }
                }

                @Override
                public boolean isDirty() {
                    return this.dirty;
                }
            }).active = mutator.test(armorStand);
            this.toggleLockButtons();
        }
    }

    private void toggleLockButtons() {
        this.lockButtons[0].visible = !clampRotations;
        this.lockButtons[1].visible = clampRotations;
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        ArmorStand armorStand = this.holder.getArmorStand();
        ArmorStandPose entityPose = ArmorStandPose.fromEntity(armorStand);
        this.currentPose.applyToEntity(armorStand);
        super.renderBg(poseStack, partialTick, mouseX, mouseY);
        entityPose.applyToEntity(armorStand);
    }

    @Override
    protected boolean withCloseButton() {
        return false;
    }

    @Override
    public ArmorStandScreenType getScreenType() {
        return ArmorStandScreenType.ROTATIONS;
    }

    private void setCurrentPose(ArmorStandPose currentPose) {
        this.currentPose = currentPose;
        this.dataSyncHandler.sendPose(this.currentPose);
        this.refreshLiveButtons();
    }

    private void refreshLiveButtons() {
        for (GuiEventListener child : this.children()) {
            if (child instanceof LiveSliderButton button) button.refreshValues();
        }
    }
}
