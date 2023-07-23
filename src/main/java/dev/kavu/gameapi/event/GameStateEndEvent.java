package dev.kavu.gameapi.event;

import dev.kavu.gameapi.GameState;
import dev.kavu.gameapi.GameStateTimer;
import org.bukkit.event.HandlerList;

/**
 * Represents a game state end event.
 */
public class GameStateEndEvent extends GameStateTimerEvent {

    private static final HandlerList handlers = new HandlerList();

    private final GameState nextState;
    private final boolean natural;

    public GameStateEndEvent(GameStateTimer timer, GameState gameState, GameState nextState, boolean natural) {
        super(timer, gameState);
        this.nextState = nextState;
        this.natural = natural;
    }

    /**
     * @return Next {@link GameState} if scheduled; {@code null} if not
     */
    public GameState getNextState() {
        return nextState;
    }

    /**
     * @return Was the following {@link GameState} terminated automatically or by hand
     */
    public boolean isNatural() {
        return natural;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }
}
