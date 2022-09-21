package fuzs.armorstatues.network.client;

import fuzs.armorstatues.world.entity.decoration.PlayerStatue;
import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import fuzs.puzzleslib.network.Message;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;

public class C2SPlayerStateModelPartMessage implements Message<C2SPlayerStateModelPartMessage> {
    private PlayerModelPart modelPart;
    private boolean value;

    public C2SPlayerStateModelPartMessage() {

    }

    public C2SPlayerStateModelPartMessage(PlayerModelPart modelPart, boolean value) {
        this.modelPart = modelPart;
        this.value = value;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeEnum(this.modelPart);
        buf.writeBoolean(this.value);
    }

    @Override
    public void read(FriendlyByteBuf buf) {
        this.modelPart = buf.readEnum(PlayerModelPart.class);
        this.value = buf.readBoolean();
    }

    @Override
    public MessageHandler<C2SPlayerStateModelPartMessage> makeHandler() {
        return new MessageHandler<>() {

            @Override
            public void handle(C2SPlayerStateModelPartMessage message, Player player, Object gameInstance) {
                if (player.containerMenu instanceof ArmorStandMenu menu && menu.stillValid(player)) {
                    if (menu.getArmorStand() instanceof PlayerStatue playerStatue) {
                        playerStatue.setModelPart(message.modelPart, message.value);
                    }
                }
            }
        };
    }
}
