package fuzs.armorstatues.client;

import fuzs.armorstatues.ArmorStatues;
import fuzs.puzzleslib.client.core.ClientCoreServices;
import net.fabricmc.api.ClientModInitializer;

public class ArmorStatuesFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientCoreServices.FACTORIES.clientModConstructor(ArmorStatues.MOD_ID).accept(new ArmorStatuesClient());
    }
}
