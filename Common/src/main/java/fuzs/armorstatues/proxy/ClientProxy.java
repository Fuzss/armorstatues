package fuzs.armorstatues.proxy;

import fuzs.armorstatues.api.client.gui.screens.armorstand.ArmorStandScreenFactory;
import fuzs.armorstatues.api.network.client.data.CommandDataSyncHandler;
import fuzs.armorstatues.api.world.inventory.ArmorStandHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;

public class ClientProxy extends ServerProxy {

    @Override
    public void openArmorStandScreen(ArmorStand armorStand, Player player) {
        Screen screen = ArmorStandScreenFactory.createLastScreenType(ArmorStandHolder.simple(armorStand), player.getInventory(), armorStand.getDisplayName(), new CommandDataSyncHandler(armorStand));
        Minecraft.getInstance().setScreen(screen);
    }
}
