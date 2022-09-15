package fuzs.armorstatues.mixin.accessor;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ArmorStand.class)
public interface ArmorStandAccessor {

    @Accessor
    NonNullList<ItemStack> getHandItems();

    @Accessor
    NonNullList<ItemStack> getArmorItems();
}
