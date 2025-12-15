package fuzs.armorstatues.client.gui.screens.armorstand;

import fuzs.armorstatues.world.inventory.data.ArmorStandScreenTypes;
import fuzs.statuemenus.api.v1.client.gui.screens.StatueButtonsScreen;
import fuzs.statuemenus.api.v1.client.gui.screens.StatuePositionScreen;
import fuzs.statuemenus.api.v1.network.client.data.DataSyncHandler;
import fuzs.statuemenus.api.v1.world.inventory.StatueHolder;
import fuzs.statuemenus.api.v1.world.inventory.data.StatueAlignment;
import fuzs.statuemenus.api.v1.world.inventory.data.StatueScreenType;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class ArmorStandAlignmentsScreen extends StatueButtonsScreen {

    public ArmorStandAlignmentsScreen(StatueHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        super(holder, inventory, component, dataSyncHandler);
    }

    @Override
    protected List<ArmorStandWidget> buildWidgets(LivingEntity livingEntity) {
        List<ArmorStandWidget> widgets = new ArrayList<>();
        widgets.add(new DoubleButtonWidget(Component.translatable(StatuePositionScreen.CENTERED_TRANSLATION_KEY),
                Component.translatable(StatuePositionScreen.CORNERED_TRANSLATION_KEY),
                Component.translatable(StatuePositionScreen.CENTERED_DESCRIPTION_TRANSLATION_KEY),
                Component.translatable(StatuePositionScreen.CORNERED_DESCRIPTION_TRANSLATION_KEY),
                Component.translatable(StatuePositionScreen.ALIGNED_TRANSLATION_KEY),
                button -> {
                    Vec3 newPosition = this.holder.getEntity()
                            .position()
                            .align(EnumSet.allOf(Direction.Axis.class))
                            .add(0.5, 0.0, 0.5);
                    this.dataSyncHandler.sendPosition(newPosition.x(), newPosition.y(), newPosition.z());
                },
                button -> {
                    Vec3 newPosition = this.holder.getEntity().position().align(EnumSet.allOf(Direction.Axis.class));
                    this.dataSyncHandler.sendPosition(newPosition.x(), newPosition.y(), newPosition.z());
                }));
        for (StatueAlignment statueAlignment : StatueAlignment.values()) {
            widgets.add(new SingleButtonWidget(Component.translatable(statueAlignment.getTranslationKey()),
                    Component.translatable(statueAlignment.getDescriptionsKey()),
                    Component.translatable(StatuePositionScreen.ALIGNED_TRANSLATION_KEY),
                    button -> {
                        ArmorStandAlignmentsScreen.this.dataSyncHandler.sendAlignment(statueAlignment);
                    }));
        }

        return widgets;
    }

    @Override
    protected void init() {
        super.init();
        this.addVanillaTweaksCreditsButton();
    }

    @Override
    public StatueScreenType getScreenType() {
        return ArmorStandScreenTypes.ALIGNMENTS;
    }
}
