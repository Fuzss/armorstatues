package fuzs.armorstatues.network.client;

import fuzs.armorstatues.client.gui.screens.inventory.ArmorStandStyleOption;
import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import fuzs.puzzleslib.network.Message;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class C2SArmorStandStyleMessage implements Message<C2SArmorStandStyleMessage> {
    private ArmorStandStyleOption styleOption;
    private boolean setting;

    public C2SArmorStandStyleMessage() {

    }

    public C2SArmorStandStyleMessage(ArmorStandStyleOption styleOption, boolean setting) {
        this.styleOption = styleOption;
        this.setting = setting;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeEnum(this.styleOption);
        buf.writeBoolean(this.setting);
    }

    @Override
    public void read(FriendlyByteBuf buf) {
        this.styleOption = buf.readEnum(ArmorStandStyleOption.class);
        this.setting = buf.readBoolean();
    }

    @Override
    public MessageHandler<C2SArmorStandStyleMessage> makeHandler() {
        return new MessageHandler<>() {

            @Override
            public void handle(C2SArmorStandStyleMessage message, Player player, Object gameInstance) {
                if (player.containerMenu instanceof ArmorStandMenu menu && menu.stillValid(player)) {
                    message.styleOption.setOption(menu.getArmorStand(), message.setting);
                }
            }
        };
    }
}
