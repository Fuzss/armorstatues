package fuzs.armorstatues.handler;

import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import fuzs.puzzleslib.core.CoreServices;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class ArmorStandInteractHandler {

    public static Optional<InteractionResult> onEntityInteract(Player player, Level level, InteractionHand interactionHand, Entity entity) {
        if (entity.getType() == EntityType.ARMOR_STAND) {
            Optional<InteractionResult> result = tryOpenArmorStatueMenu(player, level, (ArmorStand) entity);
             if (result.isPresent() && level.isClientSide) {
                // better to check for client once more, we don't want to accidentally run on the server thread when showing the screen
                ArmorStatues.PROXY.openArmorStandScreen((ArmorStand) entity, player);
            }
            return result;
        }
        return Optional.empty();
    }

    public static Optional<InteractionResult> tryOpenArmorStatueMenu(Player player, Level level, ArmorStand entity) {
        if (player.isShiftKeyDown() && (!entity.isInvulnerable() || player.getAbilities().instabuild)) {
            openArmorStatueMenu(player, entity);
            return Optional.of(InteractionResult.sidedSuccess(level.isClientSide));
        }
        return Optional.empty();
    }

    public static void openArmorStatueMenu(Player player, ArmorStand entity) {
        if (player instanceof ServerPlayer serverPlayer) {
            CoreServices.ABSTRACTIONS.openMenu(serverPlayer, new SimpleMenuProvider((containerId, inventory, player1) -> {
                return ArmorStandMenu.create(containerId, inventory, entity);
            }, entity.getDisplayName()), (serverPlayer1, friendlyByteBuf) -> {
                friendlyByteBuf.writeInt(entity.getId());
                friendlyByteBuf.writeBoolean(entity.isInvulnerable());
            });
        }
    }
}
