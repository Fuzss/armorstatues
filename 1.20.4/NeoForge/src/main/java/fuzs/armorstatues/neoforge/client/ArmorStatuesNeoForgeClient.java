package fuzs.armorstatues.neoforge.client;

import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.client.ArmorStatuesClient;
import fuzs.armorstatues.data.client.ModLanguageProvider;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = ArmorStatues.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ArmorStatuesNeoForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientModConstructor.construct(ArmorStatues.MOD_ID, ArmorStatuesClient::new);
        DataProviderHelper.registerDataProviders(ArmorStatues.MOD_ID, ModLanguageProvider::new);
    }
}
