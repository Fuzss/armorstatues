package fuzs.armorstatues.network.client;

import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import fuzs.armorstatues.world.inventory.ArmorStandPose;
import fuzs.puzzleslib.network.Message;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class C2SArmorStandPoseMessage implements Message<C2SArmorStandPoseMessage> {
    private CompoundTag tag;

    public C2SArmorStandPoseMessage() {

    }

    public C2SArmorStandPoseMessage(ArmorStandPose pose) {
        this.tag = new CompoundTag();
        pose.serialize(this.tag);
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeNbt(this.tag);
    }

    @Override
    public void read(FriendlyByteBuf buf) {
        this.tag = buf.readNbt();
    }

    @Override
    public MessageHandler<C2SArmorStandPoseMessage> makeHandler() {
        return new MessageHandler<>() {

            @Override
            public void handle(C2SArmorStandPoseMessage message, Player player, Object gameInstance) {
                if (player.containerMenu instanceof ArmorStandMenu menu && menu.stillValid(player)) {
                    ArmorStandPose.applyTagToEntity(menu.getArmorStand(), message.tag);
                }
            }
        };
    }
}
