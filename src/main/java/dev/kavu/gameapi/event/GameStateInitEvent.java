package dev.kavu.gameapi.event;

import dev.kavu.gameapi.GameState;
import dev.kavu.gameapi.GameStateTimer;
import org.bukkit.event.HandlerList;

/**
 * Represents a game state initialization event.
 */
public class GameStateInitEvent extends GameStateTimerEvent {

    private static final HandlerList handlers = new HandlerList();

    private final GameState previousState;

    public GameStateInitEvent(GameStateTimer timer, GameState gameState, GameState previousState) {
        super(timer, gameState);
        this.previousState = previousState;
    }

    /**
     * @return {@link GameState} the new state was initialized over
     */
    public GameState getPreviousState() {
        return previousState;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }
}
