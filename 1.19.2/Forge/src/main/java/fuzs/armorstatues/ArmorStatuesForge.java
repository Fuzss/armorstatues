package fuzs.armorstatues;

import fuzs.armorstatues.api.StatuesApi;
import fuzs.armorstatues.data.ModLanguageProvider;
import fuzs.armorstatues.handler.ArmorStandInteractHandler;
import fuzs.puzzleslib.core.CoreServices;
import net.minecraft.data.DataGenerator;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod(ArmorStatues.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ArmorStatuesForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        CoreServices.FACTORIES.modConstructor(ArmorStatues.MOD_ID).accept(new StatuesApi());
        CoreServices.FACTORIES.modConstructor(ArmorStatues.MOD_ID).accept(new ArmorStatues());
        registerHandlers();
    }

    private static void registerHandlers() {
        // high priority so we run before the Quark mod
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, (final PlayerInteractEvent.EntityInteractSpecific evt) -> {
            ArmorStandInteractHandler.onUseEntityAt(evt.getEntity(), evt.getLevel(), evt.getHand(), evt.getTarget(), evt.getLocalPos()).ifPresent(result -> {
                evt.setCancellationResult(result);
                evt.setCanceled(true);
            });
        });
        MinecraftForge.EVENT_BUS.addListener((final PlayerEvent.PlayerLoggedInEvent evt) -> {
            ArmorStandInteractHandler.onPlayerLoggedIn((ServerPlayer) evt.getEntity());
        });
    }

    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent evt) {
        DataGenerator generator = evt.getGenerator();
        final ExistingFileHelper existingFileHelper = evt.getExistingFileHelper();
        generator.addProvider(true, new ModLanguageProvider(generator, StatuesApi.MOD_ID));
    }
}
