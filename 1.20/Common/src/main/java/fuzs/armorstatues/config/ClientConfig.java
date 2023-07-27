package fuzs.armorstatues.config;

import fuzs.puzzlesapi.api.client.statues.v1.gui.screens.armorstand.AbstractArmorStandScreen;
import fuzs.puzzleslib.api.config.v3.Config;
import fuzs.puzzleslib.api.config.v3.ConfigCore;

public class ClientConfig implements ConfigCore {
    @Config(description = {"Allows for using this mod on a server without it (like a vanilla server) when the Vanilla Tweaks Armor Statues data pack is installed without the need for being a server operator.", "Download the Vanilla Tweaks Armor Statues data pack from here: " + AbstractArmorStandScreen.VANILLA_TWEAKS_HOMEPAGE})
    public boolean useVanillaTweaksTriggers = false;
}
