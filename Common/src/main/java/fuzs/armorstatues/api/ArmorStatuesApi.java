package fuzs.armorstatues.api;

import fuzs.armorstatues.api.network.client.*;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandStyleOption;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandStyleOptions;
import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.core.ModConstructor;
import fuzs.puzzleslib.network.MessageDirection;
import fuzs.puzzleslib.network.NetworkHandler;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class ArmorStatuesApi implements ModConstructor {
    public static final String MOD_ID = "armorstatues";
    public static final String MOD_NAME = "Armor Statues";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final NetworkHandler NETWORK = CoreServices.FACTORIES.network(MOD_ID, true, false);

    @Override
    public void onConstructMod() {
        registerMessages();
    }

    private static void registerMessages() {
        NETWORK.register(C2SArmorStandNameMessage.class, C2SArmorStandNameMessage::new, MessageDirection.TO_SERVER);
        NETWORK.register(C2SArmorStandStyleMessage.class, C2SArmorStandStyleMessage::new, MessageDirection.TO_SERVER);
        NETWORK.register(C2SArmorStandPositionMessage.class, C2SArmorStandPositionMessage::new, MessageDirection.TO_SERVER);
        NETWORK.register(C2SArmorStandPoseMessage.class, C2SArmorStandPoseMessage::new, MessageDirection.TO_SERVER);
        NETWORK.register(C2SArmorStandRotationMessage.class, C2SArmorStandRotationMessage::new, MessageDirection.TO_SERVER);
    }

    @Override
    public void onCommonSetup() {
        // do this here instead of in enum constructor to avoid potential issues with the enum class not having been loaded yet on server-side, therefore nothing being registered
        for (ArmorStandStyleOptions styleOption : ArmorStandStyleOptions.values()) {
            ArmorStandStyleOption.register(new ResourceLocation(MOD_ID, styleOption.getTranslationId().toLowerCase(Locale.ROOT)), styleOption);
        }
    }
}
