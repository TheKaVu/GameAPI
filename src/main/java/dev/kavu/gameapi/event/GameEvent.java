package dev.kavu.gameapi.event;

import dev.kavu.gameapi.Game;
import org.bukkit.event.Event;

/**
 * Represents a game event.
 */
public abstract class GameEvent extends Event {

    private final Game game;

    public GameEvent(Game game) {
        this.game = game;
    }

    /**
     * @return Game this event happened in
     */
    public Game getGame() {
        return game;
    }
}
