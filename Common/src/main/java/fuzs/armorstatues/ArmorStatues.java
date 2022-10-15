package fuzs.armorstatues;

import fuzs.armorstatues.init.ModRegistry;
import fuzs.armorstatues.network.S2CPingMessage;
import fuzs.armorstatues.proxy.ClientProxy;
import fuzs.armorstatues.proxy.Proxy;
import fuzs.armorstatues.proxy.ServerProxy;
import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.core.DistTypeExecutor;
import fuzs.puzzleslib.core.ModConstructor;
import fuzs.puzzleslib.network.MessageDirection;
import fuzs.puzzleslib.network.NetworkHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArmorStatues implements ModConstructor {
    public static final String MOD_ID = "armorstatues";
    public static final String MOD_NAME = "Armor Statues";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final NetworkHandler NETWORK = CoreServices.FACTORIES.network(MOD_ID, true, true);
    @SuppressWarnings("Convert2MethodRef")
    public static final Proxy PROXY = DistTypeExecutor.getForDistType(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    @Override
    public void onConstructMod() {
        ModRegistry.touch();
        registerMessages();
    }

    private static void registerMessages() {
        NETWORK.register(S2CPingMessage.class, S2CPingMessage::new, MessageDirection.TO_CLIENT);
    }
}
