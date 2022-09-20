package fuzs.armorstatues.client.gui.screens.armorstand.data;

import fuzs.armorstatues.client.gui.screens.armorstand.*;
import fuzs.armorstatues.network.client.data.DataSyncHandler;
import fuzs.armorstatues.world.inventory.ArmorStandHolder;
import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class ArmorStandScreenType {
    public static final ArmorStandScreenType EQUIPMENT = new ArmorStandScreenType("equipment", new ItemStack(Items.IRON_CHESTPLATE), ArmorStandEquipmentScreen::new);
    public static final ArmorStandScreenType ROTATIONS = new ArmorStandScreenType("rotations", new ItemStack(Items.COMPASS), ArmorStandRotationsScreen::new);
    public static final ArmorStandScreenType STYLE = new ArmorStandScreenType("style", new ItemStack(Items.PAINTING), ArmorStandStyleScreen::new);
    public static final ArmorStandScreenType POSES = new ArmorStandScreenType("poses", new ItemStack(Items.SPYGLASS), ArmorStandPosesScreen::new);
    public static final ArmorStandScreenType POSITION = new ArmorStandScreenType("position", new ItemStack(Items.GRASS_BLOCK), ArmorStandPositionScreen::new);
    public static final ArmorStandScreenType ALIGNMENTS = new ArmorStandScreenType("alignments", new ItemStack(Items.DIAMOND_PICKAXE), ArmorStandAlignmentsScreen::new);

    private final String name;
    private final ItemStack icon;
    private final Factory<?> factory;

    private ArmorStandScreenType(String name, ItemStack icon, Factory<?> factory) {
        this.name = name;
        this.icon = icon;
        this.factory = factory;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public Component getComponent() {
        return Component.translatable("armorstatues.screen.type." + this.name);
    }

    public ItemStack getIcon() {
        return this.icon;
    }

    public <T extends Screen & MenuAccess<ArmorStandMenu> & ArmorStandScreen> T createScreenType(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        dataSyncHandler.setLastType(this);
        T screen = (T) this.factory.create(holder, inventory, component, dataSyncHandler);
        if (screen.getScreenType() != this) throw new IllegalStateException("Armor stand screen type mismatch: %s and %s".formatted(screen.getScreenType(), this));
        return screen;
    }

    public boolean requiresServer() {
        return this == EQUIPMENT;
    }

    public static <T extends Screen & MenuAccess<ArmorStandMenu> & ArmorStandScreen> T createLastScreenType(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        return dataSyncHandler.getLastType().orElse(STYLE).createScreenType(holder, inventory, component, dataSyncHandler);
    }

    public static ArmorStandScreenType[] values() {
        return new ArmorStandScreenType[]{STYLE, ROTATIONS, POSES, POSITION, ALIGNMENTS, EQUIPMENT};
    }

    @FunctionalInterface
    public interface Factory<T extends Screen & MenuAccess<ArmorStandMenu> & ArmorStandScreen> {

        T create(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler);
    }
}
