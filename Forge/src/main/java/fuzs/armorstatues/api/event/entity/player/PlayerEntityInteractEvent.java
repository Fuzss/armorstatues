package fuzs.armorstatues.api.event.entity.player;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Cancelable;

import java.util.Objects;

/**
 * a copy of {@link PlayerInteractEvent.EntityInteractSpecific}, which is broken server-side unfortunately on Forge for cancelling interactions
 * <p>we use this copy to fix cancelling server-side interactions
 * <p>we don't just manually fire Forge's event at the correct time to avoid messing with other mods, and since we would also have to cancel Forge's current invocation of the event
 * <p>this event is only fired server-side as it's only broken there, for the client-side refer to the original event (only catch client-side invocations using {@link PlayerInteractEvent.EntityInteractSpecific#getSide()})
 */
@Cancelable
public class PlayerEntityInteractEvent extends PlayerEvent {
    private final InteractionHand hand;
    private final Entity target;
    private final Vec3 hitVector;
    private InteractionResult cancellationResult = InteractionResult.PASS;

    public PlayerEntityInteractEvent(Player player, InteractionHand hand, Entity target, Vec3 hitVector) {
        super(Objects.requireNonNull(player, "Null player in PlayerInteractEvent!"));
        this.hand = Objects.requireNonNull(hand, "Null hand in PlayerInteractEvent!");
        this.target = Objects.requireNonNull(target, "Null target in PlayerInteractEvent!");
        this.hitVector = Objects.requireNonNull(hitVector, "Null hitVector in PlayerInteractEvent!");
    }

    public InteractionHand getHand() {
        return this.hand;
    }

    public Entity getTarget() {
        return this.target;
    }

    public Vec3 getHitVector() {
        return this.hitVector;
    }

    public ItemStack getItemStack()
    {
        return this.getEntity().getItemInHand(this.hand);
    }

    public Level getLevel()
    {
        return this.getEntity().level;
    }

    public InteractionResult getCancellationResult() {
        return this.cancellationResult;
    }

    public void setCancellationResult(InteractionResult cancellationResult) {
        this.cancellationResult = cancellationResult;
    }
}
