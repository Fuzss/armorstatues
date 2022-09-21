package fuzs.armorstatues.client.gui.screens.armorstand;

import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.client.gui.components.TickBoxButton;
import fuzs.armorstatues.client.gui.screens.armorstand.data.ArmorStandScreenType;
import fuzs.armorstatues.network.client.C2SPlayerStateModelPartMessage;
import fuzs.armorstatues.network.client.data.DataSyncHandler;
import fuzs.armorstatues.world.entity.decoration.PlayerStatue;
import fuzs.armorstatues.world.inventory.ArmorStandHolder;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.PlayerModelPart;

public class PlayerStatueModelPartsScreen extends ArmorStandTickBoxScreen {

    public PlayerStatueModelPartsScreen(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        super(holder, inventory, component, dataSyncHandler);
    }

    @Override
    protected void init() {
        super.init();
        if (!(PlayerStatueModelPartsScreen.this.holder.getArmorStand() instanceof PlayerStatue playerStatue)) return;
        PlayerModelPart[] values = PlayerModelPart.values();
        final int buttonStartY = (this.imageHeight - values.length * 20 - (values.length - 1) * 2) / 2;
        for (int i = 0, j = 0; i < values.length; i++) {
            PlayerModelPart modelPart = values[i];
            this.addRenderableWidget(new TickBoxButton(this.leftPos + 98, this.topPos + buttonStartY + j++ * 22, 73, modelPart.getName(), playerStatue.isModelPartShown(modelPart), (Button button) -> {
                boolean value = ((TickBoxButton) button).isSelected();
                playerStatue.setModelPart(modelPart, value);
                ArmorStatues.NETWORK.sendToServer(new C2SPlayerStateModelPartMessage(modelPart, value));
            }, Button.NO_TOOLTIP));
        }
    }

    @Override
    public ArmorStandScreenType getScreenType() {
        return ArmorStandScreenType.MODEL_PARTS;
    }
}
