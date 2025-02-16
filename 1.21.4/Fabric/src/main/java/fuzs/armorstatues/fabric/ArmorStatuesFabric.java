package fuzs.armorstatues.fabric;

import fuzs.armorstatues.ArmorStatues;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.fabricmc.api.ModInitializer;

public class ArmorStatuesFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(ArmorStatues.MOD_ID, ArmorStatues::new);
    }
}
