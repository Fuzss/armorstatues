package fuzs.armorstatues.client.handler;

import fuzs.armorstatues.handler.ArmorStandInteractHandler;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import fuzs.puzzleslib.api.event.v1.core.EventResultHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ClientInteractHandler {

    public static EventResult onUseInteraction(Minecraft minecraft, LocalPlayer player, InteractionHand interactionHand, HitResult hitResult) {

        if (hitResult.getType() == HitResult.Type.ENTITY) {

            Entity entity = ((EntityHitResult) hitResult).getEntity();
            Vec3 hitVector = hitResult.getLocation().subtract(entity.getX(), entity.getY(), entity.getZ());
            EventResultHolder<InteractionResult> result = ArmorStandInteractHandler.onUseEntityAt(minecraft.player,
                    minecraft.level,
                    interactionHand,
                    entity,
                    hitVector
            );

            // if InteractionResult.FAIL is returned the mod is missing server-side, and we open the menu client-side without sending a packet to the server, so the server does not try to interact
            if (result.filter(interactionResult -> interactionResult == InteractionResult.FAIL).isInterrupt()) {

                return EventResult.INTERRUPT;
            }
        }

        return EventResult.PASS;
    }
}
