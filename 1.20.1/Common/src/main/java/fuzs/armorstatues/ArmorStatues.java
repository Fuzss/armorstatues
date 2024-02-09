package fuzs.armorstatues;

import fuzs.armorstatues.config.ClientConfig;
import fuzs.armorstatues.handler.ArmorStandInteractHandler;
import fuzs.armorstatues.init.ModRegistry;
import fuzs.armorstatues.network.S2CPingMessage;
import fuzs.puzzleslib.api.config.v3.ConfigHolder;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.event.v1.core.EventPhase;
import fuzs.puzzleslib.api.event.v1.entity.player.PlayerEvents;
import fuzs.puzzleslib.api.event.v1.entity.player.PlayerInteractEvents;
import fuzs.puzzleslib.api.network.v2.MessageDirection;
import fuzs.puzzleslib.api.network.v2.NetworkHandlerV2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArmorStatues implements ModConstructor {
    public static final String MOD_ID = "armorstatues";
    public static final String MOD_NAME = "Armor Statues";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final NetworkHandlerV2 NETWORK = NetworkHandlerV2.build(MOD_ID, true, true);
    public static final ConfigHolder CONFIG = ConfigHolder.builder(MOD_ID).client(ClientConfig.class);

    @Override
    public void onConstructMod() {
        ModRegistry.touch();
        registerMessages();
        registerHandlers();
    }

    private static void registerMessages() {
        NETWORK.register(S2CPingMessage.class, S2CPingMessage::new, MessageDirection.TO_CLIENT);
    }

    private static void registerHandlers() {
        // high priority so we run before the Quark mod
        PlayerInteractEvents.USE_ENTITY_AT.register(EventPhase.BEFORE, ArmorStandInteractHandler::onUseEntityAt);
        PlayerEvents.LOGGED_IN.register(ArmorStandInteractHandler::onLoggedIn);
    }
}
