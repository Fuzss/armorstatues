package fuzs.armorstatues.proxy;

import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.config.ClientConfig;
import fuzs.armorstatues.network.client.data.CommandDataSyncHandler;
import fuzs.armorstatues.network.client.data.VanillaTweaksDataSyncHandler;
import fuzs.statuemenus.api.v1.client.gui.screens.StatueScreenFactory;
import fuzs.statuemenus.api.v1.network.client.data.DataSyncHandler;
import fuzs.statuemenus.api.v1.world.entity.decoration.StatueEntity;
import fuzs.statuemenus.api.v1.world.inventory.StatueHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class ClientProxy extends ServerProxy {

    @Override
    public void openStatueScreen(LivingEntity livingEntity, StatueEntity statueEntity, Player player) {
        StatueHolder statueHolder = StatueHolder.simple(livingEntity, statueEntity);
        Screen screen = StatueScreenFactory.createLastScreenType(statueHolder,
                player.getInventory(),
                livingEntity.getDisplayName(),
                createDataSyncHandler(statueHolder, (LocalPlayer) player));
        Minecraft.getInstance().setScreen(screen);
    }

    private static DataSyncHandler createDataSyncHandler(StatueHolder holder, LocalPlayer player) {
        if ((!player.hasPermissions(2) || ArmorStatues.CONFIG.get(ClientConfig.class).overrideClientPermissionsCheck)
                && ArmorStatues.CONFIG.get(ClientConfig.class).useVanillaTweaksTriggers) {
            return new VanillaTweaksDataSyncHandler(holder, player);
        } else {
            return new CommandDataSyncHandler(holder, player);
        }
    }
}
