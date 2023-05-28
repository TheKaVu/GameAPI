package dev.kavu.gameapi.event;

import dev.kavu.gameapi.GameState;
import dev.kavu.gameapi.GameStateTimer;
import dev.kavu.gameapi.GameType;
import org.bukkit.event.HandlerList;

public class GameStateInitEvent extends GameStateTimerEvent {

    private static final HandlerList handlers = new HandlerList();

    private final GameState previousState;

    public GameStateInitEvent(GameStateTimer timer, GameState gameState, GameState previousState) {
        super(timer, gameState);
        this.previousState = previousState;
    }

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
