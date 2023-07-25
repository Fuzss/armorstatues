package fuzs.armorstatues.handler;

import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.api.helper.ArmorStandInteractHelper;
import fuzs.armorstatues.init.ModRegistry;
import fuzs.armorstatues.network.S2CPingMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/**
 * thanks to the CraftingTweaks mod for the server present check events on Forge and Fabric
 */
public class ArmorStandInteractHandler {
    private static boolean presentServerside;

    public static Optional<InteractionResult> onUseEntityAt(Player player, Level level, InteractionHand interactionHand, Entity target, Vec3 hitVector) {
        if (!player.isSpectator() && target.getType() == EntityType.ARMOR_STAND) {
            Optional<InteractionResult> result = ArmorStandInteractHelper.tryOpenArmorStatueMenu(player, level, interactionHand, (ArmorStand) target, ModRegistry.ARMOR_STAND_MENU_TYPE.get());
            if (result.isPresent() && level.isClientSide && !presentServerside) {
                ArmorStatues.PROXY.openArmorStandScreen((ArmorStand) target, player);
                // required so no packet is sent to server when only installed client-side, so the server doesn't change any equipment when we only want to open the screen
                // returning InteractionResult.FAIL will miss out on the player arm swing animation, which we manually play here
                player.swing(interactionHand);
                return Optional.of(InteractionResult.FAIL);
            }
            return result;
        }
        return Optional.empty();
    }

    public static void onPlayerLoggedIn(ServerPlayer player) {
        ArmorStatues.NETWORK.sendTo(new S2CPingMessage(), player);
    }

    public static void setPresentServerside() {
        presentServerside = true;
    }

    public static void clearPresentServerside() {
        presentServerside = false;
    }
}
