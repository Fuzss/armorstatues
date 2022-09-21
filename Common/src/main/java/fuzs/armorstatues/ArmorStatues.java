package fuzs.armorstatues;

import fuzs.armorstatues.init.ModRegistry;
import fuzs.armorstatues.network.client.*;
import fuzs.armorstatues.proxy.ClientProxy;
import fuzs.armorstatues.proxy.Proxy;
import fuzs.armorstatues.proxy.ServerProxy;
import fuzs.armorstatues.world.entity.decoration.StrawStatue;
import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.core.DistTypeExecutor;
import fuzs.puzzleslib.core.ModConstructor;
import fuzs.puzzleslib.network.MessageDirection;
import fuzs.puzzleslib.network.NetworkHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
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

    @Override
    public void onCommonSetup() {
        DispenserBlock.registerBehavior(ModRegistry.STRAW_STATUE_ITEM.get(), new DefaultDispenseItemBehavior() {

            @Override
            public ItemStack execute(BlockSource p_123461_, ItemStack p_123462_) {
                Direction direction = p_123461_.getBlockState().getValue(DispenserBlock.FACING);
                BlockPos blockpos = p_123461_.getPos().relative(direction);
                Level level = p_123461_.getLevel();
                ArmorStand armorstand = new StrawStatue(level, (double)blockpos.getX() + 0.5D, (double)blockpos.getY(), (double)blockpos.getZ() + 0.5D);
                EntityType.updateCustomEntityTag(level, (Player)null, armorstand, p_123462_.getTag());
                armorstand.setYRot(direction.toYRot());
                level.addFreshEntity(armorstand);
                p_123462_.shrink(1);
                return p_123462_;
            }
        });
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
        context.registerEntityAttributes(ModRegistry.STRAW_STATUE_ENTITY_TYPE.get(), LivingEntity.createLivingAttributes());
    }
}
