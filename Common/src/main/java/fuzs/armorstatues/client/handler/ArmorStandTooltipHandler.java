package fuzs.armorstatues.client.handler;

import fuzs.armorstatues.api.proxy.Proxy;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ArmorStandTooltipHandler {

    public static void onItemTooltip(ItemStack stack, TooltipFlag context, List<Component> lines) {
        if (stack.is(Items.ARMOR_STAND)) {
            Component component = Proxy.INSTANCE.getStatueHoverText();
            if (context.isAdvanced()) {
                lines.add(lines.size() - (stack.hasTag() ? 2 : 1), component);
            } else {
                lines.add(component);
            }
        }
    }
}
