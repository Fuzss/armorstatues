package fuzs.armorstatues.client;

import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.api.client.StatuesApiClient;
import fuzs.armorstatues.api.client.gui.screens.armorstand.ArmorStandScreen;
import fuzs.armorstatues.client.handler.ArmorStandTooltipHandler;
import fuzs.armorstatues.handler.ArmorStandInteractHandler;
import fuzs.armorstatues.handler.DataSyncTickHandler;
import fuzs.puzzleslib.client.core.ClientCoreServices;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;

public class ArmorStatuesFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientCoreServices.FACTORIES.clientModConstructor(ArmorStatues.MOD_ID).accept(new StatuesApiClient());
        ClientCoreServices.FACTORIES.clientModConstructor(ArmorStatues.MOD_ID).accept(new ArmorStatuesClient());
        registerHandlers();
    }

    private static void registerHandlers() {
        ItemTooltipCallback.EVENT.register(ArmorStandTooltipHandler::onItemTooltip);
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            ArmorStandInteractHandler.clearPresentServerside();
        });
        ClientTickEvents.END_CLIENT_TICK.register(DataSyncTickHandler::onClientTickEnd);
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof ArmorStandScreen) {
                ScreenEvents.remove(screen).register(DataSyncTickHandler::onScreenClose);
            }
        });
    }
}
