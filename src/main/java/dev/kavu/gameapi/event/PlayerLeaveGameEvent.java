package dev.kavu.gameapi.event;

import dev.kavu.gameapi.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.Set;
/**
 * Represents player leave game event.
 */
public class PlayerLeaveGameEvent extends PlayerGameEvent {

    private static final HandlerList handlers = new HandlerList();

    private final Set<Player> players;

    public PlayerLeaveGameEvent(Game game, Player player, Set<Player> players) {
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
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
