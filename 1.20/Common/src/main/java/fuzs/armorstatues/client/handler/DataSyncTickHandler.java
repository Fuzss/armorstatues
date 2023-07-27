package fuzs.armorstatues.client.handler;

import fuzs.puzzlesapi.api.client.statues.v1.gui.screens.armorstand.ArmorStandScreen;
import fuzs.puzzlesapi.api.statues.v1.network.client.data.DataSyncHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;

public class DataSyncTickHandler {
    @Nullable
    private static DataSyncHandler dataSyncHandler;

    public static void onRemove(Screen screen) {
        if (screen instanceof ArmorStandScreen armorStandScreen && armorStandScreen.getDataSyncHandler().shouldContinueTicking()) {
            dataSyncHandler = armorStandScreen.getDataSyncHandler();
        }
    }

    public static void onEndClientTick(Minecraft minecraft) {
        if (!(minecraft.screen instanceof ArmorStandScreen) && dataSyncHandler != null) {
            if (dataSyncHandler.shouldContinueTicking()) {
                dataSyncHandler.tick();
            } else {
                dataSyncHandler = null;
            }
        }
    }
}
