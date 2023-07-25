package fuzs.armorstatues.api.proxy;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ClientProxy extends ServerProxy {

    @Override
    public Component getStatueHoverText() {
        Minecraft minecraft = Minecraft.getInstance();
        Component shiftComponent = Component.empty().append(minecraft.options.keyShift.getTranslatedKeyMessage()).withStyle(ChatFormatting.LIGHT_PURPLE);
        Component useComponent = Component.empty().append(minecraft.options.keyUse.getTranslatedKeyMessage()).withStyle(ChatFormatting.LIGHT_PURPLE);
        return Component.translatable("armorstatues.item.armor_stand.description", shiftComponent, useComponent).withStyle(ChatFormatting.GRAY);
    }
}
