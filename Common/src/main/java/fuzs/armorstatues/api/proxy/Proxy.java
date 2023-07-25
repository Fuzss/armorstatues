package fuzs.armorstatues.api.proxy;

import fuzs.puzzleslib.core.DistTypeExecutor;
import net.minecraft.network.chat.Component;

public interface Proxy {
    @SuppressWarnings("Convert2MethodRef")
    Proxy INSTANCE = DistTypeExecutor.getForDistType(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    Component getStatueHoverText();
}
