package fuzs.armorstatues.neoforge.client;

import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.client.ArmorStatuesClient;
import fuzs.armorstatues.data.client.ModLanguageProvider;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = ArmorStatues.MOD_ID, dist = Dist.CLIENT)
public class ArmorStatuesNeoForgeClient {

    public ArmorStatuesNeoForgeClient() {
        ClientModConstructor.construct(ArmorStatues.MOD_ID, ArmorStatuesClient::new);
        DataProviderHelper.registerDataProviders(ArmorStatues.MOD_ID, ModLanguageProvider::new);
    }
}
