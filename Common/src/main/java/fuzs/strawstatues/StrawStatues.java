package fuzs.strawstatues;

import fuzs.armorstatues.api.world.inventory.data.ArmorStandStyleOption;
import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.core.ModConstructor;
import fuzs.puzzleslib.network.MessageDirection;
import fuzs.puzzleslib.network.NetworkHandler;
import fuzs.strawstatues.init.ModRegistry;
import fuzs.strawstatues.network.client.C2SStrawStatueModelPartMessage;
import fuzs.strawstatues.world.entity.decoration.StrawStatue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrawStatues implements ModConstructor {
    public static final String MOD_ID = "strawstatues";
    public static final String MOD_NAME = "Straw Statues";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final NetworkHandler NETWORK = CoreServices.FACTORIES.network(MOD_ID);

    @Override
    public void onConstructMod() {
        ModRegistry.touch();
        registerMessages();
    }

    private static void registerMessages() {
        NETWORK.register(C2SStrawStatueModelPartMessage.class, C2SStrawStatueModelPartMessage::new, MessageDirection.TO_SERVER);
    }

    @Override
    public void onCommonSetup() {
        ArmorStandStyleOption.register(new ResourceLocation(MOD_ID, "slimarms"), StrawStatue.SLIM_ARMS_STYLE_OPTION);
        DispenserBlock.registerBehavior(ModRegistry.STRAW_STATUE_ITEM.get(), new DefaultDispenseItemBehavior() {

            @Override
            public ItemStack execute(BlockSource blockSource, ItemStack stack) {
                Direction direction = blockSource.getBlockState().getValue(DispenserBlock.FACING);
                BlockPos blockpos = blockSource.getPos().relative(direction);
                Level level = blockSource.getLevel();
                ArmorStand armorstand = new StrawStatue(level, blockpos.getX() + 0.5, blockpos.getY(), blockpos.getZ() + 0.5);
                EntityType.updateCustomEntityTag(level, (Player) null, armorstand, stack.getTag());
                armorstand.setYRot(direction.toYRot());
                level.addFreshEntity(armorstand);
                stack.shrink(1);
                return stack;
            }
        });
    }

    @Override
    public void onEntityAttributeCreation(EntityAttributesCreateContext context) {
        context.registerEntityAttributes(ModRegistry.STRAW_STATUE_ENTITY_TYPE.get(), LivingEntity.createLivingAttributes());
    }
}
