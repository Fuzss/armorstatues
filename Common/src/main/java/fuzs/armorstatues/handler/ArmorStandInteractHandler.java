package fuzs.armorstatues.handler;

import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.api.helper.ArmorStandInteractHelper;
import fuzs.armorstatues.init.ModRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class ArmorStandInteractHandler {

    public static Optional<InteractionResult> onEntityInteract(Player player, Level level, InteractionHand interactionHand, Entity entity) {
        if (entity.getType() == EntityType.ARMOR_STAND) {
            Optional<InteractionResult> result = ArmorStandInteractHelper.tryOpenArmorStatueMenu(player, level, (ArmorStand) entity, ModRegistry.ARMOR_STAND_MENU_TYPE.get());
             if (result.isPresent() && level.isClientSide) {
                // better to check for client once more, we don't want to accidentally run on the server thread when showing the screen
                ArmorStatues.PROXY.openArmorStandScreen((ArmorStand) entity, player);
            }
            return result;
        }
        return Optional.empty();
    }
}
