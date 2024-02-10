package fuzs.armorstatues.client;

import fuzs.armorstatues.client.gui.screens.armorstand.ArmorStandAlignmentsScreen;
import fuzs.armorstatues.client.gui.screens.armorstand.ArmorStandVanillaTweaksScreen;
import fuzs.armorstatues.client.handler.ArmorStandTooltipHandler;
import fuzs.armorstatues.client.handler.DataSyncTickHandler;
import fuzs.armorstatues.init.ModRegistry;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.MenuScreensContext;
import fuzs.puzzleslib.api.client.event.v1.ClientTickEvents;
import fuzs.puzzleslib.api.client.event.v1.gui.ItemTooltipCallback;
import fuzs.puzzleslib.api.client.event.v1.gui.ScreenEvents;
import fuzs.statuemenus.api.v1.client.gui.screens.ArmorStandScreenFactory;
import fuzs.statuemenus.api.v1.world.inventory.ArmorStandMenu;
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
    }

    @Override
    public void onClientSetup() {
        ArmorStandScreenFactory.register(ModRegistry.ALIGNMENTS_SCREEN_TYPE, ArmorStandAlignmentsScreen::new);
        ArmorStandScreenFactory.register(ModRegistry.VANILLA_TWEAKS_SCREEN_TYPE, ArmorStandVanillaTweaksScreen::new);
    }

    @SuppressWarnings("Convert2MethodRef")
    @Override
    public void onRegisterMenuScreens(MenuScreensContext context) {
        // compiler doesn't like method reference :(
        context.registerMenuScreen(ModRegistry.ARMOR_STAND_MENU_TYPE.value(), (ArmorStandMenu menu, Inventory inventory, Component component) -> {
            return ArmorStandScreenFactory.createLastScreenType(menu, inventory, component);
        });
    }
}
