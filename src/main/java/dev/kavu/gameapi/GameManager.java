package dev.kavu.gameapi;

import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.UUID;

public class GameManager<T extends GameType> {

    // Fields
    private final Plugin plugin;

    private final T gametType;

    private final GameStateTimer gameStateTimer = new GameStateTimer();

    private final HashSet<UUID> playersInGame = new HashSet<>();

    private final HashSet<UUID> playersOffGame = new HashSet<>();

    // Constructor
    public GameManager(Plugin plugin, T gametType) {
        this.plugin = plugin;
        this.gametType = gametType;
    }

    // Getters
    public Plugin getPlugin() {
        return plugin;
    }

    public T getGametType() {
        return gametType;
    }

    public GameStateTimer getGameStateTimer() {
        return gameStateTimer;
    }

    public HashSet<UUID> getPlayersInGame() {
        return playersInGame;
    }

    public HashSet<UUID> getPlayersOffGame() {
        return playersOffGame;
    }
}
