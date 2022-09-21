package fuzs.armorstatues;

import fuzs.armorstatues.api.event.entity.player.PlayerEntityInteractEvent;
import fuzs.armorstatues.data.ModItemModelProvider;
import fuzs.armorstatues.data.ModLanguageProvider;
import fuzs.armorstatues.data.ModLootTableProvider;
import fuzs.armorstatues.data.ModRecipeProvider;
import fuzs.armorstatues.handler.ArmorStandInteractHandler;
import fuzs.puzzleslib.core.CoreServices;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod(ArmorStatues.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ArmorStatuesForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        CoreServices.FACTORIES.modConstructor(ArmorStatues.MOD_ID).accept(new ArmorStatues());
        registerHandlers();
    }

    private static void registerHandlers() {
        MinecraftForge.EVENT_BUS.addListener((final PlayerInteractEvent.EntityInteractSpecific evt) -> {
            // event is broken server-side, we use our own implementation for that below
            if (!evt.getSide().isClient()) return;
            ArmorStandInteractHandler.onEntityInteract(evt.getEntity(), evt.getLevel(), evt.getHand(), evt.getTarget()).ifPresent(result -> {
                evt.setCancellationResult(result);
                evt.setCanceled(true);
            });
        });
        MinecraftForge.EVENT_BUS.addListener((final PlayerEntityInteractEvent evt) -> {
            ArmorStandInteractHandler.onEntityInteract(evt.getEntity(), evt.getLevel(), evt.getHand(), evt.getTarget()).ifPresent(result -> {
                evt.setCancellationResult(result);
                evt.setCanceled(true);
            });
        });
    }

    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent evt) {
        DataGenerator generator = evt.getGenerator();
        final ExistingFileHelper existingFileHelper = evt.getExistingFileHelper();
        generator.addProvider(true, new ModRecipeProvider(generator));
        generator.addProvider(true, new ModLanguageProvider(generator, ArmorStatues.MOD_ID));
        generator.addProvider(true, new ModLootTableProvider(generator, ArmorStatues.MOD_ID));
        generator.addProvider(true, new ModItemModelProvider(generator, ArmorStatues.MOD_ID, existingFileHelper));
    }
}
