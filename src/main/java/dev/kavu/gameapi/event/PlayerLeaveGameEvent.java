package dev.kavu.gameapi.event;

import dev.kavu.gameapi.game.GameType;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PlayerLeaveGameEvent extends PlayerGameEvent {

    private static final HandlerList handlers = new HandlerList();

    private final int playerCount;

    public PlayerLeaveGameEvent(GameType gameType, Player player, int playerCount) {
        super(gameType, player);
        this.playerCount = playerCount;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
