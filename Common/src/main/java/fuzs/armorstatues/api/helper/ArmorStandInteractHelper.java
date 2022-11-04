package fuzs.armorstatues.api.helper;

import fuzs.puzzleslib.core.CoreServices;
import fuzs.armorstatues.api.world.inventory.ArmorStandMenu;
import fuzs.armorstatues.mixin.accessor.ArmorStandAccessor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class ArmorStandInteractHelper {

    public static Optional<InteractionResult> tryOpenArmorStatueMenu(Player player, Level level, ArmorStand entity, MenuType<?> menuType) {
        if (player.isShiftKeyDown() && (!entity.isInvulnerable() || player.getAbilities().instabuild)) {
            openArmorStatueMenu(player, entity, menuType);
            return Optional.of(InteractionResult.sidedSuccess(level.isClientSide));
        }
        return Optional.empty();
    }

    public static void openArmorStatueMenu(Player player, ArmorStand entity, MenuType<?> menuType) {
        if (player instanceof ServerPlayer serverPlayer) {
            CoreServices.ABSTRACTIONS.openMenu(serverPlayer, new SimpleMenuProvider((containerId, inventory, player1) -> {
                return ArmorStandMenu.create(menuType, containerId, inventory, entity);
            }, entity.getDisplayName()), (serverPlayer1, friendlyByteBuf) -> {
                friendlyByteBuf.writeInt(entity.getId());
                friendlyByteBuf.writeBoolean(entity.isInvulnerable());
                friendlyByteBuf.writeInt(((ArmorStandAccessor) entity).getDisabledSlots());
            });
        }
    }
}
