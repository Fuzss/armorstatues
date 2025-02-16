package fuzs.armorstatues.api.proxy;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;

public class ClientProxy extends ServerProxy {
    public static final String OPEN_SCREEN_TRANSLATION_KEY = Items.ARMOR_STAND.getDescriptionId() + ".description";

    @Override
    public Component getStatueHoverText() {
        Minecraft minecraft = Minecraft.getInstance();
        Component shiftComponent = Component.empty().append(minecraft.options.keyShift.getTranslatedKeyMessage()).withStyle(ChatFormatting.LIGHT_PURPLE);
        Component useComponent = Component.empty().append(minecraft.options.keyUse.getTranslatedKeyMessage()).withStyle(ChatFormatting.LIGHT_PURPLE);
        return Component.translatable(OPEN_SCREEN_TRANSLATION_KEY, shiftComponent, useComponent).withStyle(ChatFormatting.GRAY);
    }
}
