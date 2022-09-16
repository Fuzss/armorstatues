package fuzs.armorstatues.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import fuzs.puzzleslib.client.core.ClientCoreServices;
import net.minecraft.client.gui.screens.Screen;
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

    protected final ArmorStandMenu menu;
    private final Inventory inventory;
    protected final int imageWidth = 210;
    protected final int imageHeight = 188;
    protected int leftPos;
    protected int topPos;
    protected int mouseX;
    protected int mouseY;

    public AbstractArmorStandScreen(ArmorStandMenu menu, Inventory inventory, Component component) {
        super(component);
        this.menu = menu;
        this.inventory = inventory;
    }

    @Override
    public Screen createTabScreen(ArmorStandScreenType<?> screenType) {
        return screenType.createTabScreen(this.menu, this.inventory, this.title);
    }

    @Override
    protected void init() {
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            if (handleTabClicked((int) mouseX, (int) mouseY, this.leftPos, this.topPos, this.imageHeight, this)) {
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public static  <T extends Screen & ArmorStandScreen> boolean handleTabClicked(int mouseX, int mouseY, int leftPos, int topPos, int imageHeight, T screen) {
        Optional<ArmorStandScreenType<?>> hoveredTab = findHoveredTab(leftPos, topPos, imageHeight, mouseX, mouseY);
        if (hoveredTab.isPresent() && hoveredTab.get() != screen.getScreenType()) {
            ClientCoreServices.SCREENS.getMinecraft(screen).getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            ClientCoreServices.SCREENS.getMinecraft(screen).setScreen(screen.createTabScreen(hoveredTab.get()));
            return true;
        }
        return false;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(poseStack);
        this.renderBg(poseStack, partialTick, mouseX, mouseY);
        super.render(poseStack, mouseX, mouseY, partialTick);
        if (this.menu.getCarried().isEmpty()) {
            findHoveredTab(this.leftPos, this.topPos, this.imageHeight, mouseX, mouseY).ifPresent(hoveredTab -> {
                this.renderTooltip(poseStack, hoveredTab.getComponent(), mouseX, mouseY);
            });
        }
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ARMOR_STAND_BACKGROUND_LOCATION);
        this.blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        drawTabs(poseStack, this.leftPos, this.topPos, this.imageHeight, this);
    }

    public static <T extends Screen & ArmorStandScreen> void drawTabs(PoseStack poseStack, int leftPos, int topPos, int imageHeight, T screen) {
        int tabsStartY = getTabsStartY(imageHeight);
        for (int i = 0; i < ArmorStandScreenType.values().length; i++) {
            ArmorStandScreenType<?> tabType = ArmorStandScreenType.values()[i];
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

    public static Optional<ArmorStandScreenType<?>> findHoveredTab(int leftPos, int topPos, int imageHeight, int mouseX, int mouseY) {
        int tabsStartY = getTabsStartY(imageHeight);
        for (int i = 0; i < ArmorStandScreenType.values().length; i++) {
            int tabX = leftPos - 32;
            int tabY = topPos + tabsStartY + 27 * i;
            if (mouseX > tabX && mouseX <= tabX + 32 && mouseY > tabY && mouseY <= tabY + 26) {
                return Optional.of(ArmorStandScreenType.values()[i]);
            }
        }
        return Optional.empty();
    }

    private static int getTabsStartY(int imageHeight) {
        int tabsHeight = ArmorStandScreenType.values().length * 26 + (ArmorStandScreenType.values().length - 1);
        return (imageHeight - tabsHeight) / 2;
    }

    @Override
    public ArmorStandMenu getMenu() {
        return this.menu;
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
