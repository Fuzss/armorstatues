package fuzs.armorstatues.data;

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
        this.add(CommandDataSyncHandler.NOT_FINISHED_TRANSLATION_KEY, "Queue Not Empty");
        this.add(CommandDataSyncHandler.FINISHED_TRANSLATION_KEY, "Finished sending queued armor stand data");
        this.add(ModRegistry.ALIGNMENTS_SCREEN_TYPE.getTranslationKey(), "Alignments");
    }
}
