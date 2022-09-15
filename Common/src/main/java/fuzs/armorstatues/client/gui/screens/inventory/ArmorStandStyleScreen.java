package fuzs.armorstatues.client.gui.screens.inventory;

import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ArmorStandStyleScreen extends AbstractArmorStandScreen {

    public ArmorStandStyleScreen(ArmorStandMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
    }

    @Override
    public void removed() {
        super.removed();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    @Override
    public ArmorStandScreenType<?> getScreenType() {
        return ArmorStandScreenType.STYLE;
    }
}
