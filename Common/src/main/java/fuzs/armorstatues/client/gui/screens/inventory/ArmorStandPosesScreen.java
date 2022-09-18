package fuzs.armorstatues.client.gui.screens.inventory;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.client.gui.components.TickButton;
import fuzs.armorstatues.network.client.C2SArmorStandPoseMessage;
import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import fuzs.armorstatues.world.inventory.ArmorStandPose;
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
    private TickButton randomizeButton;

    public ArmorStandPosesScreen(ArmorStandMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.inventoryEntityX = 5;
        this.inventoryEntityY = 15;
    }

    @Override
    public void tick() {
        super.tick();
        this.randomizeButton.tick();
    }

    @Override
    protected void init() {
        super.init();
        this.randomizeButton = this.addRenderableWidget(new TickButton(this.leftPos + 5, this.topPos + 128, 76, 20, Component.translatable("armorstatues.screen.pose.randomize"), Component.translatable("armorstatues.screen.pose.randomized"), button -> {
            this.applyPoseToEntity(ArmorStandPose.random());
        }));
        this.randomizeButton.lastClickedTicksDelay = 15;
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
                getPoseAt(ii).ifPresent(this::applyPoseToEntity);
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

    private void applyPoseToEntity(ArmorStandPose pose) {
        pose.applyToEntity(this.menu.getArmorStand());
        ArmorStatues.NETWORK.sendToServer(new C2SArmorStandPoseMessage(pose));
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        super.renderBg(poseStack, partialTick, mouseX, mouseY);
        ArmorStand armorStand = this.menu.getArmorStand();
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
    public ArmorStandScreenType<?> getScreenType() {
        return ArmorStandScreenType.POSES;
    }

    private static Optional<ArmorStandPose> getPoseAt(int index) {
        index += firstPoseIndex;
        if (index >= ArmorStandPose.values().length) return Optional.empty();
        return Optional.of(ArmorStandPose.values()[index]);
    }
}
