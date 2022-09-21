package fuzs.armorstatues.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class HammerItem extends Item {

    public HammerItem(Properties properties) {
        super(properties);
    }

    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        Level level = pContext.getLevel();
        if (!level.isClientSide && player != null) {
            BlockPos blockpos = pContext.getClickedPos();
            BlockState clickedState = level.getBlockState(blockpos);
            if (clickedState.isCollisionShapeFullBlock(level, blockpos)) {
                Block clickedBlock = clickedState.getBlock();
                BlockState stateBelow = level.getBlockState(blockpos.below());
                if (stateBelow.is(clickedState.getBlock())) {
                    level.se
                }
            }
            if (!this.handleInteraction(player, level.getBlockState(blockpos), level, blockpos, true, pContext.getItemInHand())) {
                return InteractionResult.FAIL;
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
