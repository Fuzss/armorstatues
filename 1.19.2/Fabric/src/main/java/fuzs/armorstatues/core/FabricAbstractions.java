package fuzs.armorstatues.core;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;

public final class FabricAbstractions implements CommonAbstractions {

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot slot, Entity entity) {
        return slot == Mob.getEquipmentSlotForItem(stack);
    }
}
