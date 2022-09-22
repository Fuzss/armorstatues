package fuzs.armorstatues.api.client.gui.screens.armorstand;

import fuzs.armorstatues.world.inventory.ArmorStandScreenType;
import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;

public interface ArmorStandScreen {

    ArmorStandScreenType getScreenType();

    <T extends Screen & MenuAccess<ArmorStandMenu> & ArmorStandScreen> T createScreenType(ArmorStandScreenType screenType);

    void setMouseX(int mouseX);

    void setMouseY(int mouseY);
}
