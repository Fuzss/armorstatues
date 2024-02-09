package fuzs.armorstatues.client;

import fuzs.armorstatues.ArmorStatues;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;

public class ArmorStatuesFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(ArmorStatues.MOD_ID, ArmorStatuesClient::new);
    }
}
