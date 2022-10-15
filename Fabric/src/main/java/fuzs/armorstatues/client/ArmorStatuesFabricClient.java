package fuzs.armorstatues.client;

import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.api.client.ArmorStatuesApiClient;
import fuzs.armorstatues.client.handler.ArmorStandTooltipHandler;
import fuzs.armorstatues.handler.ArmorStandInteractHandler;
import fuzs.puzzleslib.client.core.ClientCoreServices;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

public class ArmorStatuesFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientCoreServices.FACTORIES.clientModConstructor(ArmorStatues.MOD_ID).accept(new ArmorStatuesApiClient());
        ClientCoreServices.FACTORIES.clientModConstructor(ArmorStatues.MOD_ID).accept(new ArmorStatuesClient());
        registerHandlers();
    }

    private static void registerHandlers() {
        ItemTooltipCallback.EVENT.register(ArmorStandTooltipHandler::onItemTooltip);
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            ArmorStandInteractHandler.clearPresentServerside();
        });
    }
}
