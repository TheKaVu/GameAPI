package dev.kavu.gameapi;

import dev.kavu.gameapi.statistic.Statistic;
import dev.kavu.gameapi.statistic.RegisteredStatistic;
import dev.kavu.gameapi.world.MapManager;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class GameManager {

    // Fields
    private final Plugin plugin;

    private final GameStateTimer gameStateTimer;

    private final HashSet<UUID> players = new HashSet<>();

    private final HashMap<Class<? extends Statistic>, RegisteredStatistic<?>> statistics = new HashMap<>();

    private final RuleSet rules = new RuleSet();

    private final MapManager mapManager;

    // Constructor
    public GameManager(Plugin plugin) {
        Validate.notNull(plugin, "plugin cannot be null");

        this.plugin = plugin;
        gameStateTimer = new GameStateTimer(plugin);
        mapManager = new MapManager();
    }

    public GameManager(Plugin plugin, GameStateTimer gameStateTimer) {
        Validate.notNull(plugin, "plugin cannot be null");
        Validate.notNull(gameStateTimer, "gameStateTimer cannot be null");

        this.plugin = plugin;
        this.gameStateTimer = gameStateTimer;
        mapManager = new MapManager();
    }

    public GameManager(Plugin plugin, MapManager mapManager) {
        Validate.notNull(plugin, "plugin cannot be null");
        Validate.notNull(mapManager, "mapManager cannot be null");

        this.plugin = plugin;
        gameStateTimer = new GameStateTimer(plugin);
        this.mapManager = mapManager;
    }

    public GameManager(Plugin plugin, Collection<? extends Player> players) {
        Validate.notNull(plugin, "plugin cannot be null");
        Validate.notNull(players, "players cannot be null");

        this.plugin = plugin;
        gameStateTimer = new GameStateTimer(plugin);
        mapManager = new MapManager();
        for(Player p : players){
            this.players.add(p.getUniqueId());
        }
    }

    public GameManager(Plugin plugin, GameStateTimer gameStateTimer, MapManager mapManager, Collection<? extends Player> players) {
        Validate.notNull(plugin, "plugin cannot be null");
        Validate.notNull(gameStateTimer, "gameStateTimer cannot be null");
        Validate.notNull(mapManager, "mapManager cannot be null");
        Validate.notNull(players, "players cannot be null");

        this.plugin = plugin;
        this.gameStateTimer = gameStateTimer;
        this.mapManager = mapManager;
        for(Player p : players){
            this.players.add(p.getUniqueId());
        }
    }

    // Getters
    public Plugin getPlugin() {
        return plugin;
    }

    public GameStateTimer getGameStateTimer() {
        return gameStateTimer;
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public HashSet<UUID> getPlayers() {
        return players;
    }

    public RuleSet getRules() {
        return rules;
    }

    public <N extends Number> void registerStatistic(Statistic<N> statistic){
        Validate.notNull(statistic, "statistic cannot be null");

        statistics.put(statistic.getClass(), new RegisteredStatistic<>(statistic, plugin));
    }

    public <N extends Number> void registerStatistic(Statistic<N> statistic, Collection<UUID> initialMembers){
        Validate.notNull(statistic, "statistic cannot be null");

        statistics.put(statistic.getClass(), new RegisteredStatistic<>(statistic, initialMembers, plugin));
    }

    public <N extends Number, E extends Statistic<N>> RegisteredStatistic<N> getRegisteredStatistic(Class<E> clazz){
        Validate.notNull(clazz, "clazz cannot be null");

        return (RegisteredStatistic<N>) statistics.get(clazz);
    }
}
