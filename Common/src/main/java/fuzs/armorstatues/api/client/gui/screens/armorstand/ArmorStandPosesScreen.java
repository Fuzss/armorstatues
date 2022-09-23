package fuzs.armorstatues.api.client.gui.screens.armorstand;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.api.network.client.data.DataSyncHandler;
import fuzs.armorstatues.api.world.inventory.ArmorStandHolder;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandPose;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandScreenType;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;

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
        this.cycleButtons[0] = this.addRenderableWidget(new ImageButton(this.leftPos + 17, this.topPos + 153, 20, 20, 156, 64, ARMOR_STAND_WIDGETS_LOCATION, button -> {
            firstPoseIndex -= POSES_PER_PAGE;
            this.toggleCycleButtons();
        }));
        this.cycleButtons[1] = this.addRenderableWidget(new ImageButton(this.leftPos + 49, this.topPos + 153, 20, 20, 176, 64, ARMOR_STAND_WIDGETS_LOCATION, button -> {
            firstPoseIndex += POSES_PER_PAGE;
            this.toggleCycleButtons();
        }));
        for (int i = 0; i < this.poseButtons.length; i++) {
            final int ii = i;
            this.poseButtons[i] = this.addRenderableWidget(new ImageButton(this.leftPos + 83 + i % 2 * 62, this.topPos + 9 + i / 2 * 88, 60, 82, 76, 0, 82, ARMOR_STAND_WIDGETS_LOCATION, 256, 256, button -> {
                getPoseAt(ii).ifPresent(this.dataSyncHandler::sendPose);
            }, (Button button, PoseStack poseStack, int mouseX, int mouseY) -> {
                getPoseAt(ii).ifPresent(pose -> this.renderTooltip(poseStack, pose.getComponent(), mouseX, mouseY));
            }, CommonComponents.EMPTY));
        }
        this.toggleCycleButtons();
    }

    private void toggleCycleButtons() {
        this.cycleButtons[0].active = firstPoseIndex > 0;
        this.cycleButtons[1].active = firstPoseIndex + POSES_PER_PAGE < ArmorStandPose.values().length;
        for (int i = 0; i < this.poseButtons.length; i++) {
            this.poseButtons[i].visible = getPoseAt(i).isPresent();
        }
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
                InventoryScreen.renderEntityInInventory(this.leftPos + 112 + i % 2 * 62, this.topPos + 79 + i / 2 * 88, 30, this.leftPos + 112 + i % 2 * 62 - 10 - this.mouseX, this.topPos + 79 + i / 2 * 88 - 44 - this.mouseY, armorStand);
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
