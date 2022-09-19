package fuzs.armorstatues.network.client;

import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import fuzs.puzzleslib.network.Message;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;

public class C2SArmorStandRotationMessage implements Message<C2SArmorStandRotationMessage> {
    private float rotation;

    public C2SArmorStandRotationMessage() {

    }

    public C2SArmorStandRotationMessage(float rotation) {
        this.rotation = rotation;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeFloat(this.rotation);
    }

    @Override
    public void read(FriendlyByteBuf buf) {
        this.rotation = buf.readFloat();
    }

    @Override
    public MessageHandler<C2SArmorStandRotationMessage> makeHandler() {
        return new MessageHandler<>() {

            @Override
            public void handle(C2SArmorStandRotationMessage message, Player player, Object gameInstance) {
                if (player.containerMenu instanceof ArmorStandMenu menu && menu.stillValid(player)) {
                    ArmorStand armorStand = menu.getArmorStand();
                    armorStand.setYRot(message.rotation);
                    armorStand.setYBodyRot(message.rotation);
                }
            }
        };
    }
}
