package fuzs.armorstatues.mixin.accessor;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ArmorStand.class)
public interface ArmorStandAccessor {

    @Accessor
    NonNullList<ItemStack> getHandItems();

    @Invoker("getDimensionsMarker")
    EntityDimensions getArmorstandDimensions(boolean marker);

    @Accessor
    NonNullList<ItemStack> getArmorItems();

    @Accessor
    int getDisabledSlots();

    @Accessor
    void setDisabledSlots(int disabledSlots);

    @Invoker
    void callReadPose(CompoundTag compound);

    @Invoker
    void callBrokenByAnything(DamageSource damageSource);

    @Invoker
    void callCauseDamage(DamageSource damageSource, float amount);
}
