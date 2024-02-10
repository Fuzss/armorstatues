package fuzs.armorstatues.neoforge;

import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.data.client.ModLanguageProvider;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;

@Mod(ArmorStatues.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ArmorStatuesNeoForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ModConstructor.construct(ArmorStatues.MOD_ID, ArmorStatues::new);
        DataProviderHelper.registerDataProviders(ArmorStatues.MOD_ID, ModLanguageProvider::new);
    }
}
