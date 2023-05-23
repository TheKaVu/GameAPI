package dev.kavu.gameapi.event;

import dev.kavu.gameapi.game.GameType;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.Set;

public class PlayerLeaveGameEvent extends PlayerGameEvent {

    private static final HandlerList handlers = new HandlerList();

    private final Set<Player> players;

    public PlayerLeaveGameEvent(GameType gameType, Player player, Set<Player> players) {
        super(gameType, player);
        this.players = players;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
