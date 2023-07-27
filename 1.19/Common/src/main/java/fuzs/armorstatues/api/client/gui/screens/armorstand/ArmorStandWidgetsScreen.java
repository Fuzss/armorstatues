package fuzs.armorstatues.api.client.gui.screens.armorstand;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.api.client.gui.components.NewTextureButton;
import fuzs.armorstatues.api.client.gui.components.TickingButton;
import fuzs.armorstatues.api.network.client.data.DataSyncHandler;
import fuzs.armorstatues.api.world.inventory.ArmorStandHolder;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public abstract class ArmorStandWidgetsScreen extends AbstractArmorStandScreen {
    protected final List<PositionScreenWidget> widgets;
    @Nullable
    private PositionScreenWidget activeWidget;

    public ArmorStandWidgetsScreen(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        super(holder, inventory, component, dataSyncHandler);
        this.widgets = ImmutableList.copyOf(this.buildWidgets(holder.getArmorStand()));
    }

    protected abstract List<PositionScreenWidget> buildWidgets(ArmorStand armorStand);

    private Collection<PositionScreenWidget> getActivePositionComponentWidgets() {
        if (this.activeWidget != null) {
            List<PositionScreenWidget> activeWidgets = Lists.newArrayList(this.activeWidget);
            for (PositionScreenWidget widget : this.widgets) {
                if (widget.alwaysVisible(this.activeWidget)) activeWidgets.add(widget);
            }
            return activeWidgets;
        }
        return this.widgets;
    }

    protected void setActiveWidget(PositionScreenWidget widget) {
        if (this.activeWidget == widget) {
            this.toggleMenuRendering(false);
            this.activeWidget = null;
        } else {
            this.activeWidget = widget;
            this.toggleMenuRendering(true);
        }
    }

    @Override
    protected boolean renderInventoryEntity() {
        return false;
    }

    @Override
    protected boolean disableMenuRendering() {
        return this.activeWidget != null;
    }

    @Override
    protected void toggleMenuRendering(boolean disableMenuRendering) {
        super.toggleMenuRendering(disableMenuRendering);
        for (PositionScreenWidget widget : this.widgets) {
            widget.setVisible(!disableMenuRendering || widget.alwaysVisible(this.activeWidget));
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.getActivePositionComponentWidgets().forEach(PositionScreenWidget::tick);
    }

    @Override
    protected void init() {
        super.init();
        int startY = (this.imageHeight - this.widgets.size() * 22 - (this.widgets.size() - 1) * 7) / 2;
        for (int i = 0; i < this.widgets.size(); i++) {
            this.widgets.get(i).init(this.leftPos + 8, this.topPos + startY + this.getWidgetRenderOffset() + i * 29);
        }
    }

    protected int getWidgetRenderOffset() {
        return 7;
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        super.renderBg(poseStack, partialTick, mouseX, mouseY);
        for (PositionScreenWidget widget : this.getActivePositionComponentWidgets()) {
            widget.render(poseStack, mouseX, mouseY, partialTick);
        }
    }

    protected interface PositionScreenWidget {

        void tick();

        void reset();

        void init(int posX, int posY);

        void setVisible(boolean visible);

        void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick);

        boolean alwaysVisible(@Nullable PositionScreenWidget activeWidget);
    }

    protected abstract class AbstractPositionScreenWidget implements PositionScreenWidget {
        protected final Component title;
        protected int posX;
        protected int posY;
        protected List<AbstractWidget> children;

        protected AbstractPositionScreenWidget(Component title) {
            this.title = title;
        }

        @Override
        public void tick() {
            if (this.shouldTick()) {
                for (AbstractWidget widget : this.children) {
                    if (widget instanceof TickingButton tickButton) tickButton.tick();
                }
            }
        }
        
        protected boolean shouldTick() {
            return false;
        }

        @Override
        public void reset() {

        }

        @Override
        public void init(int posX, int posY) {
            this.posX = posX;
            this.posY = posY;
            this.children = Lists.newArrayList();
        }

        @Override
        public final void setVisible(boolean visible) {
            for (AbstractWidget widget : this.children) {
                widget.visible = visible;
            }
        }

        @Override
        public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
            if (ArmorStandWidgetsScreen.this.disableMenuRendering()) {
                NewTextureButton.drawCenteredString(poseStack, ArmorStandWidgetsScreen.this.font, this.title, this.posX + 36, this.posY + 6, 0xFFFFFFFF, true);
            } else {
                NewTextureButton.drawCenteredString(poseStack, ArmorStandWidgetsScreen.this.font, this.title, this.posX + 36, this.posY + 6, 0x404040, false);
            }
        }

        @Override
        public boolean alwaysVisible(@Nullable PositionScreenWidget activeWidget) {
            return activeWidget == this;
        }
    }
}
