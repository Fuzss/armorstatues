package fuzs.armorstatues.api.client.gui.screens.armorstand;

import com.google.common.collect.Lists;
import fuzs.armorstatues.api.client.gui.components.TickButton;
import fuzs.armorstatues.api.client.gui.components.TickingButton;
import fuzs.armorstatues.api.network.client.data.DataSyncHandler;
import fuzs.armorstatues.api.world.inventory.ArmorStandHolder;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandAlignment;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandScreenType;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandStyleOptions;
import net.minecraft.Util;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class ArmorStandAlignmentsScreen extends ArmorStandWidgetsScreen {

    public ArmorStandAlignmentsScreen(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        super(holder, inventory, component, dataSyncHandler);
    }

    @Override
    protected List<PositionScreenWidget> buildWidgets(ArmorStand armorStand) {
        List<PositionScreenWidget> widgets = Lists.newArrayList(new PositionAlignWidget());
        for (ArmorStandAlignment alignment : ArmorStandAlignment.values()) {
            widgets.add(new AlignmentWidget(alignment));
        }
        return widgets;
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new ImageButton(this.leftPos + 6, this.topPos + 6, 20, 20, 136, 64, 20, ARMOR_STAND_WIDGETS_LOCATION, 256, 256, button -> {
            this.minecraft.setScreen(new ConfirmLinkScreen((bl) -> {
                if (bl) Util.getPlatform().openUri("https://vanillatweaks.net/");
                this.minecraft.setScreen(this);
            }, "https://vanillatweaks.net/", true));
        }, (button, poseStack, mouseX, mouseY) -> {
            this.renderTooltip(poseStack, Component.translatable("armorstatues.screen.alignments.credit"), mouseX, mouseY);
        }, CommonComponents.EMPTY));
    }

    @Override
    public ArmorStandScreenType getScreenType() {
        return ArmorStandScreenType.ALIGNMENTS;
    }

    private abstract class BlockPositionWidget extends AbstractPositionScreenWidget {

        public BlockPositionWidget() {
            super(Component.empty());
        }

        @Override
        public void tick() {
            super.tick();
            for (AbstractWidget widget : this.children) {
                if (widget instanceof TickingButton tickButton) tickButton.tick();
            }
        }

        protected Vec3 getCurrentPosition() {
            return ArmorStandAlignmentsScreen.this.holder.getArmorStand().position();
        }

        protected void setNewPosition(Vec3 vec3) {
            ArmorStandAlignmentsScreen.this.dataSyncHandler.sendPosition(vec3.x(), vec3.y(), vec3.z());
        }
    }

    private class PositionAlignWidget extends BlockPositionWidget {

        @Override
        public void init(int posX, int posY) {
            super.init(posX, posY);
            this.children.add(ArmorStandAlignmentsScreen.this.addRenderableWidget(new TickButton(posX, posY + 1, 94, 20, Component.translatable("armorstatues.screen.position.centered"), Component.translatable("armorstatues.screen.position.aligned"), button -> {
                this.setNewPosition(this.getCurrentPosition().align(EnumSet.allOf(Direction.Axis.class)).add(0.5, 0.0, 0.5));
            })));
            this.children.add(ArmorStandAlignmentsScreen.this.addRenderableWidget(new TickButton(posX + 100, posY + 1, 94, 20, Component.translatable("armorstatues.screen.position.cornered"), Component.translatable("armorstatues.screen.position.aligned"), button -> {
                this.setNewPosition(this.getCurrentPosition().align(EnumSet.allOf(Direction.Axis.class)));
            })));
        }
    }

    private class AlignmentWidget extends BlockPositionWidget {
        private final ArmorStandAlignment alignment;

        public AlignmentWidget(ArmorStandAlignment alignment) {
            this.alignment = alignment;
        }

        @Override
        public void init(int posX, int posY) {
            super.init(posX, posY);
            this.children.add(ArmorStandAlignmentsScreen.this.addRenderableWidget(new TickButton(posX, posY + 1, 194, 20, this.alignment.getComponent(), Component.translatable("armorstatues.screen.position.aligned"), button -> {
                ArmorStandAlignmentsScreen.this.dataSyncHandler.sendPose(this.alignment.getPose());
                ArmorStand armorStand = ArmorStandAlignmentsScreen.this.holder.getArmorStand();
                this.setNewPosition(this.getCurrentPosition().align(EnumSet.allOf(Direction.Axis.class)).add(0.5, 0.0, 0.5).add(this.alignment.getPosition(armorStand.isSmall())));
                if (!armorStand.isInvisible()) {
                    ArmorStandAlignmentsScreen.this.dataSyncHandler.sendStyleOption(ArmorStandStyleOptions.INVISIBLE, true);
                }
                if (!armorStand.isNoGravity()) {
                    ArmorStandAlignmentsScreen.this.dataSyncHandler.sendStyleOption(ArmorStandStyleOptions.NO_GRAVITY, true);
                }
            })));
        }
    }
}
