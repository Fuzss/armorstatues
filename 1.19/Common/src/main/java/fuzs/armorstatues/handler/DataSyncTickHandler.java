package fuzs.armorstatues.handler;

import fuzs.armorstatues.api.client.gui.screens.armorstand.ArmorStandScreen;
import fuzs.armorstatues.api.network.client.data.DataSyncHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;

public class DataSyncTickHandler {
    @Nullable
    private static DataSyncHandler dataSyncHandler;

    public static void onScreenClose(Screen screen) {
        if (screen instanceof ArmorStandScreen armorStandScreen && armorStandScreen.getDataSyncHandler().shouldContinueTicking()) {
            dataSyncHandler = armorStandScreen.getDataSyncHandler();
        }
    }

    public static void onClientTickEnd(Minecraft minecraft) {
        if (!(minecraft.screen instanceof ArmorStandScreen) && dataSyncHandler != null) {
            if (dataSyncHandler.shouldContinueTicking()) {
                dataSyncHandler.tick();
            } else {
                dataSyncHandler = null;
            }
        }
    }
}
