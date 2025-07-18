package fuzs.armorstatues.proxy;

import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;

public class ServerProxy implements Proxy {

    @Override
    public void openArmorStandScreen(ArmorStand armorStand, Player player) {
        // NO-OP
    }
}
