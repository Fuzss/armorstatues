package fuzs.armorstatues.api.client.gui.screens.armorstand;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.api.client.gui.components.TickBoxButton;
import fuzs.armorstatues.api.client.gui.screens.armorstand.data.ArmorStandStyleOption;
import fuzs.armorstatues.api.network.client.data.DataSyncHandler;
import fuzs.armorstatues.world.inventory.ArmorStandHolder;
import fuzs.armorstatues.world.inventory.ArmorStandScreenType;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;

import java.util.stream.Stream;

public class ArmorStandStyleScreen extends ArmorStandTickBoxScreen<ArmorStandStyleOption> {

    public ArmorStandStyleScreen(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        super(holder, inventory, component, dataSyncHandler);
    }

    @Override
    protected ArmorStandStyleOption[] getAllTickBoxValues() {
        return Stream.of(ArmorStandStyleOption.values())
                .filter(option -> this.minecraft.player.getAbilities().instabuild || !option.onlyCreative())
                .toArray(ArmorStandStyleOption[]::new);
    }

    @Override
    protected AbstractWidget makeTickBoxWidget(ArmorStand armorStand, int buttonStartY, int index, ArmorStandStyleOption option) {
        return new TickBoxButton(this.leftPos + 96, this.topPos + buttonStartY + index * 22, 6, 76, option.getComponent(), option.getOption(armorStand), (Button button) -> {
            this.dataSyncHandler.sendStyleOption(option, ((TickBoxButton) button).isSelected());
        }, (Button button, PoseStack poseStack, int mouseX, int mouseY) -> {
            this.renderTooltip(poseStack, option.getDescriptionComponent(), mouseX, mouseY);
        });
    }

    @Override
    public ArmorStandScreenType getScreenType() {
        return ArmorStandScreenType.STYLE;
    }
}
