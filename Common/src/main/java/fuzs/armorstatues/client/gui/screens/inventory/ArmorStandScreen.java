package fuzs.armorstatues.client.gui.screens.inventory;

import net.minecraft.client.gui.screens.Screen;

public interface ArmorStandScreen {

    ArmorStandScreenType<?> getScreenType();

    Screen createTabScreen(ArmorStandScreenType<?> screenType);
}
