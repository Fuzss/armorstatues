package fuzs.armorstatues.data.client;

import fuzs.armorstatues.client.gui.screens.armorstand.ArmorStandVanillaTweaksScreen;
import fuzs.armorstatues.world.inventory.data.ArmorStandScreenTypes;
import fuzs.armorstatues.network.client.data.CommandDataSyncHandler;
import fuzs.puzzleslib.api.client.data.v2.AbstractLanguageProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations(TranslationBuilder builder) {
        builder.add(CommandDataSyncHandler.FAILURE_TRANSLATION_KEY, "Unable to modify armor stand data: %s");
        builder.add(CommandDataSyncHandler.NO_PERMISSION_TRANSLATION_KEY, "No Permission");
        builder.add(CommandDataSyncHandler.NO_ARMOR_STAND_TRANSLATION_KEY, "No Valid Armor Stand");
        builder.add(CommandDataSyncHandler.OUT_OF_RANGE_TRANSLATION_KEY, "Out Of Range");
        builder.add(CommandDataSyncHandler.NOT_FINISHED_TRANSLATION_KEY, "Queue Not Empty");
        builder.add(CommandDataSyncHandler.FINISHED_TRANSLATION_KEY, "Finished sending queued armor stand data");
        builder.add(ArmorStandScreenTypes.POSITION.getTranslationKey(), "Position");
        builder.add(ArmorStandScreenTypes.ALIGNMENTS.getTranslationKey(), "Alignments");
        builder.add(ArmorStandScreenTypes.VANILLA_TWEAKS.getTranslationKey(), "Vanilla Tweaks");
        builder.add(ArmorStandVanillaTweaksScreen.TRIGGER_SENT_TRANSLATION_KEY, "Sent!");
        builder.add(ArmorStandVanillaTweaksScreen.CHECK_TARGET_TRANSLATION_KEY, "Check Armor Stand Target");
        builder.add(ArmorStandVanillaTweaksScreen.CHECK_TARGET_DESCRIPTION_KEY,
                "Highlights the closest armor stand within three blocks of the player which will be adjusted. Due to how data packs work, builder isn't necessarily the armor stand which opened builder menu.");
        builder.add(ArmorStandVanillaTweaksScreen.LOCK_TRANSLATION_KEY, "Lock");
        builder.add(ArmorStandVanillaTweaksScreen.LOCK_DESCRIPTION_KEY,
                "Locking an armor stand prevents it from being changed using builder menu and disables interaction with the equipment slots.");
        builder.add(ArmorStandVanillaTweaksScreen.UNLOCK_TRANSLATION_KEY, "Unlock");
        builder.add(ArmorStandVanillaTweaksScreen.UNLOCK_DESCRIPTION_KEY,
                "Unlocking an armor stand reverts any adjustments made via a previous lock action.");
        builder.add(ArmorStandVanillaTweaksScreen.TOOL_RACK_TRANSLATION_KEY, "Align Tool As Tool Rack");
        builder.add(ArmorStandVanillaTweaksScreen.TOOL_RACK_DESCRIPTION_KEY,
                "Align an armor stand with a tripwire hook on the wall above it so that a tool held by it appears to be hanging up. Also locks the armor stand and disables all slots except the mainhand.");
        builder.add(ArmorStandVanillaTweaksScreen.SWAP_MAINHAND_AND_OFFHAND_TRANSLATION_KEY, "Swap Mainhand & Offhand");
        builder.add(ArmorStandVanillaTweaksScreen.SWAP_MAINHAND_AND_OFFHAND_DESCRIPTION_KEY,
                "Swaps items between the main hand and off hand equipment slots.");
        builder.add(ArmorStandVanillaTweaksScreen.SWAP_MAINHAND_AND_HEAD_TRANSLATION_KEY, "Swap Mainhand & Helmet");
        builder.add(ArmorStandVanillaTweaksScreen.SWAP_MAINHAND_AND_HEAD_DESCRIPTION_KEY,
                "Swaps items between the main hand and helmet equipment slots.");
    }
}
