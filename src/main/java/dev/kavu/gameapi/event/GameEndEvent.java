package dev.kavu.gameapi.event;

import dev.kavu.gameapi.game.GameType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class GameEndEvent extends GameEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final int playerCount;
    private boolean cancelled;

    public GameEndEvent(GameType gameType, int playerCount) {
        super(gameType);
        this.playerCount = playerCount;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
