package dev.kavu.gameapi.event;

import dev.kavu.gameapi.game.GameState;
import dev.kavu.gameapi.game.GameType;
import org.bukkit.event.HandlerList;

public class GameStateInitEvent extends GameStateEvent{

    private static final HandlerList handlers = new HandlerList();

    private final GameState previousState;

    public GameStateInitEvent(GameType gameType, GameState gameState, GameState previousState, boolean scheduled) {
        super(gameType, gameState, scheduled);
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
