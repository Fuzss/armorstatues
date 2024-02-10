package fuzs.armorstatues.config;

import fuzs.puzzleslib.api.config.v3.Config;
import fuzs.puzzleslib.api.config.v3.ConfigCore;
import fuzs.statuemenus.api.v1.client.gui.screens.AbstractArmorStandScreen;

public class ClientConfig implements ConfigCore {
    @Config(description = {"Allows for using this mod on a server without it (like a vanilla server) when the Vanilla Tweaks Armor Statues data pack is installed without the need for being a server operator.", "Download the Vanilla Tweaks Armor Statues data pack from here: " + AbstractArmorStandScreen.VANILLA_TWEAKS_HOMEPAGE})
    public boolean useVanillaTweaksTriggers = false;
    @Config(description = "Do not check if the client has the necessary permission level for executing the '/data' command when trying to edit an armor stand. Useful when the current server is using custom permissions handling.")
    public boolean overrideClientPermissionsCheck = false;
    @Config(description = "The delay in ticks for sending queued client commands for editing armor stands to the server. Increase this values if commands are sent too quickly and the server fails to process all of them.")
    @Config.IntRange(min = 20)
    public int clientCommandDelay = 20;
}
