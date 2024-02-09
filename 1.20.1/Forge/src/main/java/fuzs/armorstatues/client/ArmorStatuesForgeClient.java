package fuzs.armorstatues.client;

import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.handler.ArmorStandInteractHandler;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.event.v1.core.EventResultHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = ArmorStatues.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ArmorStatuesForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientModConstructor.construct(ArmorStatues.MOD_ID, ArmorStatuesClient::new);
        registerHandlers();
    }

    private static void registerHandlers() {
        MinecraftForge.EVENT_BUS.addListener((final InputEvent.InteractionKeyMappingTriggered evt) -> {

            Minecraft minecraft = Minecraft.getInstance();
            if (evt.isUseItem() && minecraft.hitResult != null && minecraft.hitResult.getType() == HitResult.Type.ENTITY) {

                EntityHitResult hitResult = (EntityHitResult) minecraft.hitResult;
                Entity entity = hitResult.getEntity();
                if (minecraft.level.getWorldBorder().isWithinBounds(entity.blockPosition())) {

                    Vec3 hitVector = hitResult.getLocation().subtract(entity.getX(), entity.getY(), entity.getZ());
                    EventResultHolder<InteractionResult> result = ArmorStandInteractHandler.onUseEntityAt(minecraft.player, minecraft.level, evt.getHand(), entity, hitVector);
                    // if InteractionResult.FAIL is returned the mod is missing server-side, and we open the menu client-side without sending a packet to the server, so the server does not try to interact
                    // only Fabric sending the packet is simple prevented by returning InteractionResult.FAIL from ArmorStandInteractHandler::onUseEntityAt, on Forge this approach seems to work
                    if (result.filter(t -> t == InteractionResult.FAIL).isInterrupt()) {
                        evt.setSwingHand(false);
                        evt.setCanceled(true);
                    }
                }
            }
        });
    }
}
