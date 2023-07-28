package fuzs.armorstatues.client;

import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.api.client.StatuesApiClient;
import fuzs.armorstatues.client.handler.ArmorStandTooltipHandler;
import fuzs.armorstatues.client.handler.DataSyncTickHandler;
import fuzs.armorstatues.handler.ArmorStandInteractHandler;
import fuzs.puzzleslib.client.core.ClientCoreServices;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = ArmorStatues.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ArmorStatuesForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientCoreServices.FACTORIES.clientModConstructor(ArmorStatues.MOD_ID).accept(new StatuesApiClient());
        ClientCoreServices.FACTORIES.clientModConstructor(ArmorStatues.MOD_ID).accept(new ArmorStatuesClient());
        registerHandlers();
    }

    private static void registerHandlers() {
        MinecraftForge.EVENT_BUS.addListener((final InputEvent.InteractionKeyMappingTriggered evt) -> {

            Minecraft minecraft = Minecraft.getInstance();
            if (evt.isUseItem() && minecraft.hitResult != null && minecraft.hitResult.getType() == HitResult.Type.ENTITY) {

                EntityHitResult hitResult = (EntityHitResult) minecraft.hitResult;
                Entity entity = hitResult.getEntity();
                if (minecraft.level.getWorldBorder().isWithinBounds(entity.blockPosition()) && minecraft.player.canInteractWith(entity, 0)) {

                    Vec3 hitVector = hitResult.getLocation().subtract(entity.getX(), entity.getY(), entity.getZ());
                    Optional<InteractionResult> result = ArmorStandInteractHandler.onUseEntityAt(minecraft.player, minecraft.level, evt.getHand(), entity, hitVector);
                    // if InteractionResult.FAIL is returned the mod is missing server-side, and we open the menu client-side without sending a packet to the server, so the server does not try to interact
                    // only Fabric sending the packet is simple prevented by returning InteractionResult.FAIL from ArmorStandInteractHandler::onUseEntityAt, on Forge this approach seems to work
                    if (result.filter(t -> t == InteractionResult.FAIL).isPresent()) {
                        evt.setSwingHand(false);
                        evt.setCanceled(true);
                    }
                }
            }
        });
        MinecraftForge.EVENT_BUS.addListener((final ItemTooltipEvent evt) -> {
            ArmorStandTooltipHandler.onItemTooltip(evt.getItemStack(), evt.getFlags(), evt.getToolTip());
        });
        MinecraftForge.EVENT_BUS.addListener((final ClientPlayerNetworkEvent.LoggingIn evt) -> {
            ArmorStandInteractHandler.clearPresentServerside();
        });
        MinecraftForge.EVENT_BUS.addListener((final TickEvent.ClientTickEvent evt) -> {
            if (evt.phase != TickEvent.Phase.END) return;
            DataSyncTickHandler.onClientTickEnd(Minecraft.getInstance());
        });
        MinecraftForge.EVENT_BUS.addListener((final ScreenEvent.Closing evt) -> {
            DataSyncTickHandler.onScreenClose(evt.getScreen());
        });
    }
}
