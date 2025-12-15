package fuzs.armorstatues.client;

import fuzs.armorstatues.client.gui.screens.armorstand.ArmorStandAlignmentsScreen;
import fuzs.armorstatues.client.gui.screens.armorstand.ArmorStandVanillaTweaksScreen;
import fuzs.armorstatues.client.gui.screens.armorstand.ArmorStandPositionScreen;
import fuzs.armorstatues.client.handler.ClientInteractHandler;
import fuzs.armorstatues.client.handler.DataSyncTickHandler;
import fuzs.armorstatues.init.ModRegistry;
import fuzs.armorstatues.world.inventory.data.ArmorStandScreenTypes;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.MenuScreensContext;
import fuzs.puzzleslib.api.client.event.v1.ClientTickEvents;
import fuzs.puzzleslib.api.client.event.v1.entity.player.InteractionInputEvents;
import fuzs.puzzleslib.api.client.event.v1.gui.ScreenEvents;
import fuzs.puzzleslib.api.client.gui.v2.tooltip.ItemTooltipRegistry;
import fuzs.puzzleslib.api.event.v1.core.EventPhase;
import fuzs.statuemenus.api.v1.client.gui.screens.StatueScreenFactory;
import fuzs.statuemenus.api.v1.helper.ArmorStandInteractHelper;
import fuzs.statuemenus.api.v1.world.inventory.StatueMenu;
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
        StatueScreenFactory.register(ArmorStandScreenTypes.POSITION, ArmorStandPositionScreen::new);
        StatueScreenFactory.register(ArmorStandScreenTypes.ALIGNMENTS, ArmorStandAlignmentsScreen::new);
        StatueScreenFactory.register(ArmorStandScreenTypes.VANILLA_TWEAKS, ArmorStandVanillaTweaksScreen::new);
    }

    @SuppressWarnings("Convert2MethodRef")
    @Override
    public void onRegisterMenuScreens(MenuScreensContext context) {
        context.registerMenuScreen(ModRegistry.ARMOR_STAND_MENU_TYPE.value(),
                (StatueMenu menu, Inventory inventory, Component component) -> {
                    return StatueScreenFactory.createLastScreenType(menu, inventory, component);
                });
    }
}
