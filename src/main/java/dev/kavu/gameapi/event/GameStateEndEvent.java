package dev.kavu.gameapi.event;

import dev.kavu.gameapi.GameState;
import dev.kavu.gameapi.GameStateTimer;
import dev.kavu.gameapi.GameType;
import org.bukkit.event.HandlerList;

public class GameStateEndEvent extends GameStateTimerEvent {

    private static final HandlerList handlers = new HandlerList();

    private final GameState nextState;

    public GameStateEndEvent(GameStateTimer timer, GameState gameState, GameState nextState) {
        super(timer, gameState);
        this.nextState = nextState;
    }

    public GameState getNextState() {
        return nextState;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }
}
