package fuzs.armorstatues.api.client.gui.screens.armorstand;

import fuzs.armorstatues.api.ArmorStatuesApi;
import fuzs.armorstatues.api.client.gui.components.TickButton;
import fuzs.armorstatues.api.network.client.data.DataSyncHandler;
import fuzs.armorstatues.api.world.inventory.ArmorStandHolder;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public abstract class AbstractArmorStandPositionScreen extends ArmorStandWidgetsScreen {
    public static final String CENTERED_TRANSLATION_KEY = ArmorStatuesApi.MOD_ID + ".screen.centered";
    public static final String CENTERED_DESCRIPTION_TRANSLATION_KEY = ArmorStatuesApi.MOD_ID + ".screen.centered.description";
    public static final String CORNERED_TRANSLATION_KEY = ArmorStatuesApi.MOD_ID + ".screen.cornered";
    public static final String CORNERED_DESCRIPTION_TRANSLATION_KEY = ArmorStatuesApi.MOD_ID + ".screen.cornered.description";

    public AbstractArmorStandPositionScreen(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        super(holder, inventory, component, dataSyncHandler);
    }

    protected class PositionAlignWidget extends AbstractPositionScreenWidget {

        public PositionAlignWidget() {
            super(Component.empty());
        }

        @Override
        protected boolean shouldTick() {
            return true;
        }

        @Override
        public void init(int posX, int posY) {
            super.init(posX, posY);
            this.children.add(AbstractArmorStandPositionScreen.this.addRenderableWidget(new TickButton(posX, posY + 1, 94, 20, Component.translatable(CENTERED_TRANSLATION_KEY), Component.translatable(ALIGNED_TRANSLATION_KEY), button -> {
                Vec3 newPosition = AbstractArmorStandPositionScreen.this.holder.getArmorStand().position().align(EnumSet.allOf(Direction.Axis.class)).add(0.5, 0.0, 0.5);
                AbstractArmorStandPositionScreen.this.dataSyncHandler.sendPosition(newPosition.x(), newPosition.y(), newPosition.z());
            }, (button, poseStack, mouseX, mouseY) -> {
                AbstractArmorStandPositionScreen.this.renderTooltip(poseStack, AbstractArmorStandPositionScreen.this.font.split(Component.translatable(CENTERED_DESCRIPTION_TRANSLATION_KEY), 175), mouseX, mouseY);
            })));
            this.children.add(AbstractArmorStandPositionScreen.this.addRenderableWidget(new TickButton(posX + 100, posY + 1, 94, 20, Component.translatable(CORNERED_TRANSLATION_KEY), Component.translatable(ALIGNED_TRANSLATION_KEY), button -> {
                Vec3 newPosition = AbstractArmorStandPositionScreen.this.holder.getArmorStand().position().align(EnumSet.allOf(Direction.Axis.class));
                AbstractArmorStandPositionScreen.this.dataSyncHandler.sendPosition(newPosition.x(), newPosition.y(), newPosition.z());
            }, (button, poseStack, mouseX, mouseY) -> {
                AbstractArmorStandPositionScreen.this.renderTooltip(poseStack, AbstractArmorStandPositionScreen.this.font.split(Component.translatable(CORNERED_DESCRIPTION_TRANSLATION_KEY), 175), mouseX, mouseY);
            })));
        }
    }
}
