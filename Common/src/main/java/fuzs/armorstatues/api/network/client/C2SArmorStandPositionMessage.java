package fuzs.armorstatues.api.network.client;

import fuzs.armorstatues.api.ArmorStatuesApi;
import fuzs.armorstatues.api.world.inventory.ArmorStandMenu;
import fuzs.puzzleslib.network.Message;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;

public class C2SArmorStandPositionMessage implements Message<C2SArmorStandPositionMessage> {
    private double posX;
    private double posY;
    private double posZ;

    public C2SArmorStandPositionMessage() {

    }

    public C2SArmorStandPositionMessage(double posX, double posY, double posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeDouble(this.posX);
        buf.writeDouble(this.posY);
        buf.writeDouble(this.posZ);
    }

    @Override
    public void read(FriendlyByteBuf buf) {
        this.posX = buf.readDouble();
        this.posY = buf.readDouble();
        this.posZ = buf.readDouble();
    }

    @Override
    public MessageHandler<C2SArmorStandPositionMessage> makeHandler() {
        return new MessageHandler<>() {

            @Override
            public void handle(C2SArmorStandPositionMessage message, Player player, Object gameInstance) {
                if (player.containerMenu instanceof ArmorStandMenu menu && menu.stillValid(player)) {
                    if (!tryMoveArmorStandTo(menu.getArmorStand(), message.posX, message.posY, message.posZ)) {
                        ArmorStatuesApi.LOGGER.warn("Player {} attempted to move armor stand further than allowed", player);
                    }
                }
            }
        };
    }

    private static boolean tryMoveArmorStandTo(ArmorStand armorStand, double posX, double posY, double posZ) {
        if (!testDistance(armorStand, posX, posY, posZ)) return false;
        armorStand.moveTo(posX, posY, posZ);
        return true;
    }

    private static boolean testDistance(ArmorStand armorStand, double posX, double posY, double posZ) {
        return armorStand.distanceToSqr(posX, posY, posZ) < 64.0;
    }
}
