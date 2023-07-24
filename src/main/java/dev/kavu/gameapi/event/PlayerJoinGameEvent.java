package dev.kavu.gameapi.event;

import dev.kavu.gameapi.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import java.util.Set;

/**
 * Represents player join game event.
 */
public class PlayerJoinGameEvent extends PlayerGameEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final Set<Player> players;
    private boolean cancelled;

    public PlayerJoinGameEvent(Game game, Player player, Set<Player> players) {
        super(game, player);
        this.players = players;
    }

    /**
     * @return Set of players present in the game
     */
    public Set<Player> getPlayers() {
        return players;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
