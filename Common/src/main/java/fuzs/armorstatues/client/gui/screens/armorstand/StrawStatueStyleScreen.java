package fuzs.armorstatues.client.gui.screens.armorstand;

import fuzs.armorstatues.client.gui.screens.armorstand.data.ArmorStandStyleOption;
import fuzs.armorstatues.network.client.data.DataSyncHandler;
import fuzs.armorstatues.world.inventory.ArmorStandHolder;
import fuzs.armorstatues.world.inventory.ArmorStandScreenType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.util.stream.Stream;

public class StrawStatueStyleScreen extends ArmorStandStyleScreen {

    public StrawStatueStyleScreen(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        super(holder, inventory, component, dataSyncHandler);
    }

    @Override
    protected ArmorStandStyleOption[] getAllTickBoxValues() {
        return Stream.of(ArmorStandStyleOption.SHOW_NAME, ArmorStandStyleOption.SMALL, ArmorStandStyleOption.NO_GRAVITY, ArmorStandStyleOption.SEALED)
                .filter(option -> this.minecraft.player.getAbilities().instabuild || !option.onlyCreative())
                .toArray(ArmorStandStyleOption[]::new);
    }

    @Override
    public ArmorStandScreenType getScreenType() {
        return ArmorStandScreenType.STRAW_STATUE_STYLE;
    }
}
