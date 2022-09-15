package fuzs.armorstatues.mixin;

import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.api.event.entity.player.PlayerEntityInteractEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/server/network/ServerGamePacketListenerImpl$1")
abstract class ServerGamePacketListenerImplMixin {

    @Inject(method = "lambda$onInteraction$0", at = @At("HEAD"), cancellable = true)
    private static void onInteraction$inject$head(Vec3 hitVector, ServerPlayer player, Entity entity, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> callback) {
        // add entity pos just like Fabric Api, although hitVector itself would be enough...
        Vec3 vec3 = hitVector.add(entity.getX(), entity.getY(), entity.getZ());
        PlayerEntityInteractEvent evt = new PlayerEntityInteractEvent(player, interactionHand, entity, vec3);
        MinecraftForge.EVENT_BUS.post(evt);
        if (evt.isCanceled()) callback.setReturnValue(evt.getCancellationResult());
    }
}
