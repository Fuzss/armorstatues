package fuzs.armorstatues.network.client;

import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import fuzs.puzzleslib.network.Message;
import net.minecraft.SharedConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
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
                    setCustomNameArmorStand(message.name, menu.getArmorStand());
                }
            }
        };
    }

    public static void setCustomNameArmorStand(String name, ArmorStand armorStand) {
        String s = SharedConstants.filterText(name);
        if (s.length() <= 50) {
            boolean remove = s.isBlank() || s.equals(EntityType.ARMOR_STAND.getDescription().getString());
            armorStand.setCustomName(remove ? null : Component.literal(s));
        }
    }
}
