package fuzs.armorstatues;

import fuzs.armorstatues.init.ModRegistry;
import fuzs.armorstatues.network.client.*;
import fuzs.armorstatues.proxy.ClientProxy;
import fuzs.armorstatues.proxy.Proxy;
import fuzs.armorstatues.proxy.ServerProxy;
import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.core.DistTypeExecutor;
import fuzs.puzzleslib.core.ModConstructor;
import fuzs.puzzleslib.network.MessageDirection;
import fuzs.puzzleslib.network.NetworkHandler;
import net.minecraft.world.entity.LivingEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArmorStatues implements ModConstructor {
    public static final String MOD_ID = "armorstatues";
    public static final String MOD_NAME = "Armor Statues";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final NetworkHandler NETWORK = CoreServices.FACTORIES.network(MOD_ID, true, false);
    @SuppressWarnings("Convert2MethodRef")
    public static final Proxy PROXY = DistTypeExecutor.getForDistType(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    @Override
    public void onConstructMod() {
        ModRegistry.touch();
        registerMessages();
    }

    private static void registerMessages() {
        NETWORK.register(C2SArmorStandNameMessage.class, C2SArmorStandNameMessage::new, MessageDirection.TO_SERVER);
        NETWORK.register(C2SArmorStandStyleMessage.class, C2SArmorStandStyleMessage::new, MessageDirection.TO_SERVER);
        NETWORK.register(C2SArmorStandPositionMessage.class, C2SArmorStandPositionMessage::new, MessageDirection.TO_SERVER);
        NETWORK.register(C2SArmorStandPoseMessage.class, C2SArmorStandPoseMessage::new, MessageDirection.TO_SERVER);
        NETWORK.register(C2SArmorStandRotationMessage.class, C2SArmorStandRotationMessage::new, MessageDirection.TO_SERVER);
        NETWORK.register(C2SPlayerStateModelPartMessage.class, C2SPlayerStateModelPartMessage::new, MessageDirection.TO_SERVER);
    }

    @Override
    public void onEntityAttributeCreation(EntityAttributesCreateContext context) {
        context.registerEntityAttributes(ModRegistry.PLAYER_STATUE_ENTITY_TYPE.get(), LivingEntity.createLivingAttributes());
    }
}
