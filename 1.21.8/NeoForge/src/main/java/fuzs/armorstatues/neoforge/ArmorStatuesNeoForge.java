package fuzs.armorstatues.neoforge;

import fuzs.armorstatues.ArmorStatues;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.neoforged.fml.common.Mod;

@Mod(ArmorStatues.MOD_ID)
public class ArmorStatuesNeoForge {

    public ArmorStatuesNeoForge() {
        ModConstructor.construct(ArmorStatues.MOD_ID, ArmorStatues::new);
    }
}
