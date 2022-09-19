package fuzs.armorstatues.client.gui.screens.inventory;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.client.gui.components.BoxedSliderButton;
import fuzs.armorstatues.client.gui.components.LiveSliderButton;
import fuzs.armorstatues.client.gui.components.TickButton;
import fuzs.armorstatues.client.gui.components.VerticalSliderButton;
import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import fuzs.armorstatues.world.inventory.ArmorStandPose;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.core.Rotations;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ArmorStandRotationsScreen extends AbstractArmorStandScreen {
    private static final List<PoseMutator> POSE_MUTATORS = ImmutableList.<PoseMutator>builder()
            .add(new PoseMutator(ArmorStandPose::getHeadPose, ArmorStandPose::setHeadPose))
            .add(new PoseMutator(ArmorStandPose::getBodyPose, ArmorStandPose::setBodyPose))
            .add(new PoseMutator(ArmorStandPose::getRightArmPose, ArmorStandPose::setRightArmPose))
            .add(new PoseMutator(ArmorStandPose::getLeftArmPose, ArmorStandPose::setLeftArmPose))
            .add(new PoseMutator(ArmorStandPose::getRightLegPose, ArmorStandPose::setRightLegPose))
            .add(new PoseMutator(ArmorStandPose::getLeftLegPose, ArmorStandPose::setLeftLegPose))
            .build();

    @Nullable
    private static ArmorStandPose clipboard;

    private ArmorStandPose currentPose;

    public ArmorStandRotationsScreen(ArmorStandMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.inventoryEntityX = 80;
        this.inventoryEntityY = 15;
        this.smallInventoryEntity = true;
        this.currentPose = ArmorStandPose.fromEntity(menu.getArmorStand());
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new TickButton(this.leftPos + 80, this.topPos + 93, 50, 20, Component.translatable("armorstatues.screen.rotations.randomize"), CommonComponents.EMPTY, button -> {
            this.setCurrentPose(ArmorStandPose.random());
        })).setLastClickedTicksDelay(15);
        this.addRenderableWidget(new TickButton(this.leftPos + 80, this.topPos + 131, 50, 20, Component.translatable("armorstatues.screen.rotations.copy"), CommonComponents.EMPTY, button -> {
            clipboard = this.currentPose;
        })).setLastClickedTicksDelay(20);
        this.addRenderableWidget(new TickButton(this.leftPos + 80, this.topPos + 157, 50, 20, Component.translatable("armorstatues.screen.rotations.paste"), CommonComponents.EMPTY, button -> {
            if (clipboard != null) this.setCurrentPose(clipboard);
        })).setLastClickedTicksDelay(20);
        for (int i = 0; i < POSE_MUTATORS.size(); i++) {
            PoseMutator mutator = POSE_MUTATORS.get(i);
            this.addRenderableWidget(new BoxedSliderButton(this.leftPos + 23 + i % 2 * 110, this.topPos + 7 + i / 2 * 60, () -> ArmorStandPositionScreen.fromWrappedDegrees(mutator.get().apply(this.currentPose).getWrappedX()), () -> ArmorStandPositionScreen.fromWrappedDegrees(mutator.get().apply(this.currentPose).getWrappedZ()), (button, poseStack, mouseX, mouseY) -> {
                List<Component> list = Lists.newArrayList();
                double mouseHorizontalValue = mutator.get().apply(ArmorStandRotationsScreen.this.currentPose).getWrappedX();
                list.add(Component.translatable("armorstatues.screen.rotations.x", ArmorStandPositionScreen.ROTATION_FORMAT.format(mouseHorizontalValue)));
                double mouseVerticalValue = mutator.get().apply(ArmorStandRotationsScreen.this.currentPose).getWrappedZ();
                list.add(Component.translatable("armorstatues.screen.rotations.z", ArmorStandPositionScreen.ROTATION_FORMAT.format(mouseVerticalValue)));
                this.renderTooltip(poseStack, list.stream().map(Component::getVisualOrderText).collect(Collectors.toList()), mouseX, mouseY);
            }) {
                private boolean dirty;

                @Override
                protected void applyValue() {
                    this.dirty = true;
                    Rotations currentRotations = mutator.get().apply(ArmorStandRotationsScreen.this.currentPose);
                    Rotations newRotations = new Rotations(ArmorStandPositionScreen.toWrappedDegrees(this.horizontalValue), currentRotations.getWrappedY(), ArmorStandPositionScreen.toWrappedDegrees(this.verticalValue));
                    ArmorStandRotationsScreen.this.currentPose = mutator.set().apply(ArmorStandRotationsScreen.this.currentPose, newRotations);
                }

                @Override
                public void onRelease(double mouseX, double mouseY) {
                    super.onRelease(mouseX, mouseY);
                    if (this.isDirty()) {
                        this.dirty = false;
                        ArmorStandRotationsScreen.this.applyCurrentPoseToEntity();
                    }
                }

                @Override
                public boolean isDirty() {
                    return this.dirty;
                }
            });
            this.addRenderableWidget(new VerticalSliderButton(this.leftPos + 6 + i % 2 * 183, this.topPos + 7 + i / 2 * 60, () -> ArmorStandPositionScreen.fromWrappedDegrees(mutator.get().apply(this.currentPose).getWrappedY()), (button, poseStack, mouseX, mouseY) -> {
                double mouseValue = mutator.get().apply(ArmorStandRotationsScreen.this.currentPose).getWrappedY();
                this.renderTooltip(poseStack, Component.translatable("armorstatues.screen.rotations.y", ArmorStandPositionScreen.ROTATION_FORMAT.format(mouseValue)), mouseX, mouseY);
            }) {
                private boolean dirty;

                @Override
                protected void applyValue() {
                    this.dirty = true;
                    Rotations currentRotations = mutator.get().apply(ArmorStandRotationsScreen.this.currentPose);
                    Rotations newRotations = new Rotations(currentRotations.getWrappedX(), ArmorStandPositionScreen.toWrappedDegrees(this.value), currentRotations.getWrappedZ());
                    ArmorStandRotationsScreen.this.currentPose = mutator.set().apply(ArmorStandRotationsScreen.this.currentPose, newRotations);
                }

                @Override
                public void onRelease(double mouseX, double mouseY) {
                    super.onRelease(mouseX, mouseY);
                    if (this.isDirty()) {
                        this.dirty = false;
                        ArmorStandRotationsScreen.this.applyCurrentPoseToEntity();
                    }
                }

                @Override
                public boolean isDirty() {
                    return this.dirty;
                }
            });
        }
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        ArmorStand armorStand = this.menu.getArmorStand();
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
    public ArmorStandScreenType<?> getScreenType() {
        return ArmorStandScreenType.ROTATIONS;
    }

    private void setCurrentPose(ArmorStandPose currentPose) {
        this.currentPose = currentPose;
        this.applyCurrentPoseToEntity();
        this.refreshLiveButtons();
    }

    private void applyCurrentPoseToEntity() {
        ArmorStandPosesScreen.applyPoseAndSync(this.menu.getArmorStand(), this.currentPose);
    }

    private void refreshLiveButtons() {
        for (GuiEventListener child : this.children()) {
            if (child instanceof LiveSliderButton button) button.refreshValues();
        }
    }

    private record PoseMutator(Function<ArmorStandPose, Rotations> get, BiFunction<ArmorStandPose, Rotations, ArmorStandPose> set) {

    }
}
