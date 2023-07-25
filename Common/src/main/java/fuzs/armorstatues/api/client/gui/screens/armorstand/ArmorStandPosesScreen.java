package fuzs.armorstatues.api.client.gui.screens.armorstand;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.api.network.client.data.DataSyncHandler;
import fuzs.armorstatues.api.world.inventory.ArmorStandHolder;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandPose;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandScreenType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;
import java.util.Optional;

public class ArmorStandPosesScreen extends AbstractArmorStandScreen {
    private static final int POSES_PER_PAGE = 4;

    private static int firstPoseIndex;

    private final AbstractWidget[] cycleButtons = new AbstractWidget[2];
    private final AbstractWidget[] poseButtons = new AbstractWidget[POSES_PER_PAGE];

    public ArmorStandPosesScreen(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        super(holder, inventory, component, dataSyncHandler);
        this.inventoryEntityX = 5;
        this.inventoryEntityY = 40;
    }

    @Override
    protected void init() {
        super.init();
        this.cycleButtons[0] = this.addRenderableWidget(new ImageButton(this.leftPos + 17, this.topPos + 153, 20, 20, 156, 64, getArmorStandWidgetsLocation(), button -> {
            this.toggleCycleButtons(-POSES_PER_PAGE);
        }));
        this.cycleButtons[1] = this.addRenderableWidget(new ImageButton(this.leftPos + 49, this.topPos + 153, 20, 20, 176, 64, getArmorStandWidgetsLocation(), button -> {
            this.toggleCycleButtons(POSES_PER_PAGE);
        }));
        for (int i = 0; i < this.poseButtons.length; i++) {
            final int index = i;
            this.poseButtons[i] = this.addRenderableWidget(new ImageButton(this.leftPos + 83 + i % 2 * 62, this.topPos + 9 + i / 2 * 88, 60, 82, 76, 0, 82, getArmorStandWidgetsLocation(), 256, 256, button -> {
                getPoseAt(index).ifPresent(this.dataSyncHandler::sendPose);
            }, (Button button, PoseStack poseStack, int mouseX, int mouseY) -> {
                getPoseAt(index).ifPresent(pose -> {
                    String translationKey = pose.getTranslationKey();
                    if (translationKey != null) {
                        Component component = Component.translatable(translationKey);
                        List<Component> lines = Lists.newArrayList(component);
                        String source = pose.getSource();
                        if (!StringUtil.isNullOrEmpty(source)) {
                            lines.add(Component.translatable("armorstatues.entity.armor_stand.pose.by", source).withStyle(ChatFormatting.GRAY));
                        }
                        this.renderTooltip(poseStack, lines, Optional.empty(), mouseX, mouseY);
                    }
                });
            }, CommonComponents.EMPTY));
        }
        this.toggleCycleButtons(0);
        this.addVanillaTweaksCreditButton();
    }

    private void toggleCycleButtons(int increment) {
        int newFirstPoseIndex = firstPoseIndex + increment;
        if (newFirstPoseIndex >= 0 && newFirstPoseIndex < ArmorStandPose.values().length) {
            firstPoseIndex = newFirstPoseIndex;
            this.cycleButtons[0].active = newFirstPoseIndex - POSES_PER_PAGE >= 0;
            this.cycleButtons[1].active = newFirstPoseIndex + POSES_PER_PAGE < ArmorStandPose.values().length;
            for (int i = 0; i < this.poseButtons.length; i++) {
                this.poseButtons[i].visible = getPoseAt(i).isPresent();
            }
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (super.mouseScrolled(mouseX, mouseY, delta)) {
            return true;
        } else if (mouseX >= this.leftPos && mouseX < this.leftPos + this.imageWidth && mouseY >= this.topPos && mouseY < this.topPos + this.imageHeight) {
            delta = Math.signum(delta);
            if (delta != 0.0) {
                this.toggleCycleButtons((int) (-1.0 * delta * POSES_PER_PAGE));
                return true;
            }
        }
        return false;
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        super.renderBg(poseStack, partialTick, mouseX, mouseY);
        ArmorStand armorStand = this.holder.getArmorStand();
        ArmorStandPose entityPose = ArmorStandPose.fromEntity(armorStand);
        for (int i = 0; i < POSES_PER_PAGE; i++) {
            Optional<ArmorStandPose> pose = getPoseAt(i);
            if (pose.isPresent()) {
                pose.get().applyToEntity(armorStand);
                this.renderArmorStandInInventory(this.leftPos + 112 + i % 2 * 62, this.topPos + 79 + i / 2 * 88, 30, this.leftPos + 112 + i % 2 * 62 - 10 - this.mouseX, this.topPos + 79 + i / 2 * 88 - 44 - this.mouseY);
            }
        }
        entityPose.applyToEntity(armorStand);
    }

    @Override
    protected boolean withCloseButton() {
        return false;
    }

    @Override
    public ArmorStandScreenType getScreenType() {
        return ArmorStandScreenType.POSES;
    }

    private static Optional<ArmorStandPose> getPoseAt(int index) {
        index += firstPoseIndex;
        if (index >= ArmorStandPose.values().length) return Optional.empty();
        return Optional.of(ArmorStandPose.values()[index]);
    }
}
