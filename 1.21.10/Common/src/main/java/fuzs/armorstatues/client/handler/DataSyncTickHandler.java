package fuzs.armorstatues.client.handler;

import fuzs.statuemenus.api.v1.client.gui.screens.StatueScreen;
import fuzs.statuemenus.api.v1.network.client.data.DataSyncHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;

public class DataSyncTickHandler {
    @Nullable
    private static DataSyncHandler dataSyncHandler;

    public static void onRemove(Screen screen) {
        if (screen instanceof StatueScreen statueScreen && statueScreen.getDataSyncHandler().shouldContinueTicking()) {
            dataSyncHandler = statueScreen.getDataSyncHandler();
        }
    }

    public static void onEndClientTick(Minecraft minecraft) {
        if (minecraft.player != null && !(minecraft.screen instanceof StatueScreen) && dataSyncHandler != null) {
            if (dataSyncHandler.shouldContinueTicking()) {
                dataSyncHandler.tick();
            } else {
                dataSyncHandler = null;
            }
        }
    }
}
