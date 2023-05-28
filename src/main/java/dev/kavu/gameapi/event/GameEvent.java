package dev.kavu.gameapi.event;

import dev.kavu.gameapi.GameType;
import org.bukkit.event.Event;

public abstract class GameEvent extends Event {

    private final GameType gameType;

    public GameEvent(GameType gameType) {
        this.gameType = gameType;
    }

    public GameType getGameType() {
        return gameType;
    }
}
