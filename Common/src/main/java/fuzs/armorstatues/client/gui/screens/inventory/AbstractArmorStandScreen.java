package fuzs.armorstatues.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public abstract class AbstractArmorStandScreen extends Screen implements MenuAccess<ArmorStandMenu>, ArmorStandScreen {
    private static final ResourceLocation ARMOR_STAND_BACKGROUND_LOCATION = new ResourceLocation(ArmorStatues.MOD_ID, "textures/gui/container/armor_stand/background.png");
    public static final ResourceLocation ARMOR_STAND_WIDGETS_LOCATION = new ResourceLocation(ArmorStatues.MOD_ID, "textures/gui/container/armor_stand/widgets.png");

    protected final ArmorStandMenu menu;
    private final Inventory inventory;
    protected final int imageWidth = 210;
    protected final int imageHeight = 188;
    protected int leftPos;
    protected int topPos;

    public AbstractArmorStandScreen(ArmorStandMenu menu, Inventory inventory, Component component) {
        super(component);
        this.menu = menu;
        this.inventory = inventory;
    }

    public void openTabScreen(ArmorStandScreenType<?> screenType) {
        // TODO save settings/send to server
        this.minecraft.setScreen(screenType.create(this.menu, this.inventory, this.title));
    }

    @Override
    protected void init() {
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = this.height / 4;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(poseStack);
        this.renderBg(poseStack, partialTick, mouseX, mouseY);
        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ARMOR_STAND_BACKGROUND_LOCATION);
        this.blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
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
