package fuzs.armorstatues.network;

import fuzs.armorstatues.handler.ArmorStandInteractHandler;
import fuzs.puzzleslib.api.network.v2.MessageV2;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class S2CPingMessage implements MessageV2<S2CPingMessage> {

    @Override
    public void write(FriendlyByteBuf buf) {

    }

    @Override
    public void read(FriendlyByteBuf buf) {

    }

    @Override
    public MessageHandler<S2CPingMessage> makeHandler() {
        return new MessageHandler<>() {

            @Override
            public void handle(S2CPingMessage message, Player player, Object gameInstance) {
                ArmorStandInteractHandler.setPresentServerside();
            }
        };
    }
}
