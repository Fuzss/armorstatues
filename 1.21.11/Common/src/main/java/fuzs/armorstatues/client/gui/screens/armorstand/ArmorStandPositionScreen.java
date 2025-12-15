package fuzs.armorstatues.client.gui.screens.armorstand;

import fuzs.armorstatues.network.client.data.CommandDataSyncHandler;
import fuzs.armorstatues.world.inventory.data.ArmorStandScreenTypes;
import fuzs.statuemenus.api.v1.client.gui.screens.StatuePositionScreen;
import fuzs.statuemenus.api.v1.helper.ScaleAttributeHelper;
import fuzs.statuemenus.api.v1.network.client.data.DataSyncHandler;
import fuzs.statuemenus.api.v1.world.inventory.StatueHolder;
import fuzs.statuemenus.api.v1.world.inventory.data.StatueScreenType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;

public class ArmorStandPositionScreen extends StatuePositionScreen {
    protected static final ArmorStandWidgetFactory<ArmorStandPositionScreen> SCALE_WIDGET_FACTORY = (ArmorStandPositionScreen screen, LivingEntity livingEntity) -> {
        return screen.new CustomScaleWidget(Component.translatable(SCALE_TRANSLATION_KEY),
                livingEntity::getScale,
                screen.dataSyncHandler::sendScale);
    };

    public ArmorStandPositionScreen(StatueHolder statueHolder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        super(statueHolder, inventory, component, dataSyncHandler);
    }

    @Override
    protected List<ArmorStandWidget> buildWidgets(LivingEntity livingEntity) {
        // only move server-side to prevent rubber banding
        return buildWidgets(this,
                livingEntity,
                List.of(SCALE_WIDGET_FACTORY,
                        ROTATION_WIDGET_FACTORY,
                        POSITION_INCREMENT_WIDGET_FACTORY,
                        POSITION_X_WIDGET_FACTORY,
                        POSITION_Y_WIDGET_FACTORY,
                        POSITION_Z_WIDGET_FACTORY));
    }

    @Override
    public StatueScreenType getScreenType() {
        return ArmorStandScreenTypes.POSITION;
    }

    protected class CustomScaleWidget extends ScaleWidget {
        @Nullable
        private Boolean hasModifier;

        public CustomScaleWidget(Component title, DoubleSupplier currentValue, Consumer<Float> newValue) {
            super(title, currentValue, newValue);
        }

        @Override
        protected void setNewValue(double newValue) {
            if (ArmorStandPositionScreen.this.dataSyncHandler instanceof CommandDataSyncHandler commandDataSyncHandler) {
                commandDataSyncHandler.sendScale(this.hasModifier == null || this.hasModifier,
                        toLogarithmicValue(newValue),
                        true);
            } else {
                super.setNewValue(newValue);
            }

            this.hasModifier = null;
        }

        @Override
        protected OptionalDouble getDefaultValue() {
            return OptionalDouble.of(fromLogarithmicValue(ScaleAttributeHelper.DEFAULT_SCALE));
        }

        @Override
        protected void applyClientValue(double newValue) {
            if (this.hasModifier == null) {
                this.hasModifier = ArmorStandPositionScreen.this.getHolder()
                        .getEntity()
                        .getAttributes()
                        .hasModifier(Attributes.SCALE, ScaleAttributeHelper.SCALE_BONUS_ID);
            }

            super.applyClientValue(newValue);
        }
    }
}
