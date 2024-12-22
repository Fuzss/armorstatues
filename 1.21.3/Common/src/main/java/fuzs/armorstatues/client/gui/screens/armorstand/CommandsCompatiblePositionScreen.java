package fuzs.armorstatues.client.gui.screens.armorstand;

import fuzs.armorstatues.init.ModRegistry;
import fuzs.armorstatues.network.client.data.CommandDataSyncHandler;
import fuzs.statuemenus.api.v1.client.gui.screens.ArmorStandPositionScreen;
import fuzs.statuemenus.api.v1.helper.ScaleAttributeHelper;
import fuzs.statuemenus.api.v1.network.client.data.DataSyncHandler;
import fuzs.statuemenus.api.v1.world.inventory.ArmorStandHolder;
import fuzs.statuemenus.api.v1.world.inventory.data.ArmorStandScreenType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;

public class CommandsCompatiblePositionScreen extends ArmorStandPositionScreen {
    protected static final ArmorStandWidgetFactory<CommandsCompatiblePositionScreen> SCALE_WIDGET_FACTORY = (CommandsCompatiblePositionScreen screen, ArmorStand armorStand) -> {
        return screen.new CommandsScaleWidget(Component.translatable(SCALE_TRANSLATION_KEY),
                armorStand::getScale,
                screen.dataSyncHandler::sendScale);
    };

    public CommandsCompatiblePositionScreen(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        super(holder, inventory, component, dataSyncHandler);
    }

    @Override
    protected List<ArmorStandWidget> buildWidgets(ArmorStand armorStand) {
        // only move server-side to prevent rubber banding
        return buildWidgets(this,
                armorStand,
                List.of(SCALE_WIDGET_FACTORY,
                        ROTATION_WIDGET_FACTORY,
                        POSITION_INCREMENT_WIDGET_FACTORY,
                        POSITION_X_WIDGET_FACTORY,
                        POSITION_Y_WIDGET_FACTORY,
                        POSITION_Z_WIDGET_FACTORY));
    }

    @Override
    public ArmorStandScreenType getScreenType() {
        return ModRegistry.POSITION_SCREEN_TYPE;
    }

    protected class CommandsScaleWidget extends ScaleWidget {
        @Nullable
        private Boolean hasModifier;

        public CommandsScaleWidget(Component title, DoubleSupplier currentValue, Consumer<Float> newValue) {
            super(title, currentValue, newValue);
        }

        @Override
        protected void setNewValue(double newValue) {
            if (CommandsCompatiblePositionScreen.this.dataSyncHandler instanceof CommandDataSyncHandler commandDataSyncHandler) {
                commandDataSyncHandler.sendScale(this.hasModifier == null || this.hasModifier,
                        getScaledValue(newValue),
                        true);
            } else {
                super.setNewValue(newValue);
            }
            this.hasModifier = null;
        }

        @Override
        protected void applyClientValue(double newValue) {
            if (this.hasModifier == null) {
                this.hasModifier = CommandsCompatiblePositionScreen.this.getHolder()
                        .getArmorStand()
                        .getAttributes()
                        .hasModifier(Attributes.SCALE, ScaleAttributeHelper.SCALE_BONUS_ID);
            }
            super.applyClientValue(newValue);
        }
    }
}
