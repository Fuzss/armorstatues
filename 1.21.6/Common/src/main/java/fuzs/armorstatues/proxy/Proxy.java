package fuzs.armorstatues.proxy;

import fuzs.puzzleslib.api.core.v1.ModLoaderEnvironment;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;

public interface Proxy {
    Proxy INSTANCE = ModLoaderEnvironment.INSTANCE.isClient() ? new ClientProxy() : new ServerProxy();

    void openArmorStandScreen(ArmorStand armorStand, Player player);
}
