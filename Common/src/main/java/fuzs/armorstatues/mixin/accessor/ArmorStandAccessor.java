package fuzs.armorstatues.mixin.accessor;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ArmorStand.class)
public interface ArmorStandAccessor {

    @Accessor
    NonNullList<ItemStack> getHandItems();

    @Accessor
    NonNullList<ItemStack> getArmorItems();

    @Accessor
    void setDisabledSlots(int disabledSlots);

    @Invoker
    void callReadPose(CompoundTag compound);
}
