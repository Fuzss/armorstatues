package fuzs.armorstatues.client.gui.screens.inventory;

import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import fuzs.puzzleslib.client.init.builder.ModScreenConstructor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public final class ArmorStandScreenType<T extends Screen & MenuAccess<ArmorStandMenu> & ArmorStandScreen> {
    public static final ArmorStandScreenType<ArmorStandEquipmentScreen> EQUIPMENT = new ArmorStandScreenType<>(0, "equipment", ArmorStandEquipmentScreen::new);
    public static final ArmorStandScreenType<ArmorStandEquipmentScreen> ROTATIONS = new ArmorStandScreenType<>(1, "rotations", ArmorStandEquipmentScreen::new);
    public static final ArmorStandScreenType<ArmorStandStyleScreen> STYLE = new ArmorStandScreenType<>(2, "style", ArmorStandStyleScreen::new);
    public static final ArmorStandScreenType<ArmorStandEquipmentScreen> POSES = new ArmorStandScreenType<>(3, "poses", ArmorStandEquipmentScreen::new);
    public static final ArmorStandScreenType<ArmorStandEquipmentScreen> POSITION = new ArmorStandScreenType<>(4, "position", ArmorStandEquipmentScreen::new);
    private static final ArmorStandScreenType<?>[] VALUES = new ArmorStandScreenType[]{EQUIPMENT, ROTATIONS, STYLE, POSES, POSITION};

    private static ArmorStandScreenType<?> lastType = EQUIPMENT;

    private final int ordinal;
    private final String name;
    private final ModScreenConstructor<ArmorStandMenu, T> factory;

    private ArmorStandScreenType(int ordinal, String name, ModScreenConstructor<ArmorStandMenu, T> factory) {
        this.ordinal = ordinal;
        this.name = name;
        this.factory = factory;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int ordinal() {
        return this.ordinal;
    }

    public T create(ArmorStandMenu menu, Inventory inventory, Component component) {
        lastType = this;
        T screen = this.factory.create(menu, inventory, component);
        if (screen.getScreenType() != this) throw new IllegalStateException("Armor stand screen type mismatch: %s and %s".formatted(screen.getScreenType(), this));
        return screen;
    }

    public static ArmorStandScreenType<?>[] values() {
        return VALUES;
    }

    public static ArmorStandScreenType<?> getLastType() {
        return lastType;
    }
}
