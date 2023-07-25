package fuzs.armorstatues.api.client.gui.screens.armorstand;

import com.google.common.collect.Lists;
import fuzs.armorstatues.api.client.gui.components.TickButton;
import fuzs.armorstatues.api.client.gui.components.TickingButton;
import fuzs.armorstatues.api.network.client.data.DataSyncHandler;
import fuzs.armorstatues.api.world.inventory.ArmorStandHolder;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandAlignment;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandScreenType;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandStyleOptions;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
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
        this.addVanillaTweaksCreditButton();
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
            this.children.add(ArmorStandAlignmentsScreen.this.addRenderableWidget(new TickButton(posX, posY + 1, 94, 20, Component.translatable("armorstatues.screen.alignments.centered"), Component.translatable("armorstatues.screen.alignments.aligned"), button -> {
                this.setNewPosition(this.getCurrentPosition().align(EnumSet.allOf(Direction.Axis.class)).add(0.5, 0.0, 0.5));
            }, (button, poseStack, mouseX, mouseY) -> {
                ArmorStandAlignmentsScreen.this.renderTooltip(poseStack, ArmorStandAlignmentsScreen.this.font.split(Component.translatable("armorstatues.screen.alignments.centered.description"), 175), mouseX, mouseY);
            })));
            this.children.add(ArmorStandAlignmentsScreen.this.addRenderableWidget(new TickButton(posX + 100, posY + 1, 94, 20, Component.translatable("armorstatues.screen.alignments.cornered"), Component.translatable("armorstatues.screen.alignments.aligned"), button -> {
                this.setNewPosition(this.getCurrentPosition().align(EnumSet.allOf(Direction.Axis.class)));
            }, (button, poseStack, mouseX, mouseY) -> {
                ArmorStandAlignmentsScreen.this.renderTooltip(poseStack, ArmorStandAlignmentsScreen.this.font.split(Component.translatable("armorstatues.screen.alignments.cornered.description"), 175), mouseX, mouseY);
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
            this.children.add(ArmorStandAlignmentsScreen.this.addRenderableWidget(new TickButton(posX, posY + 1, 194, 20, Component.translatable(this.alignment.getTranslationKey()), Component.translatable("armorstatues.screen.alignments.aligned"), button -> {
                ArmorStandAlignmentsScreen.this.dataSyncHandler.sendPose(this.alignment.getPose(), false);
                ArmorStand armorStand = ArmorStandAlignmentsScreen.this.holder.getArmorStand();
                this.setNewPosition(this.getCurrentPosition().align(EnumSet.allOf(Direction.Axis.class)).add(0.5, 0.0, 0.5).add(this.alignment.getPosition(armorStand.isSmall())));
                if (!armorStand.isInvisible()) {
                    ArmorStandAlignmentsScreen.this.dataSyncHandler.sendStyleOption(ArmorStandStyleOptions.INVISIBLE, true, false);
                }
                if (!armorStand.isNoGravity()) {
                    ArmorStandAlignmentsScreen.this.dataSyncHandler.sendStyleOption(ArmorStandStyleOptions.NO_GRAVITY, true, false);
                }
                ArmorStandAlignmentsScreen.this.dataSyncHandler.finalizeCurrentOperation();
            }, (button, poseStack, mouseX, mouseY) -> {
                Component component = Component.translatable(this.alignment.getDescriptionsKey());
                List<FormattedCharSequence> lines = ArmorStandAlignmentsScreen.this.font.split(component, 175);
                ArmorStandAlignmentsScreen.this.renderTooltip(poseStack, lines, mouseX, mouseY);
            })));
        }
    }
}
