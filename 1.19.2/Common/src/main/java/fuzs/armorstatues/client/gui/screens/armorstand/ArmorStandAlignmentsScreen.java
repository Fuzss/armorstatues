package fuzs.armorstatues.client.gui.screens.armorstand;

import com.google.common.collect.Lists;
import fuzs.armorstatues.api.client.gui.screens.armorstand.ArmorStandButtonsScreen;
import fuzs.armorstatues.api.client.gui.screens.armorstand.ArmorStandPositionScreen;
import fuzs.armorstatues.api.network.client.data.DataSyncHandler;
import fuzs.armorstatues.api.world.inventory.ArmorStandHolder;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandAlignment;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandScreenType;
import fuzs.armorstatues.init.ModRegistry;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class ArmorStandAlignmentsScreen extends ArmorStandButtonsScreen {

    public ArmorStandAlignmentsScreen(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        super(holder, inventory, component, dataSyncHandler);
    }

    @Override
    protected List<ArmorStandWidget> buildWidgets(ArmorStand armorStand) {
        List<ArmorStandWidget> widgets = Lists.newArrayList();
        widgets.add(new DoubleButtonWidget(Component.translatable(ArmorStandPositionScreen.CENTERED_TRANSLATION_KEY), Component.translatable(ArmorStandPositionScreen.CORNERED_TRANSLATION_KEY), Component.translatable(ArmorStandPositionScreen.CENTERED_DESCRIPTION_TRANSLATION_KEY), Component.translatable(ArmorStandPositionScreen.CORNERED_DESCRIPTION_TRANSLATION_KEY), Component.translatable(ArmorStandPositionScreen.ALIGNED_TRANSLATION_KEY), button -> {
            Vec3 newPosition = this.holder.getArmorStand().position().align(EnumSet.allOf(Direction.Axis.class)).add(0.5, 0.0, 0.5);
            this.dataSyncHandler.sendPosition(newPosition.x(), newPosition.y(), newPosition.z());
        }, button -> {
            Vec3 newPosition = this.holder.getArmorStand().position().align(EnumSet.allOf(Direction.Axis.class));
            this.dataSyncHandler.sendPosition(newPosition.x(), newPosition.y(), newPosition.z());
        }));
        for (ArmorStandAlignment alignment : ArmorStandAlignment.values()) {
            widgets.add(new SingleButtonWidget(Component.translatable(alignment.getTranslationKey()), Component.translatable(alignment.getDescriptionsKey()), Component.translatable(ArmorStandPositionScreen.ALIGNED_TRANSLATION_KEY), button -> {
                ArmorStandAlignmentsScreen.this.dataSyncHandler.sendAlignment(alignment);
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
    public ArmorStandScreenType getScreenType() {
        return ModRegistry.ALIGNMENTS_SCREEN_TYPE;
    }
}
