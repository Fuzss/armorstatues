package fuzs.armorstatues.mixin;

import fuzs.armorstatues.api.event.entity.player.PlayerEvents;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
abstract class PlayerListMixin {

    @Inject(method = "placeNewPlayer", at = @At("TAIL"))
    public void armorstatues$placeNewPlayer(Connection netManager, ServerPlayer player, CallbackInfo callback) {
        PlayerEvents.LOGIN.invoker().onPlayerLoggedIn(player);
    }

    @Inject(method = "remove", at = @At("HEAD"))
    public void armorstatues$remove(ServerPlayer player, CallbackInfo callback) {
        PlayerEvents.LOGOUT.invoker().onPlayerLoggedOut(player);
    }
}
