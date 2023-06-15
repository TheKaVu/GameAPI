package dev.kavu.gameapi.event;

import dev.kavu.gameapi.Game;
import org.bukkit.entity.Player;

public abstract class PlayerGameEvent extends GameEvent{

    private final Player player;

    public PlayerGameEvent(Game game, Player player) {
        super(game);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
