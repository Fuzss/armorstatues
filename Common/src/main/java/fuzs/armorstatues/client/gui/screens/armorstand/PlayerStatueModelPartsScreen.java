package fuzs.armorstatues.client.gui.screens.armorstand;

import com.google.common.collect.Lists;
import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.client.gui.components.TickBoxButton;
import fuzs.armorstatues.client.gui.screens.armorstand.data.ArmorStandScreenType;
import fuzs.armorstatues.network.client.C2SPlayerStateModelPartMessage;
import fuzs.armorstatues.network.client.data.DataSyncHandler;
import fuzs.armorstatues.world.entity.decoration.PlayerStatue;
import fuzs.armorstatues.world.inventory.ArmorStandHolder;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.PlayerModelPart;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PlayerStatueModelPartsScreen extends ArmorStandWidgetsScreen {

    public PlayerStatueModelPartsScreen(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        super(holder, inventory, component, dataSyncHandler);
    }

    @Override
    protected List<PositionScreenWidget> buildWidgets(ArmorStand armorStand) {
        List<PositionScreenWidget> widgets = Lists.newArrayList();
        PlayerModelPart[] values = PlayerModelPart.values();
        for (int i = 0; i < values.length;) {
            widgets.add(new PlayerModelPartsWidget(values[i++], i < values.length ? values[i++] : null));
        }
        return widgets;
    }

    @Override
    public ArmorStandScreenType getScreenType() {
        return ArmorStandScreenType.MODEL_PARTS;
    }

    private class PlayerModelPartsWidget extends AbstractPositionScreenWidget {
        private final PlayerModelPart leftModelPart;
        @Nullable
        private final PlayerModelPart rightModelPart;

        public PlayerModelPartsWidget(PlayerModelPart leftModelPart, @Nullable PlayerModelPart rightModelPart) {
            super(CommonComponents.EMPTY);
            this.leftModelPart = leftModelPart;
            this.rightModelPart = rightModelPart;
        }

        @Override
        public void init(int posX, int posY) {
            super.init(posX, posY);
            if (!(PlayerStatueModelPartsScreen.this.holder.getArmorStand() instanceof PlayerStatue playerStatue)) return;
            this.children.add(PlayerStatueModelPartsScreen.this.addRenderableWidget(new TickBoxButton(posX + (this.rightModelPart == null ? 50 : 0), posY + 1, 66, this.leftModelPart.getName(), playerStatue.isModelPartShown(this.leftModelPart), button -> {
                boolean value = ((TickBoxButton) button).isSelected();
                playerStatue.setModelPart(this.leftModelPart, value);
                ArmorStatues.NETWORK.sendToServer(new C2SPlayerStateModelPartMessage(this.leftModelPart, value));
            }, Button.NO_TOOLTIP)));
            if (this.rightModelPart != null) {
                this.children.add(PlayerStatueModelPartsScreen.this.addRenderableWidget(new TickBoxButton(posX + 100, posY + 1, 66, this.rightModelPart.getName(), playerStatue.isModelPartShown(this.rightModelPart), button -> {
                    boolean value = ((TickBoxButton) button).isSelected();
                    playerStatue.setModelPart(this.rightModelPart, value);
                    ArmorStatues.NETWORK.sendToServer(new C2SPlayerStateModelPartMessage(this.rightModelPart, value));
                }, Button.NO_TOOLTIP)));
            }
        }
    }
}
