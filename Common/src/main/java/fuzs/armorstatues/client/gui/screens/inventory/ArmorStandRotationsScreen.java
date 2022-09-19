package fuzs.armorstatues.client.gui.screens.inventory;

import fuzs.armorstatues.client.gui.components.TickButton;
import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import fuzs.armorstatues.world.inventory.ArmorStandPose;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ArmorStandRotationsScreen extends AbstractArmorStandScreen {
    private static ArmorStandPose clipboard;

    private final TickButton[] clipboardButtons = new TickButton[2];

    public ArmorStandRotationsScreen(ArmorStandMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.inventoryEntityX = 80;
        this.inventoryEntityY = 30;
        this.smallInventoryEntity = true;
    }

    @Override
    public void tick() {
        super.tick();
        for (TickButton widget : this.clipboardButtons) {
            widget.tick();
        }
    }

    @Override
    protected void init() {
        super.init();
        this.clipboardButtons[0] = this.addRenderableWidget(new TickButton(this.leftPos + 80, this.topPos + 114, 50, 20, Component.translatable("armorstatues.screen.copy"), CommonComponents.EMPTY, button -> {
            clipboard = ArmorStandPose.fromEntity(this.menu.getArmorStand());
        }));
        this.clipboardButtons[1] = this.addRenderableWidget(new TickButton(this.leftPos + 80, this.topPos + 138, 50, 20, Component.translatable("armorstatues.screen.paste"), CommonComponents.EMPTY, button -> {
            if (clipboard != null) ArmorStandPosesScreen.applyPoseAndSync(this.menu.getArmorStand(), clipboard);
        }));
    }

    @Override
    protected boolean withCloseButton() {
        return false;
    }

    @Override
    public ArmorStandScreenType<?> getScreenType() {
        return ArmorStandScreenType.ROTATIONS;
    }
}
