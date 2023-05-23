package dev.kavu.gameapi.event;

import dev.kavu.gameapi.game.GameType;
import org.bukkit.entity.Player;

public abstract class PlayerGameEvent extends GameEvent{

    private final Player player;

    public PlayerGameEvent(GameType gameType, Player player) {
        super(gameType);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
