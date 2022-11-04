package fuzs.armorstatues.client.handler;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ArmorStandTooltipHandler {

    public static void onItemTooltip(ItemStack stack, TooltipFlag context, List<Component> lines) {
        if (stack.is(Items.ARMOR_STAND)) {
            Component component = new TranslatableComponent("armorstatues.item.armor_stand.description").withStyle(ChatFormatting.GRAY);
            if (context.isAdvanced()) {
                lines.add(lines.size() - 1, component);
            } else {
                lines.add(component);
            }
        }
    }
}
