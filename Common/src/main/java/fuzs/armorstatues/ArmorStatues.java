package fuzs.armorstatues;

import fuzs.armorstatues.init.ModRegistry;
import fuzs.armorstatues.proxy.ClientProxy;
import fuzs.armorstatues.proxy.Proxy;
import fuzs.armorstatues.proxy.ServerProxy;
import fuzs.puzzleslib.core.DistTypeExecutor;
import fuzs.puzzleslib.core.ModConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArmorStatues implements ModConstructor {
    public static final String MOD_ID = "armorstatues";
    public static final String MOD_NAME = "Armor Statues";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @SuppressWarnings("Convert2MethodRef")
    public static final Proxy PROXY = DistTypeExecutor.getForDistType(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    @Override
    public void onConstructMod() {
        ModRegistry.touch();
    }
}
