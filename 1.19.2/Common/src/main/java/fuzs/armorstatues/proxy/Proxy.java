package fuzs.armorstatues.proxy;

import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;

public interface Proxy {

    void openArmorStandScreen(ArmorStand armorStand, Player player);
}
