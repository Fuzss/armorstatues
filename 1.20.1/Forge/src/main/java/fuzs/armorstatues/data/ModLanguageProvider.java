package fuzs.armorstatues.data;

import fuzs.armorstatues.client.gui.screens.armorstand.ArmorStandVanillaTweaksScreen;
import fuzs.armorstatues.init.ModRegistry;
import fuzs.armorstatues.network.client.data.CommandDataSyncHandler;
import fuzs.puzzleslib.api.data.v1.AbstractLanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(GatherDataEvent evt, String modId) {
        super(evt, modId);
    }

    @Override
    protected void addTranslations() {
        // Armor Statues
        this.add(CommandDataSyncHandler.FAILURE_TRANSLATION_KEY, "Unable to modify armor stand data: %s");
        this.add(CommandDataSyncHandler.NO_PERMISSION_TRANSLATION_KEY, "No Permission");
        this.add(CommandDataSyncHandler.NO_ARMOR_STAND_TRANSLATION_KEY, "No Valid Armor Stand");
        this.add(CommandDataSyncHandler.OUT_OF_RANGE_TRANSLATION_KEY, "Out Of Range");
        this.add(CommandDataSyncHandler.NOT_FINISHED_TRANSLATION_KEY, "Queue Not Empty");
        this.add(CommandDataSyncHandler.FINISHED_TRANSLATION_KEY, "Finished sending queued armor stand data");
        this.add(ModRegistry.ALIGNMENTS_SCREEN_TYPE.getTranslationKey(), "Alignments");
        this.add(ModRegistry.VANILLA_TWEAKS_SCREEN_TYPE.getTranslationKey(), "Vanilla Tweaks");
        this.add(ArmorStandVanillaTweaksScreen.TRIGGER_SENT_TRANSLATION_KEY, "Sent!");
        this.add(ArmorStandVanillaTweaksScreen.CHECK_TARGET_TRANSLATION_KEY, "Check Armor Stand Target");
        this.add(ArmorStandVanillaTweaksScreen.CHECK_TARGET_DESCRIPTION_KEY, "Highlights the closest armor stand within three blocks of the player which will be adjusted. Due to how data packs work, this isn't necessarily the armor stand which opened this menu.");
        this.add(ArmorStandVanillaTweaksScreen.LOCK_TRANSLATION_KEY, "Lock");
        this.add(ArmorStandVanillaTweaksScreen.LOCK_DESCRIPTION_KEY, "Locking an armor stand prevents it from being changed using this menu and disables interaction with the equipment slots.");
        this.add(ArmorStandVanillaTweaksScreen.UNLOCK_TRANSLATION_KEY, "Unlock");
        this.add(ArmorStandVanillaTweaksScreen.UNLOCK_DESCRIPTION_KEY, "Unlocking an armor stand reverts any adjustments made via a previous lock action.");
        this.add(ArmorStandVanillaTweaksScreen.TOOL_RACK_TRANSLATION_KEY, "Align Tool As Tool Rack");
        this.add(ArmorStandVanillaTweaksScreen.TOOL_RACK_DESCRIPTION_KEY, "Align an armor stand with a tripwire hook on the wall above it so that a tool held by it appears to be hanging up. Also locks the armor stand and disables all slots except the mainhand.");
        this.add(ArmorStandVanillaTweaksScreen.SWAP_MAINHAND_AND_OFFHAND_TRANSLATION_KEY, "Swap Mainhand & Offhand");
        this.add(ArmorStandVanillaTweaksScreen.SWAP_MAINHAND_AND_OFFHAND_DESCRIPTION_KEY, "Swaps items between the main hand and off hand equipment slots.");
        this.add(ArmorStandVanillaTweaksScreen.SWAP_MAINHAND_AND_HEAD_TRANSLATION_KEY, "Swap Mainhand & Helmet");
        this.add(ArmorStandVanillaTweaksScreen.SWAP_MAINHAND_AND_HEAD_DESCRIPTION_KEY, "Swaps items between the main hand and helmet equipment slots.");
    }
}
