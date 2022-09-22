package fuzs.armorstatues.api.client.gui.screens.armorstand;

import com.google.common.collect.Maps;
import fuzs.armorstatues.api.network.client.data.DataSyncHandler;
import fuzs.armorstatues.world.inventory.ArmorStandHolder;
import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import fuzs.armorstatues.world.inventory.ArmorStandScreenType;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.util.Map;

@FunctionalInterface
public interface ArmorStandScreenFactory<T extends Screen & MenuAccess<ArmorStandMenu> & ArmorStandScreen> {
    Map<ArmorStandScreenType, ArmorStandScreenFactory<?>> FACTORIES = Maps.newHashMap();

    T create(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler);

    static <T extends Screen & MenuAccess<ArmorStandMenu> & ArmorStandScreen> T createScreenType(ArmorStandScreenType screenType, ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        dataSyncHandler.setLastType(screenType);
        ArmorStandScreenFactory<?> factory = FACTORIES.get(screenType);
        if (factory == null) throw new IllegalStateException("No screen factory registered for armor stand screen type %s".formatted(screenType));
        T screen = (T) factory.create(holder, inventory, component, dataSyncHandler);
        if (screen.getScreenType() != screenType) throw new IllegalStateException("Armor stand screen type mismatch: %s and %s".formatted(screen.getScreenType(), screenType));
        return screen;
    }

    static <T extends Screen & MenuAccess<ArmorStandMenu> & ArmorStandScreen> T createLastScreenType(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        ArmorStandScreenType screenType = dataSyncHandler.getLastType().orElse(dataSyncHandler.getDataProvider().getDefaultScreenType());
        return createScreenType(screenType, holder, inventory, component, dataSyncHandler);
    }

    static void register(ArmorStandScreenType screenType, ArmorStandScreenFactory<?> factory) {
        if (FACTORIES.put(screenType, factory) != null) throw new IllegalStateException("Attempted to register duplicate screen factory for armor stand screen type %s".formatted(screenType));
    }
}
