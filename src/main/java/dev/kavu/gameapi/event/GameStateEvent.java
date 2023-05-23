package dev.kavu.gameapi.event;

import dev.kavu.gameapi.GameState;
import dev.kavu.gameapi.GameType;
import org.bukkit.event.HandlerList;

public class GameStateEvent extends GameEvent{

    private static final HandlerList handlers = new HandlerList();

    private final GameState gameState;
    private final boolean scheduled;

    public GameStateEvent(GameType gameType, GameState gameState, boolean scheduled) {
        super(gameType);
        this.gameState = gameState;
        this.scheduled = scheduled;
    }

    public GameState getGameState() {
        return gameState;
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
