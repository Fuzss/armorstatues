package fuzs.armorstatues.proxy;

import fuzs.armorstatues.client.gui.screens.inventory.ArmorStandScreenType;
import fuzs.armorstatues.network.client.data.CommandDataSyncHandler;
import fuzs.armorstatues.world.inventory.ArmorStandHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;

public class ClientProxy implements Proxy {

    @Override
    public void openArmorStandScreen(ArmorStand armorStand, Player player) {
        Screen screen = ArmorStandScreenType.createLastScreenType(ArmorStandHolder.simple(armorStand), player.getInventory(), armorStand.getDisplayName(), new CommandDataSyncHandler(armorStand));
        Minecraft.getInstance().setScreen(screen);
    }
}
