package fuzs.armorstatues.core;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public final class ForgeAbstractions implements CommonAbstractions {

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot slot, Entity entity) {
        return stack.canEquip(slot, entity);
    }
}
