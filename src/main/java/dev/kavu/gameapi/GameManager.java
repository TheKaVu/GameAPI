package dev.kavu.gameapi;

import dev.kavu.gameapi.world.GameMap;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class GameManager<T extends GameType> {

    // Fields
    private final Plugin plugin;

    private final T gametType;

    private final GameStateTimer gameStateTimer = new GameStateTimer();

    private final HashSet<UUID> playersInGame = new HashSet<>();

    private final HashSet<UUID> playersOffGame = new HashSet<>();

    private final HashMap<String, Statistic<?>> statistics = new HashMap<>();

    private final GameMap gameMap;

    // Constructor
    public GameManager(Plugin plugin, T gametType, GameMap gameMap) {
        this.plugin = plugin;
        this.gametType = gametType;
        this.gameMap = gameMap;
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

    public GameMap getGameMap() {
        return gameMap;
    }

    public HashSet<UUID> getPlayersInGame() {
        return playersInGame;
    }

    public HashSet<UUID> getPlayersOffGame() {
        return playersOffGame;
    }

    public HashMap<String, Statistic<?>> getStatistics(){
        return statistics;
    }
}
