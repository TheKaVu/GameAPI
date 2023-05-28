package dev.kavu.gameapi.event;

import dev.kavu.gameapi.GameState;
import dev.kavu.gameapi.GameStateTimer;
import dev.kavu.gameapi.GameType;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStateTimerEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final GameStateTimer timer;
    private final GameState gameState;

    public GameStateTimerEvent(GameStateTimer timer, GameState gameState) {
        this.timer = timer;
        this.gameState = gameState;
    }

    public GameStateTimer getTimer() {
        return timer;
    }

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
