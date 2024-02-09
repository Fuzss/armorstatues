package fuzs.armorstatues.handler;

import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.init.ModRegistry;
import fuzs.armorstatues.network.S2CPingMessage;
import fuzs.armorstatues.proxy.Proxy;
import fuzs.puzzlesapi.api.statues.v1.helper.ArmorStandInteractHelper;
import fuzs.puzzleslib.api.event.v1.core.EventResultHolder;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ArmorStandInteractHandler {
    private static boolean presentServerside;

    public static EventResultHolder<InteractionResult> onUseEntityAt(Player player, Level level, InteractionHand interactionHand, Entity target, Vec3 hitVector) {
        if (player.getAbilities().mayBuild && target.getType() == EntityType.ARMOR_STAND) {
            boolean clientsideOnly = level.isClientSide && !presentServerside;
            // the menu won't exist in the registry if the mod is missing serverside since Forge syncs registries to clients
            MenuType<?> menuType = clientsideOnly ? null : ModRegistry.ARMOR_STAND_MENU_TYPE.get();
            EventResultHolder<InteractionResult> result = ArmorStandInteractHelper.tryOpenArmorStatueMenu(player, level, interactionHand, (ArmorStand) target, menuType, ModRegistry.ARMOR_STAND_DATA_PROVIDER);
            if (result.isInterrupt() && clientsideOnly) {
                Proxy.INSTANCE.openArmorStandScreen((ArmorStand) target, player);
                // required so no packet is sent to server when only installed client-side, so the server doesn't change any equipment when we only want to open the screen
                // returning InteractionResult.FAIL will miss out on the player arm swing animation, which we manually play here
                player.swing(interactionHand);
                return EventResultHolder.interrupt(InteractionResult.FAIL);
            }
            return result;
        }
        return EventResultHolder.pass();
    }

    public static void onLoggedIn(ServerPlayer player) {
        ArmorStatues.NETWORK.sendTo(new S2CPingMessage(), player);
    }

    public static void onLoggedIn(LocalPlayer player, MultiPlayerGameMode multiPlayerGameMode, Connection connection) {
        presentServerside = false;
    }

    public static void setPresentServerside() {
        presentServerside = true;
    }
}
