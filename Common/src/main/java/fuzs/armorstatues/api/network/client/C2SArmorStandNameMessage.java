package fuzs.armorstatues.api.network.client;

import fuzs.puzzleslib.network.Message;
import fuzs.armorstatues.api.network.client.data.DataSyncHandler;
import fuzs.armorstatues.api.world.inventory.ArmorStandMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class C2SArmorStandNameMessage implements Message<C2SArmorStandNameMessage> {
    private String name;

    public C2SArmorStandNameMessage() {

    }

    public C2SArmorStandNameMessage(String name) {
        this.name = name;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(this.name);
    }

    @Override
    public void read(FriendlyByteBuf buf) {
        this.name = buf.readUtf();
    }

    @Override
    public MessageHandler<C2SArmorStandNameMessage> makeHandler() {
        return new MessageHandler<>() {

            @Override
            public void handle(C2SArmorStandNameMessage message, Player player, Object gameInstance) {
                if (player.containerMenu instanceof ArmorStandMenu menu && menu.stillValid(player)) {
                    DataSyncHandler.setCustomArmorStandName(menu.getArmorStand(), message.name);
                }
            }
        };
    }
}
