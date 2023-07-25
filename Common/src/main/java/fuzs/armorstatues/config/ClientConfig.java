package fuzs.armorstatues.config;

import fuzs.armorstatues.api.client.gui.screens.armorstand.ArmorStandAlignmentsScreen;
import fuzs.puzzleslib.config.ConfigCore;
import fuzs.puzzleslib.config.annotation.Config;

public class ClientConfig implements ConfigCore {
    @Config(description = {"Allows for using this mod on a server without it (like a vanilla server) when the Vanilla Tweaks Armor Statues data pack is installed without the need for being a server operator.", "Download the Vanilla Tweaks Armor Statues data pack from here: " + ArmorStandAlignmentsScreen.VANILLA_TWEAKS_HOMEPAGE})
    public boolean useVanillaTweaksTriggers = false;
}
