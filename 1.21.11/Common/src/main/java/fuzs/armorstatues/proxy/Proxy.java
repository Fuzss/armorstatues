package fuzs.armorstatues.proxy;

import fuzs.puzzleslib.api.core.v1.ModLoaderEnvironment;
import fuzs.statuemenus.api.v1.world.entity.decoration.StatueEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public interface Proxy {
    Proxy INSTANCE = ModLoaderEnvironment.INSTANCE.isClient() ? new ClientProxy() : new ServerProxy();

    void openStatueScreen(LivingEntity livingEntity, StatueEntity statueEntity, Player player);
}
