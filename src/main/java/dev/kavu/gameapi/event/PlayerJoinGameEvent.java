package dev.kavu.gameapi.event;

import dev.kavu.gameapi.GameType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import java.util.Set;

public class PlayerJoinGameEvent extends PlayerGameEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final Set<Player> players;
    private boolean cancelled;

    public PlayerJoinGameEvent(GameType gameType, Player player, Set<Player> players) {
        super(gameType, player);
        this.players = players;
    }

    public Set<Player> getPlayers() {
        return players;
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
