package fuzs.armorstatues.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.client.gui.components.TickButton;
import fuzs.armorstatues.client.gui.components.UnboundedSliderButton;
import fuzs.armorstatues.network.client.data.DataSyncHandler;
import fuzs.armorstatues.world.inventory.ArmorStandHolder;
import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import fuzs.puzzleslib.client.core.ClientCoreServices;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;

import java.util.Optional;

public abstract class AbstractArmorStandScreen extends Screen implements MenuAccess<ArmorStandMenu>, ArmorStandScreen {
    private static final ResourceLocation ARMOR_STAND_BACKGROUND_LOCATION = new ResourceLocation(ArmorStatues.MOD_ID, "textures/gui/container/armor_stand/background.png");
    public static final ResourceLocation ARMOR_STAND_WIDGETS_LOCATION = new ResourceLocation(ArmorStatues.MOD_ID, "textures/gui/container/armor_stand/widgets.png");

    protected final int imageWidth = 210;
    protected final int imageHeight = 188;
    protected final ArmorStandHolder holder;
    private final Inventory inventory;
    protected final DataSyncHandler dataSyncHandler;
    protected int leftPos;
    protected int topPos;
    protected int inventoryEntityX;
    protected int inventoryEntityY;
    protected boolean smallInventoryEntity;
    protected int mouseX;
    protected int mouseY;
    private AbstractWidget closeButton;

    public AbstractArmorStandScreen(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        super(component);
        this.holder = holder;
        this.inventory = inventory;
        this.dataSyncHandler = dataSyncHandler;
    }

    @Override
    public <T extends Screen & MenuAccess<ArmorStandMenu> & ArmorStandScreen> T createScreenType(ArmorStandScreenType screenType) {
        return (T) screenType.createScreenType(this.holder, this.inventory, this.title, this.dataSyncHandler);
    }

    @Override
    public void tick() {
        super.tick();
        for (GuiEventListener child : this.children()) {
            if (child instanceof TickButton button) button.tick();
        }
    }

    @Override
    protected void init() {
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
        if (this.withCloseButton()) {
            this.closeButton = this.addRenderableWidget(makeCloseButton(this, this.leftPos, this.imageWidth, this.topPos));
        }
    }

    public static AbstractButton makeCloseButton(Screen screen, int leftPos, int imageWidth, int topPos) {
        return new ImageButton(leftPos + imageWidth - 15 - 8, topPos + 8, 15, 15, 136, 0, ARMOR_STAND_WIDGETS_LOCATION, button -> {
            screen.onClose();
        });
    }

    protected boolean withCloseButton() {
        return true;
    }

    protected boolean renderInventoryEntity() {
        return true;
    }

    protected boolean disableMenuRendering() {
        return false;
    }

    protected void toggleMenuRendering(boolean disableMenuRendering) {
        this.closeButton.visible = !disableMenuRendering;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            if (!this.disableMenuRendering() && handleTabClicked((int) mouseX, (int) mouseY, this.leftPos, this.topPos, this.imageHeight, this, this.dataSyncHandler.tabs())) {
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public static  <T extends Screen & ArmorStandScreen> boolean handleTabClicked(int mouseX, int mouseY, int leftPos, int topPos, int imageHeight, T screen, ArmorStandScreenType[] tabs) {
        Optional<ArmorStandScreenType> hoveredTab = findHoveredTab(leftPos, topPos, imageHeight, mouseX, mouseY, tabs);
        if (hoveredTab.isPresent() && hoveredTab.get() != screen.getScreenType()) {
            ClientCoreServices.SCREENS.getMinecraft(screen).getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            ClientCoreServices.SCREENS.getMinecraft(screen).setScreen(screen.createScreenType(hoveredTab.get()));
            return true;
        }
        return false;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        if (!this.disableMenuRendering()) {
            this.renderBackground(poseStack);
        }
        this.renderBg(poseStack, partialTick, mouseX, mouseY);
        super.render(poseStack, mouseX, mouseY, partialTick);
        if (!this.disableMenuRendering()) {
            findHoveredTab(this.leftPos, this.topPos, this.imageHeight, mouseX, mouseY, this.dataSyncHandler.tabs()).ifPresent(hoveredTab -> {
                this.renderTooltip(poseStack, hoveredTab.getComponent(), mouseX, mouseY);
            });
        }
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        if (!this.disableMenuRendering()) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, ARMOR_STAND_BACKGROUND_LOCATION);
            this.blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
            drawTabs(poseStack, this.leftPos, this.topPos, this.imageHeight, this, this.dataSyncHandler.tabs());
            this.renderEntityInInventory(poseStack);
        }
    }

    private void renderEntityInInventory(PoseStack poseStack) {
        if (this.renderInventoryEntity()) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, ARMOR_STAND_WIDGETS_LOCATION);
            if (this.smallInventoryEntity) {
                this.blit(poseStack, this.leftPos + this.inventoryEntityX, this.topPos + this.inventoryEntityY, 200, 174, 50, 72);
                InventoryScreen.renderEntityInInventory(this.leftPos + this.inventoryEntityX + 24, this.topPos + this.inventoryEntityY + 65, 30, this.leftPos + this.inventoryEntityX + 24 - 10 - this.mouseX, this.topPos + this.inventoryEntityY + 65 - 44 - this.mouseY, this.holder.getArmorStand());
            } else {
                this.blit(poseStack, this.leftPos + this.inventoryEntityX, this.topPos + this.inventoryEntityY, 0, 0, 76, 108);
                InventoryScreen.renderEntityInInventory(this.leftPos + this.inventoryEntityX + 38, this.topPos + this.inventoryEntityY + 98, 45, (float) (this.leftPos + this.inventoryEntityX + 38 - 5) - this.mouseX, (float) (this.topPos + this.inventoryEntityY + 98 - 66) - this.mouseY, this.holder.getArmorStand());
            }
        }
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        // make sure value is sent to server when mouse is released outside of slider widget, but when the slider value has been changed
        boolean mouseReleased = false;
        for (GuiEventListener child : this.children()) {
            if (child instanceof UnboundedSliderButton sliderButton) {
                if (sliderButton.isDirty()) {
                    mouseReleased |= child.mouseReleased(mouseX, mouseY, button);
                }
            }
        }
        return mouseReleased || super.mouseReleased(mouseX, mouseY, button);
    }

    public static <T extends Screen & ArmorStandScreen> void drawTabs(PoseStack poseStack, int leftPos, int topPos, int imageHeight, T screen, ArmorStandScreenType[] tabs) {
        int tabsStartY = getTabsStartY(imageHeight, tabs.length);
        for (int i = 0; i < tabs.length; i++) {
            ArmorStandScreenType tabType = tabs[i];
            int tabX = leftPos - 32;
            int tabY = topPos + tabsStartY + 27 * i;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, ARMOR_STAND_WIDGETS_LOCATION);
            screen.blit(poseStack, tabX, tabY, 212, tabType == screen.getScreenType() ? 0 : 27, 35, 26);
            ItemRenderer itemRenderer = ClientCoreServices.SCREENS.getItemRenderer(screen);
            itemRenderer.blitOffset = 100.0F;
            itemRenderer.renderAndDecorateItem(tabType.getIcon(), tabX + 10, tabY + 5);
            itemRenderer.blitOffset = 0.0F;
        }
    }

    public static Optional<ArmorStandScreenType> findHoveredTab(int leftPos, int topPos, int imageHeight, int mouseX, int mouseY, ArmorStandScreenType[] tabs) {
        int tabsStartY = getTabsStartY(imageHeight, tabs.length);
        for (int i = 0; i < tabs.length; i++) {
            int tabX = leftPos - 32;
            int tabY = topPos + tabsStartY + 27 * i;
            if (mouseX > tabX && mouseX <= tabX + 32 && mouseY > tabY && mouseY <= tabY + 26) {
                return Optional.of(tabs[i]);
            }
        }
        return Optional.empty();
    }

    private static int getTabsStartY(int imageHeight, int tabsCount) {
        int tabsHeight = tabsCount * 26 + (tabsCount - 1);
        return (imageHeight - tabsHeight) / 2;
    }

    @Override
    public ArmorStandMenu getMenu() {
        return (ArmorStandMenu) this.holder;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        this.minecraft.player.closeContainer();
        super.onClose();
    }
}
