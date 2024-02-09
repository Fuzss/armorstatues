package fuzs.armorstatues.client.handler;

import fuzs.puzzlesapi.api.statues.v1.helper.ArmorStandInteractHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ArmorStandTooltipHandler {

    public static void onItemTooltip(ItemStack stack, @Nullable Player player, List<Component> lines, TooltipFlag context) {
        if (stack.is(Items.ARMOR_STAND)) {
            Component component = ArmorStandInteractHelper.getArmorStandHoverText();
            if (context.isAdvanced()) {
                lines.add(lines.size() - (stack.hasTag() ? 2 : 1), component);
            } else {
                lines.add(component);
            }
        }
    }
}
