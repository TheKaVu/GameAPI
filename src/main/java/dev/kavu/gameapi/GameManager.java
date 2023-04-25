package dev.kavu.gameapi;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.function.Supplier;

public class GameManager<T extends GameType> {

    // Fields
    private final Plugin plugin;

    private final T gameType;

    private final GameStateTimer gameStateTimer = new GameStateTimer();

    private final HashSet<UUID> playersInGame = new HashSet<>();

    private final HashSet<UUID> playersOffGame = new HashSet<>();

    private final HashMap<String, Statistic<?>> statistics = new HashMap<>();

    private final MapManager mapManager;

    // Constructor
    public GameManager(Plugin plugin, T gameType) {
        this.plugin = plugin;
        this.gameType = gameType;
        mapManager = new MapManager(plugin.getDataFolder());
    }

    public GameManager(Plugin plugin, T gameType, MapManager mapManager) {
        this.plugin = plugin;
        this.gameType = gameType;
        this.mapManager = mapManager;
    }

    public GameManager(Plugin plugin, T gameType, Supplier<Collection<Player>> inGamePlayers) {
        this.plugin = plugin;
        this.gameType = gameType;
        mapManager = new MapManager(plugin.getDataFolder());
        for(Player p : inGamePlayers.get()){
            playersInGame.add(p.getUniqueId());
        }
    }

    public GameManager(Plugin plugin, T gameType, MapManager mapManager, Supplier<Collection<Player>> inGamePlayers) {
        this.plugin = plugin;
        this.gameType = gameType;
        this.mapManager = mapManager;
        for(Player p : inGamePlayers.get()){
            playersInGame.add(p.getUniqueId());
        }
    }

    // Getters
    public Plugin getPlugin() {
        return plugin;
    }

    public T getGameType() {
        return gameType;
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
