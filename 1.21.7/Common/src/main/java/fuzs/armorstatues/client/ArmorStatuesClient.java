package fuzs.armorstatues.client;

import fuzs.armorstatues.client.gui.screens.armorstand.ArmorStandAlignmentsScreen;
import fuzs.armorstatues.client.gui.screens.armorstand.ArmorStandVanillaTweaksScreen;
import fuzs.armorstatues.client.gui.screens.armorstand.CommandsCompatiblePositionScreen;
import fuzs.armorstatues.client.handler.ClientInteractHandler;
import fuzs.armorstatues.client.handler.DataSyncTickHandler;
import fuzs.armorstatues.init.ModRegistry;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.MenuScreensContext;
import fuzs.puzzleslib.api.client.event.v1.ClientTickEvents;
import fuzs.puzzleslib.api.client.event.v1.entity.player.InteractionInputEvents;
import fuzs.puzzleslib.api.client.event.v1.gui.ScreenEvents;
import fuzs.puzzleslib.api.client.gui.v2.tooltip.ItemTooltipRegistry;
import fuzs.puzzleslib.api.event.v1.core.EventPhase;
import fuzs.statuemenus.api.v1.client.gui.screens.ArmorStandScreenFactory;
import fuzs.statuemenus.api.v1.helper.ArmorStandInteractHelper;
import fuzs.statuemenus.api.v1.world.inventory.ArmorStandMenu;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Items;

public class ArmorStatuesClient implements ClientModConstructor {

    @Override
    public void onConstructMod() {
        registerEventHandlers();
        ItemTooltipRegistry.ITEM.registerItemTooltip(Items.ARMOR_STAND,
                ArmorStandInteractHelper.getArmorStandHoverText());
    }

    private static void registerEventHandlers() {
        ClientTickEvents.END.register(DataSyncTickHandler::onEndClientTick);
        ScreenEvents.remove(Screen.class).register(DataSyncTickHandler::onRemove);
        // event phase must match PlayerInteractEvents#USE_ENTITY_AT as both are implemented using the same event on Fabric
        InteractionInputEvents.USE.register(EventPhase.BEFORE, ClientInteractHandler::onUseInteraction);
    }

    @Override
    public void onClientSetup() {
        ArmorStandScreenFactory.register(ModRegistry.POSITION_SCREEN_TYPE, CommandsCompatiblePositionScreen::new);
        ArmorStandScreenFactory.register(ModRegistry.ALIGNMENTS_SCREEN_TYPE, ArmorStandAlignmentsScreen::new);
        ArmorStandScreenFactory.register(ModRegistry.VANILLA_TWEAKS_SCREEN_TYPE, ArmorStandVanillaTweaksScreen::new);
    }

    @SuppressWarnings("Convert2MethodRef")
    @Override
    public void onRegisterMenuScreens(MenuScreensContext context) {
        // compiler doesn't like method reference :(
        context.registerMenuScreen(ModRegistry.ARMOR_STAND_MENU_TYPE.value(),
                (ArmorStandMenu menu, Inventory inventory, Component component) -> {
                    return ArmorStandScreenFactory.createLastScreenType(menu, inventory, component);
                });
    }
}
