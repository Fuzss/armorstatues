package fuzs.armorstatues.client.gui.screens.armorstand;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.client.gui.components.TickBoxButton;
import fuzs.armorstatues.client.gui.screens.armorstand.data.ArmorStandScreenType;
import fuzs.armorstatues.client.gui.screens.armorstand.data.ArmorStandStyleOption;
import fuzs.armorstatues.network.client.data.DataSyncHandler;
import fuzs.armorstatues.world.inventory.ArmorStandHolder;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;

import java.util.stream.Stream;

public class ArmorStandStyleScreen extends ArmorStandTickBoxScreen {

    public ArmorStandStyleScreen(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        super(holder, inventory, component, dataSyncHandler);
    }

    @Override
    protected void init() {
        super.init();
        ArmorStand armorStand = this.holder.getArmorStand();
        int optionsSize = (int) Stream.of(ArmorStandStyleOption.values()).filter(option -> this.minecraft.player.getAbilities().instabuild || !option.onlyCreative()).count();
        final int buttonStartY = (this.imageHeight - optionsSize * 20 - (optionsSize - 1) * 2) / 2;
        for (int i = 0, j = 0; i < ArmorStandStyleOption.values().length; i++) {
            ArmorStandStyleOption option = ArmorStandStyleOption.values()[i];
            if (!this.minecraft.player.getAbilities().instabuild && option.onlyCreative()) continue;
            this.addRenderableWidget(new TickBoxButton(this.leftPos + 98, this.topPos + buttonStartY + j++ * 22, 73, option.getComponent(), option.getOption(armorStand), (Button button) -> {
                this.dataSyncHandler.sendStyleOption(option, ((TickBoxButton) button).isSelected());
            }, (Button button, PoseStack poseStack, int mouseX, int mouseY) -> {
                this.renderTooltip(poseStack, option.getDescriptionComponent(), mouseX, mouseY);
            }));
        }
    }

    @Override
    public ArmorStandScreenType getScreenType() {
        return ArmorStandScreenType.STYLE;
    }
}
