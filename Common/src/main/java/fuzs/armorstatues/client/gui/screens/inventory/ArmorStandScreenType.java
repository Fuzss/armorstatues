package fuzs.armorstatues.client.gui.screens.inventory;

import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import fuzs.puzzleslib.client.init.builder.ModScreenConstructor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class ArmorStandScreenType<T extends Screen & MenuAccess<ArmorStandMenu> & ArmorStandScreen> {
    public static final ArmorStandScreenType<ArmorStandEquipmentScreen> EQUIPMENT = new ArmorStandScreenType<>("equipment", new ItemStack(Items.IRON_CHESTPLATE), ArmorStandEquipmentScreen::new);
    public static final ArmorStandScreenType<ArmorStandRotationsScreen> ROTATIONS = new ArmorStandScreenType<>("rotations", new ItemStack(Items.COMPASS), ArmorStandRotationsScreen::new);
    public static final ArmorStandScreenType<ArmorStandStyleScreen> STYLE = new ArmorStandScreenType<>("style", new ItemStack(Items.PAINTING), ArmorStandStyleScreen::new);
    public static final ArmorStandScreenType<ArmorStandPosesScreen> POSES = new ArmorStandScreenType<>("poses", new ItemStack(Items.SPYGLASS), ArmorStandPosesScreen::new);
    public static final ArmorStandScreenType<ArmorStandPositionScreen> POSITION = new ArmorStandScreenType<>("position", new ItemStack(Items.GRASS_BLOCK), ArmorStandPositionScreen::new);

    private static ArmorStandScreenType<?> lastType = STYLE;

    private final String name;
    private final ItemStack icon;
    private final ModScreenConstructor<ArmorStandMenu, T> factory;

    private ArmorStandScreenType(String name, ItemStack icon, ModScreenConstructor<ArmorStandMenu, T> factory) {
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

    public T createTabScreen(ArmorStandMenu menu, Inventory inventory, Component component) {
        lastType = this;
        T screen = this.factory.create(menu, inventory, component);
        if (screen.getScreenType() != this) throw new IllegalStateException("Armor stand screen type mismatch: %s and %s".formatted(screen.getScreenType(), this));
        return screen;
    }

    public static ArmorStandScreenType<?> getLastType() {
        return lastType;
    }

    public static ArmorStandScreenType<?>[] values() {
        return (ArmorStandScreenType<?>[]) new ArmorStandScreenType[]{STYLE, ROTATIONS, POSES, POSITION, EQUIPMENT};
    }
}
