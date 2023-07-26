package fuzs.armorstatues.api.client.gui.screens.armorstand;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.api.ArmorStatuesApi;
import fuzs.armorstatues.api.client.gui.components.TickingButton;
import fuzs.armorstatues.api.client.gui.components.UnboundedSliderButton;
import fuzs.armorstatues.api.network.client.data.DataSyncHandler;
import fuzs.armorstatues.api.world.inventory.ArmorStandHolder;
import fuzs.armorstatues.api.world.inventory.ArmorStandMenu;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandScreenType;
import fuzs.puzzleslib.client.core.ClientCoreServices;
import fuzs.puzzleslib.client.gui.screens.CommonScreens;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class AbstractArmorStandScreen extends Screen implements MenuAccess<ArmorStandMenu>, ArmorStandScreen {
    public static final String VANILLA_TWEAKS_HOMEPAGE = "https://vanillatweaks.net/";
    public static final String CREDITS_TRANSLATION_KEY = ArmorStatuesApi.MOD_ID + ".screen.credits";
    public static final String APPLIED_TRANSLATION_KEY = ArmorStatuesApi.MOD_ID + ".screen.applied";
    public static final String ALIGNED_TRANSLATION_KEY = ArmorStatuesApi.MOD_ID + ".screen.aligned";
    private static final ResourceLocation ARMOR_STAND_BACKGROUND_LOCATION = ArmorStatuesApi.id("textures/gui/container/armor_stand/background.png");
    private static final ResourceLocation ARMOR_STAND_WIDGETS_LOCATION = ArmorStatuesApi.id("textures/gui/container/armor_stand/widgets.png");
    private static final ResourceLocation ARMOR_STAND_EQUIPMENT_LOCATION = ArmorStatuesApi.id("textures/gui/container/armor_stand/equipment.png");

    @Nullable
    static ArmorStandScreenType lastScreenType;
    static ArmorStandInInventoryRenderer armorStandRenderer = ArmorStandInInventoryRenderer.SIMPLE;

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
    @Nullable
    private AbstractWidget closeButton;

    public AbstractArmorStandScreen(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        super(component);
        this.holder = holder;
        this.inventory = inventory;
        this.dataSyncHandler = dataSyncHandler;
    }

    public static ResourceLocation getArmorStandBackgroundLocation() {
        return ARMOR_STAND_BACKGROUND_LOCATION;
    }

    public static ResourceLocation getArmorStandWidgetsLocation() {
        return ARMOR_STAND_WIDGETS_LOCATION;
    }

    public static ResourceLocation getArmorStandEquipmentLocation() {
        return ARMOR_STAND_EQUIPMENT_LOCATION;
    }

    @Override
    public ArmorStandHolder getHolder() {
        return this.holder;
    }

    @Override
    public DataSyncHandler getDataSyncHandler() {
        return this.dataSyncHandler;
    }

    @Override
    public <T extends Screen & MenuAccess<ArmorStandMenu> & ArmorStandScreen> T createScreenType(ArmorStandScreenType screenType) {
        T screen = ArmorStandScreenFactory.createScreenType(screenType, this.holder, this.inventory, this.title, this.dataSyncHandler);
        screen.setMouseX(this.mouseX);
        screen.setMouseY(this.mouseY);
        return screen;
    }

    @Override
    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    @Override
    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    @Override
    public void tick() {
        this.dataSyncHandler.tick();
        for (GuiEventListener child : this.children()) {
            if (child instanceof TickingButton button) button.tick();
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
        return new ImageButton(leftPos + imageWidth - 15 - 8, topPos + 8, 15, 15, 136, 0, getArmorStandWidgetsLocation(), button -> {
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
        if (this.closeButton != null) {
            this.closeButton.visible = !disableMenuRendering;
        }
    }

    protected void addVanillaTweaksCreditsButton() {
        this.addRenderableWidget(new ImageButton(this.leftPos + 6, this.topPos + 6, 20, 20, 136, 64, 20, getArmorStandWidgetsLocation(), 256, 256, button -> {
            this.minecraft.setScreen(new ConfirmLinkScreen((bl) -> {
                if (bl) Util.getPlatform().openUri(VANILLA_TWEAKS_HOMEPAGE);
                this.minecraft.setScreen(this);
            }, VANILLA_TWEAKS_HOMEPAGE, true));
        }, (button, poseStack, mouseX, mouseY) -> {
            this.renderTooltip(poseStack, this.font.split(Component.translatable(CREDITS_TRANSLATION_KEY), 175), mouseX, mouseY);
        }, CommonComponents.EMPTY));
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

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        if (!this.disableMenuRendering()) {
            this.renderBackground(poseStack);
        }
        this.renderBg(poseStack, partialTick, mouseX, mouseY);
        super.render(poseStack, mouseX, mouseY, partialTick);
        if (!this.disableMenuRendering()) {
            findHoveredTab(this.leftPos, this.topPos, this.imageHeight, mouseX, mouseY, this.dataSyncHandler.tabs()).ifPresent(hoveredTab -> {
                this.renderTooltip(poseStack, Component.translatable(hoveredTab.getTranslationKey()), mouseX, mouseY);
            });
        }
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        if (!this.disableMenuRendering()) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, getArmorStandBackgroundLocation());
            this.blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
            drawTabs(poseStack, this.leftPos, this.topPos, this.imageHeight, this, this.dataSyncHandler.tabs());
            this.renderEntityInInventory(poseStack);
        }
    }

    private void renderEntityInInventory(PoseStack poseStack) {
        if (this.renderInventoryEntity()) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, getArmorStandWidgetsLocation());
            if (this.smallInventoryEntity) {
                this.blit(poseStack, this.leftPos + this.inventoryEntityX, this.topPos + this.inventoryEntityY, 200, 184, 50, 72);
                this.renderArmorStandInInventory(this.leftPos + this.inventoryEntityX + 24, this.topPos + this.inventoryEntityY + 65, 30, this.leftPos + this.inventoryEntityX + 24 - 10 - this.mouseX, this.topPos + this.inventoryEntityY + 65 - 44 - this.mouseY);
            } else {
                this.blit(poseStack, this.leftPos + this.inventoryEntityX, this.topPos + this.inventoryEntityY, 0, 0, 76, 108);
                this.renderArmorStandInInventory(this.leftPos + this.inventoryEntityX + 38, this.topPos + this.inventoryEntityY + 98, 45, (float) (this.leftPos + this.inventoryEntityX + 38 - 5) - this.mouseX, (float) (this.topPos + this.inventoryEntityY + 98 - 66) - this.mouseY);
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

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        } else if (this.minecraft.options.keyInventory.matches(keyCode, scanCode)) {
            this.onClose();
            return true;
        } else if (handleHotbarKeyPressed(keyCode, scanCode, this, this.dataSyncHandler.tabs())) {
            return true;
        }
        return false;
    }

    public static <T extends Screen & ArmorStandScreen> boolean handleHotbarKeyPressed(int keyCode, int scanCode, T screen, ArmorStandScreenType[] tabs) {
        Minecraft minecraft = CommonScreens.INSTANCE.getMinecraft(screen);
        for (int i = 0; i < Math.min(tabs.length, 9); ++i) {
            if (minecraft.options.keyHotbarSlots[i].matches(keyCode, scanCode)) {
                if (openTabScreen(screen, tabs[i], true)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (super.mouseScrolled(mouseX, mouseY, delta)) {
            return true;
        } else {
            return handleMouseScrolled((int) mouseX, (int) mouseY, delta, this.leftPos, this.topPos, this.imageHeight, this, this.dataSyncHandler.tabs());
        }
    }

    public static <T extends Screen & ArmorStandScreen> boolean handleMouseScrolled(int mouseX, int mouseY, double delta, int leftPos, int topPos, int imageHeight, T screen, ArmorStandScreenType[] tabs) {
        delta = Math.signum(delta);
        if (delta != 0.0) {
            Optional<ArmorStandScreenType> optional = findHoveredTab(leftPos, topPos, imageHeight, mouseX, mouseY, tabs);
            if (optional.isPresent()) {
                ArmorStandScreenType screenType = cycleTabs(screen.getScreenType(), tabs, delta > 0.0);
                return openTabScreen(screen, screenType, false);
            }
        }
        return false;
    }

    public static <T extends Screen & ArmorStandScreen> boolean handleTabClicked(int mouseX, int mouseY, int leftPos, int topPos, int imageHeight, T screen, ArmorStandScreenType[] tabs) {
        Optional<ArmorStandScreenType> hoveredTab = findHoveredTab(leftPos, topPos, imageHeight, mouseX, mouseY, tabs);
        return hoveredTab.filter(armorStandScreenType -> openTabScreen(screen, armorStandScreenType, true)).isPresent();
    }

    private static <T extends Screen & ArmorStandScreen> boolean openTabScreen(T screen, ArmorStandScreenType screenType, boolean clickSound) {
        if (screenType != screen.getScreenType()) {
            Minecraft minecraft = ClientCoreServices.SCREENS.getMinecraft(screen);
            if (clickSound) {
                SimpleSoundInstance sound = SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F);
                minecraft.getSoundManager().play(sound);
            }
            minecraft.setScreen(screen.createScreenType(screenType));
            return true;
        }
        return false;
    }

    private static ArmorStandScreenType cycleTabs(ArmorStandScreenType currentScreenType, ArmorStandScreenType[] screenTypes, boolean backwards) {
        int index = ArrayUtils.indexOf(screenTypes, currentScreenType);
        return screenTypes[((backwards ? --index : ++index) % screenTypes.length + screenTypes.length) % screenTypes.length];
    }

    public static <T extends Screen & ArmorStandScreen> void drawTabs(PoseStack poseStack, int leftPos, int topPos, int imageHeight, T screen, ArmorStandScreenType[] tabs) {
        int tabsStartY = getTabsStartY(imageHeight, tabs.length);
        for (int i = 0; i < tabs.length; i++) {
            ArmorStandScreenType tabType = tabs[i];
            int tabX = leftPos - 32;
            int tabY = topPos + tabsStartY + 27 * i;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, getArmorStandBackgroundLocation());
            GuiComponent.blit(poseStack, tabX, tabY, tabY <= topPos ? 36 : tabY >= topPos + imageHeight - 36 ? 72 : 0, 188 + (tabType == screen.getScreenType() ? 0 : 26), 36, 26, 256, 256);
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
    public void removed() {
        for (GuiEventListener child : this.children()) {
            if (child instanceof UnboundedSliderButton sliderButton) {
                sliderButton.clearDirty();
            }
        }
    }

    @Override
    public void onClose() {
        if (this.holder instanceof AbstractContainerMenu) {
            this.minecraft.player.closeContainer();
        }
        super.onClose();
    }
}
