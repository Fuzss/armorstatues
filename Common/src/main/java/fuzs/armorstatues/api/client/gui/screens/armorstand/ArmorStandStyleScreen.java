package fuzs.armorstatues.api.client.gui.screens.armorstand;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.api.client.gui.components.TickBoxButton;
import fuzs.armorstatues.api.network.client.data.DataSyncHandler;
import fuzs.armorstatues.api.world.inventory.ArmorStandHolder;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandScreenType;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandStyleOption;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandStyleOptions;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;

import java.util.stream.Stream;

public class ArmorStandStyleScreen extends ArmorStandTickBoxScreen<ArmorStandStyleOption> {
    private static final Component STYLE_NAME_COMPONENT = Component.translatable("armorstatues.screen.style.name");

    public ArmorStandStyleScreen(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        super(holder, inventory, component, dataSyncHandler);
    }

    @Override
    protected ArmorStandStyleOption[] getAllTickBoxValues() {
        return Stream.of(ArmorStandStyleOptions.values())
                .filter(option -> option.allowChanges(this.minecraft.player))
                .toArray(ArmorStandStyleOption[]::new);
    }

    @Override
    protected AbstractWidget makeTickBoxWidget(ArmorStand armorStand, int buttonStartY, int index, ArmorStandStyleOption option) {
        return new TickBoxButton(this.leftPos + 96, this.topPos + buttonStartY + index * 22, 6, 76, Component.translatable(option.getTranslationKey()), option.getOption(armorStand), (Button button) -> {
            this.dataSyncHandler.sendStyleOption(option, ((TickBoxButton) button).isSelected());
        }, (Button button, PoseStack poseStack, int mouseX, int mouseY) -> {
            this.renderTooltip(poseStack, this.minecraft.font.split(Component.translatable(option.getDescriptionKey()), 175), mouseX, mouseY);
        });
    }

    @Override
    protected void syncNameChange(String input) {
        this.dataSyncHandler.sendName(input);
    }

    @Override
    protected int getNameMaxLength() {
        return 50;
    }

    @Override
    protected String getNameDefaultValue() {
        return this.holder.getArmorStand().getName().getString();
    }

    @Override
    protected Component getNameComponent() {
        return STYLE_NAME_COMPONENT;
    }

    @Override
    public ArmorStandScreenType getScreenType() {
        return ArmorStandScreenType.STYLE;
    }
}
