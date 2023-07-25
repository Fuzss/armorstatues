package fuzs.armorstatues.proxy;

import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.api.client.gui.screens.armorstand.ArmorStandScreenFactory;
import fuzs.armorstatues.api.network.client.data.CommandDataSyncHandler;
import fuzs.armorstatues.api.network.client.data.DataSyncHandler;
import fuzs.armorstatues.api.network.client.data.VanillaTweaksDataSyncHandler;
import fuzs.armorstatues.api.world.inventory.ArmorStandHolder;
import fuzs.armorstatues.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;

public class ClientProxy extends ServerProxy {

    @Override
    public void openArmorStandScreen(ArmorStand armorStand, Player player) {
        ArmorStandHolder holder = ArmorStandHolder.simple(armorStand);
        Screen screen = ArmorStandScreenFactory.createLastScreenType(holder, player.getInventory(), armorStand.getDisplayName(), createDataSyncHandler(armorStand, (LocalPlayer) player));
        Minecraft.getInstance().setScreen(screen);
    }

    private static DataSyncHandler createDataSyncHandler(ArmorStand armorStand, LocalPlayer player) {
        if (!player.hasPermissions(2) && ArmorStatues.CONFIG.get(ClientConfig.class).useVanillaTweaksTriggers) {
            return new VanillaTweaksDataSyncHandler(armorStand, player);
        } else {
            return new CommandDataSyncHandler(armorStand, player);
        }
    }
}
