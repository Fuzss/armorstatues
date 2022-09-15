package fuzs.armorstatues.core;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public interface CommonAbstractions {

    boolean canEquip(ItemStack stack, EquipmentSlot slot, Entity entity);
}
