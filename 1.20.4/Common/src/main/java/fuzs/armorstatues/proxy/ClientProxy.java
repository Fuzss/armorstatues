package fuzs.armorstatues.proxy;

import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.config.ClientConfig;
import fuzs.armorstatues.init.ModRegistry;
import fuzs.armorstatues.network.client.data.CommandDataSyncHandler;
import fuzs.armorstatues.network.client.data.VanillaTweaksDataSyncHandler;
import fuzs.statuemenus.api.v1.client.gui.screens.ArmorStandScreenFactory;
import fuzs.statuemenus.api.v1.network.client.data.DataSyncHandler;
import fuzs.statuemenus.api.v1.world.entity.decoration.ArmorStandDataProvider;
import fuzs.statuemenus.api.v1.world.inventory.ArmorStandHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;

public class ClientProxy extends ServerProxy {

    @Override
    public void openArmorStandScreen(ArmorStand armorStand, Player player) {
        ArmorStandHolder holder = new ArmorStandHolder() {

            @Override
            public ArmorStand getArmorStand() {
                return armorStand;
            }

            @Override
            public ArmorStandDataProvider getDataProvider() {
                return ModRegistry.ARMOR_STAND_DATA_PROVIDER;
            }
        };
        Screen screen = ArmorStandScreenFactory.createLastScreenType(holder, player.getInventory(), armorStand.getDisplayName(), createDataSyncHandler(holder, (LocalPlayer) player));
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.setScreen(screen);
    }

    private static DataSyncHandler createDataSyncHandler(ArmorStandHolder holder, LocalPlayer player) {
        if (!player.hasPermissions(2) && ArmorStatues.CONFIG.get(ClientConfig.class).useVanillaTweaksTriggers) {
            return new VanillaTweaksDataSyncHandler(holder, player);
        } else {
            return new CommandDataSyncHandler(holder, player);
        }
    }
}
