package dev.kavu.gameapi;

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

    private final MapManager mapManager;

    // Constructor
    public GameManager(Plugin plugin, T gametType) {
        this.plugin = plugin;
        this.gametType = gametType;
        mapManager = new MapManager(plugin.getDataFolder());
    }

    public GameManager(Plugin plugin, T gametType, MapManager mapManager) {
        this.plugin = plugin;
        this.gametType = gametType;
        this.mapManager = mapManager;
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

    public MapManager getMapManager() {
        return mapManager;
    }

    public HashSet<UUID> getPlayersInGame() {
        return playersInGame;
    }

    public HashSet<UUID> getPlayersOffGame() {
        return playersOffGame;
    }

    public void addStatistic(String name, Statistic<?> statistic){
        statistics.put(name, statistic);
    }

    @SuppressWarnings("unchecked")
    public <E extends Statistic<?>> HashMap<String, E> getStatistics(Class<E> statisticClass){
        HashMap<String, E> statisticSubmap = new HashMap<>();

        statistics.forEach((k, v) -> {
            if(v.getClass().equals(statisticClass)){
                try {
                    E e = (E) v;
                    statisticSubmap.put(k, e);
                } catch (ClassCastException ignored){ }
            }
        });

        return statisticSubmap;
    }
}
