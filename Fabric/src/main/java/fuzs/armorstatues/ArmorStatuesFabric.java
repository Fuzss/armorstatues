package fuzs.armorstatues;

import fuzs.armorstatues.api.ArmorStatuesApi;
import fuzs.armorstatues.handler.ArmorStandInteractHandler;
import fuzs.puzzleslib.core.CoreServices;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ArmorStatuesFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CoreServices.FACTORIES.modConstructor(ArmorStatues.MOD_ID).accept(new ArmorStatuesApi());
        CoreServices.FACTORIES.modConstructor(ArmorStatues.MOD_ID).accept(new ArmorStatues());
        registerHandlers();
    }

    private static void registerHandlers() {
        UseEntityCallback.EVENT.register((Player player, Level level, InteractionHand interactionHand, Entity target, EntityHitResult entityHitResult) -> {
            // entityHitResult is marked as nullable in Fabric Api, but can actually not be null
            Vec3 vec3 = entityHitResult.getLocation().subtract(target.getX(), target.getY(), target.getZ());
            return ArmorStandInteractHandler.onEntityInteract(player, level, interactionHand, target, vec3).orElse(InteractionResult.PASS);
        });
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ArmorStandInteractHandler.onPlayerLoggedIn(handler.getPlayer());
        });
    }
}
