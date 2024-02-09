package fuzs.armorstatues.client;

import fuzs.armorstatues.client.gui.screens.armorstand.ArmorStandAlignmentsScreen;
import fuzs.armorstatues.client.gui.screens.armorstand.ArmorStandVanillaTweaksScreen;
import fuzs.armorstatues.client.handler.ArmorStandTooltipHandler;
import fuzs.armorstatues.client.handler.DataSyncTickHandler;
import fuzs.armorstatues.handler.ArmorStandInteractHandler;
import fuzs.armorstatues.init.ModRegistry;
import fuzs.puzzlesapi.api.client.statues.v1.gui.screens.armorstand.ArmorStandScreenFactory;
import fuzs.puzzlesapi.api.statues.v1.world.inventory.ArmorStandMenu;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.event.v1.ClientPlayerEvents;
import fuzs.puzzleslib.api.client.event.v1.ClientTickEvents;
import fuzs.puzzleslib.api.client.event.v1.ItemTooltipCallback;
import fuzs.puzzleslib.api.client.event.v1.ScreenEvents;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ArmorStatuesClient implements ClientModConstructor {

    @Override
    public void onConstructMod() {
        registerHandlers();
    }

    private static void registerHandlers() {
        ItemTooltipCallback.EVENT.register(ArmorStandTooltipHandler::onItemTooltip);
        ClientTickEvents.END.register(DataSyncTickHandler::onEndClientTick);
        ScreenEvents.remove(Screen.class).register(DataSyncTickHandler::onRemove);
        ClientPlayerEvents.LOGGED_IN.register(ArmorStandInteractHandler::onLoggedIn);
    }

    @SuppressWarnings("Convert2MethodRef")
    @Override
    public void onClientSetup() {
        // compiler doesn't like method reference :(
        MenuScreens.register(ModRegistry.ARMOR_STAND_MENU_TYPE.get(), (ArmorStandMenu menu, Inventory inventory, Component component) -> {
            return ArmorStandScreenFactory.createLastScreenType(menu, inventory, component);
        });
        ArmorStandScreenFactory.register(ModRegistry.ALIGNMENTS_SCREEN_TYPE, ArmorStandAlignmentsScreen::new);
        ArmorStandScreenFactory.register(ModRegistry.VANILLA_TWEAKS_SCREEN_TYPE, ArmorStandVanillaTweaksScreen::new);
    }
}
