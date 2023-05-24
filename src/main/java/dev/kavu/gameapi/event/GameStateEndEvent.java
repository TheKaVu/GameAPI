package dev.kavu.gameapi.event;

import dev.kavu.gameapi.GameState;
import dev.kavu.gameapi.GameStateTimer;
import dev.kavu.gameapi.GameType;
import org.bukkit.event.HandlerList;

public class GameStateEndEvent extends GameStateTimerEvent {

    private static final HandlerList handlers = new HandlerList();

    private final GameState nextState;
    private final boolean natural;

    public GameStateEndEvent(GameStateTimer timer, GameState gameState, GameState nextState, boolean natural) {
        super(timer, gameState);
        this.nextState = nextState;
        this.natural = natural;
    }

    public GameState getNextState() {
        return nextState;
    }

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
