package fuzs.armorstatues.world.inventory.data;

import fuzs.armorstatues.ArmorStatues;
import fuzs.statuemenus.api.v1.world.inventory.data.StatueScreenType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class ArmorStandScreenTypes {
    public static final StatueScreenType POSITION = new StatueScreenType(ArmorStatues.id("position"),
            new ItemStack(Items.GRASS_BLOCK));
    public static final StatueScreenType ALIGNMENTS = new StatueScreenType(ArmorStatues.id("alignments"),
            new ItemStack(Items.DIAMOND_PICKAXE));
    public static final StatueScreenType VANILLA_TWEAKS = new StatueScreenType(ArmorStatues.id("vanilla_tweaks"),
            new ItemStack(Items.WRITTEN_BOOK));
    public static final List<StatueScreenType> TYPES = List.of(StatueScreenType.ROTATIONS,
            StatueScreenType.POSES,
            StatueScreenType.STYLE,
            POSITION,
            ALIGNMENTS,
            StatueScreenType.EQUIPMENT);
}
