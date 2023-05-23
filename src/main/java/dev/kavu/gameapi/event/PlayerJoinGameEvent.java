package dev.kavu.gameapi.event;

import dev.kavu.gameapi.game.GameType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class PlayerJoinGameEvent extends PlayerGameEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final int playerCount;
    private boolean cancelled;

    public PlayerJoinGameEvent(GameType gameType, Player player, int playerCount) {
        super(gameType, player);
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
