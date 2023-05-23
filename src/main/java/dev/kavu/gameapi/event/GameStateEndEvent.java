package dev.kavu.gameapi.event;

import dev.kavu.gameapi.game.GameState;
import dev.kavu.gameapi.game.GameType;
import org.bukkit.event.HandlerList;

public class GameStateEndEvent extends GameStateEvent{

    private static final HandlerList handlers = new HandlerList();

    private final GameState nextState;

    public GameStateEndEvent(GameType gameType, GameState gameState, GameState nextState, boolean scheduled) {
        super(gameType, gameState, scheduled);
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
