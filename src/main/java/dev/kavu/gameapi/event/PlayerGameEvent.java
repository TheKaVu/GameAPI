package dev.kavu.gameapi.event;

import dev.kavu.gameapi.Game;
import org.bukkit.entity.Player;

/**
 * Represents a player game event.
 */
public abstract class PlayerGameEvent extends GameEvent{

    private final Player player;

    public PlayerGameEvent(Game game, Player player) {
        super(game);
        this.player = player;
    }

    /**
     * @return Player involved in this event
     */
    public Player getPlayer() {
        return player;
    }
}
