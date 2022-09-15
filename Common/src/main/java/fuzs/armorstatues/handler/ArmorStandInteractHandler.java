package fuzs.armorstatues.handler;

import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import fuzs.puzzleslib.core.CoreServices;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class ArmorStandInteractHandler {

    public static Optional<InteractionResult> onEntityInteract(Player player, Level level, InteractionHand interactionHand, Entity entity) {
        if (entity instanceof ArmorStand && player.isShiftKeyDown()) {
            if (player instanceof ServerPlayer serverPlayer) {
                CoreServices.ABSTRACTIONS.openMenu(serverPlayer, new SimpleMenuProvider((pContainerId, pPlayerInventory, pPlayer) -> {
                    return ArmorStandMenu.create(pContainerId, pPlayerInventory, (ArmorStand) entity);
                }, entity.getDisplayName()), (serverPlayer1, friendlyByteBuf) -> {
                    friendlyByteBuf.writeInt(entity.getId());
                });
            }
            return Optional.of(InteractionResult.sidedSuccess(level.isClientSide));
        }
        return Optional.empty();
    }
}
