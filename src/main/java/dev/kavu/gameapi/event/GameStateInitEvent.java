package dev.kavu.gameapi.event;

import dev.kavu.gameapi.GameState;
import dev.kavu.gameapi.GameStateTimer;
import dev.kavu.gameapi.GameType;
import org.bukkit.event.HandlerList;

public class GameStateInitEvent extends GameStateTimerEvent {

    private static final HandlerList handlers = new HandlerList();

    private final GameState previousState;
    private final boolean scheduled;

    public GameStateInitEvent(GameType gameType, GameStateTimer timer, GameState gameState, GameState previousState, boolean scheduled) {
        super(gameType, timer, gameState);
        this.previousState = previousState;
        this.scheduled = scheduled;
    }

    public GameState getPreviousState() {
        return previousState;
    }

    public boolean isScheduled() {
        return scheduled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }
}
