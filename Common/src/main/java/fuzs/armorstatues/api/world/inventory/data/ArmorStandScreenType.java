package fuzs.armorstatues.api.world.inventory.data;

import fuzs.armorstatues.api.ArmorStatuesApi;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Locale;

public class ArmorStandScreenType {
    public static final ArmorStandScreenType EQUIPMENT = new ArmorStandScreenType("equipment", new ItemStack(Items.IRON_CHESTPLATE), true);
    public static final ArmorStandScreenType ROTATIONS = new ArmorStandScreenType("rotations", new ItemStack(Items.COMPASS));
    public static final ArmorStandScreenType STYLE = new ArmorStandScreenType("style", new ItemStack(Items.PAINTING));
    public static final ArmorStandScreenType POSES = new ArmorStandScreenType("poses", new ItemStack(Items.SPYGLASS));
    public static final ArmorStandScreenType POSITION = new ArmorStandScreenType("position", new ItemStack(Items.GRASS_BLOCK));

    private final String name;
    private final ItemStack icon;
    private final boolean requiresServer;

    public ArmorStandScreenType(String name, ItemStack icon) {
        this(name, icon, false);
    }

    public ArmorStandScreenType(String name, ItemStack icon, boolean requiresServer) {
        this.name = name;
        this.icon = icon;
        this.requiresServer = requiresServer;
    }

    @Override
    public String toString() {
        return this.name.toUpperCase(Locale.ROOT);
    }

    public String getTranslationKey() {
        return ArmorStatuesApi.MOD_ID + ".screen.type." + this.name;
    }

    public ItemStack getIcon() {
        return this.icon;
    }

    public boolean requiresServer() {
        return this.requiresServer;
    }
}
