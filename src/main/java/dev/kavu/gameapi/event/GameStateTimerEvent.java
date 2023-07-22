package dev.kavu.gameapi.event;

import dev.kavu.gameapi.GameState;
import dev.kavu.gameapi.GameStateTimer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Represents a game state timer event
 */
public class GameStateTimerEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final GameStateTimer timer;
    private final GameState gameState;

    public GameStateTimerEvent(GameStateTimer timer, GameState gameState) {
        this.timer = timer;
        this.gameState = gameState;
    }

    /**
     * @return {@link GameStateTimer} involved in this event
     */
    public GameStateTimer getTimer() {
        return timer;
    }

    /**
     * @return {@link GameState} this event relates to
     */
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }
}
