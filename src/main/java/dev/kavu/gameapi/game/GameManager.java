package dev.kavu.gameapi.game;

import dev.kavu.gameapi.statistic.Statistic;
import dev.kavu.gameapi.statistic.StatisticRegistry;
import dev.kavu.gameapi.world.MapManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class GameManager<T extends GameType> {

    // Fields
    private final Plugin plugin;

    private final T gameType;

    private final GameStateTimer gameStateTimer = new GameStateTimer();

    private final HashSet<UUID> playersInGame = new HashSet<>();

    private final HashSet<UUID> playersOffGame = new HashSet<>();

    private final HashMap<Class<? extends Statistic>, StatisticRegistry<?>> statistics = new HashMap<>();

    private final MapManager mapManager;

    // Constructor
    public GameManager(Plugin plugin, T gameType) {
        if(plugin == null){
            throw new NullPointerException("plugin was null");
        }
        if(gameType == null){
            throw new NullPointerException("gameType was null");
        }
        this.plugin = plugin;
        this.gameType = gameType;
        mapManager = new MapManager(plugin.getDataFolder());
    }

    public GameManager(Plugin plugin, T gameType, MapManager mapManager) {
        if(plugin == null){
            throw new NullPointerException("plugin was null");
        }
        if(gameType == null){
            throw new NullPointerException("gameType was null");
        }
        if(mapManager == null){
            throw new NullPointerException("mapManager was null");
        }
        this.plugin = plugin;
        this.gameType = gameType;
        this.mapManager = mapManager;
    }

    public GameManager(Plugin plugin, T gameType, Collection<? extends Player> playersInGame) {
        if(plugin == null){
            throw new NullPointerException("plugin was null");
        }
        if(gameType == null){
            throw new NullPointerException("gameType was null");
        }
        if(playersInGame == null){
            throw new NullPointerException("playersInGame was null");
        }
        this.plugin = plugin;
        this.gameType = gameType;
        mapManager = new MapManager(plugin.getDataFolder());
        for(Player p : playersInGame){
            this.playersInGame.add(p.getUniqueId());
        }
    }

    public GameManager(Plugin plugin, T gameType, MapManager mapManager, Collection<? extends Player> playersInGame) {
        if(plugin == null){
            throw new NullPointerException("plugin was null");
        }
        if(gameType == null){
            throw new NullPointerException("gameType was null");
        }
        if(mapManager == null){
            throw new NullPointerException("mapManager was null");
        }
        if(playersInGame == null){
            throw new NullPointerException("playersInGame was null");
        }
        this.plugin = plugin;
        this.gameType = gameType;
        this.mapManager = mapManager;
        for(Player p : playersInGame){
            this.playersInGame.add(p.getUniqueId());
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

    public <N extends Number> void addStatistic(Statistic<N> statistic){
        if(statistic == null){
            throw new NullPointerException();
        }
        statistics.put(statistic.getClass(), new StatisticRegistry<>(statistic, plugin));
    }

    public <E extends Statistic> E getStatistic(Class<E> clazz){
        if(clazz == null){
            throw new NullPointerException();
        }

        return clazz.cast(statistics.get(clazz));
    }
}
