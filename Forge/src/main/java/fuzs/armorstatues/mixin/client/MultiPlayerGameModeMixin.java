package fuzs.armorstatues.mixin.client;

import fuzs.armorstatues.handler.ArmorStandInteractHandler;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(MultiPlayerGameMode.class)
abstract class MultiPlayerGameModeMixin {
    @Shadow
    @Final
    private ClientPacketListener connection;

    @Inject(method = "interactAt", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;ensureHasSentCarriedItem()V", shift = At.Shift.AFTER), cancellable = true)
    public void interactAt(Player player, Entity target, EntityHitResult ray, InteractionHand hand, CallbackInfoReturnable<InteractionResult> callback) {
        Vec3 vec3 = ray.getLocation().subtract(target.getX(), target.getY(), target.getZ());
        Optional<InteractionResult> result = ArmorStandInteractHandler.onEntityInteract(player, player.level, hand, target, vec3);
        if (result.isPresent()) {
            if (result.get() == InteractionResult.SUCCESS) {
                this.connection.send(ServerboundInteractPacket.createInteractionPacket(target, player.isShiftKeyDown(), hand, vec3));
            }
            callback.setReturnValue(result.get());
        }
    }
}
