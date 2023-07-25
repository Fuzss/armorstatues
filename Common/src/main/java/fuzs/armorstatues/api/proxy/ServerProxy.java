package fuzs.armorstatues.api.proxy;

import net.minecraft.network.chat.Component;

public class ServerProxy implements Proxy {

    @Override
    public Component getStatueHoverText() {
        return Component.empty();
    }
}
