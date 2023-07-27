package fuzs.armorstatues.api.client.gui.screens.armorstand;

import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.LivingEntity;

public interface ArmorStandInInventoryRenderer {
    ArmorStandInInventoryRenderer SIMPLE = InventoryScreen::renderEntityInInventory;

    void renderEntityInInventory(int posX, int posY, int scale, float mouseX, float mouseY, LivingEntity livingEntity);

    static void setArmorStandRenderer(ArmorStandInInventoryRenderer armorStandRenderer) {
        AbstractArmorStandScreen.armorStandRenderer = armorStandRenderer;
    }
}
