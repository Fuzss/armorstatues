package fuzs.strawstatues.client.gui.screens.strawstatue;

import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.api.client.gui.components.TickBoxButton;
import fuzs.armorstatues.api.client.gui.screens.armorstand.ArmorStandTickBoxScreen;
import fuzs.strawstatues.network.client.C2SStrawStatueModelPartMessage;
import fuzs.armorstatues.api.network.client.data.DataSyncHandler;
import fuzs.strawstatues.world.entity.decoration.StrawStatue;
import fuzs.armorstatues.world.inventory.ArmorStandHolder;
import fuzs.armorstatues.world.inventory.ArmorStandScreenType;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.PlayerModelPart;

public class StrawStatueModelPartsScreen extends ArmorStandTickBoxScreen<PlayerModelPart> {

    public StrawStatueModelPartsScreen(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        super(holder, inventory, component, dataSyncHandler);
    }

    @Override
    protected PlayerModelPart[] getAllTickBoxValues() {
        return PlayerModelPart.values();
    }

    @Override
    protected AbstractWidget makeTickBoxWidget(ArmorStand armorStand, int buttonStartY, int index, PlayerModelPart option) {
        StrawStatue strawStatue = (StrawStatue) armorStand;
        return new TickBoxButton(this.leftPos + 96, this.topPos + buttonStartY + index * 22, 6, 76, option.getName(), strawStatue.isModelPartShown(option), (Button button) -> {
            boolean value = ((TickBoxButton) button).isSelected();
            strawStatue.setModelPart(option, value);
            ArmorStatues.NETWORK.sendToServer(new C2SStrawStatueModelPartMessage(option, value));
        }, Button.NO_TOOLTIP);
    }

    @Override
    public ArmorStandScreenType getScreenType() {
        return StrawStatue.MODEL_PARTS;
    }
}
