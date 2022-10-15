package fuzs.armorstatues.api.event.entity.player;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;

public class PlayerEvents {
    public static final Event<LoggedIn> LOGIN = EventFactory.createArrayBacked(LoggedIn.class, listeners -> (Player player) -> {
        for (LoggedIn event : listeners) {
            event.onPlayerLoggedIn(player);
        }
    });
    public static final Event<LoggedOut> LOGOUT = EventFactory.createArrayBacked(LoggedOut.class, listeners -> (Player player) -> {
        for (LoggedOut event : listeners) {
            event.onPlayerLoggedOut(player);
        }
    });

    @FunctionalInterface
    public interface LoggedIn {

        /**
         * fires when a new player logs in and is added to the {@link net.minecraft.server.players.PlayerList}
         * <p>fires after all data has been sent to the client, can therefore not be cancelled
         * <p>could possibly also use {@link net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents#JOIN},
         * but it is fired much earlier (and has a different purpose from signaling that the player has fully logged int)
         *
         * @param player the player that's logging in
         */
        void onPlayerLoggedIn(Player player);
    }

    @FunctionalInterface
    public interface LoggedOut {

        /**
         * fires when a player logs out and is aremoveddded to the {@link net.minecraft.server.players.PlayerList}
         *
         * @param player the player that's logging out
         */
        void onPlayerLoggedOut(Player player);
    }
}
