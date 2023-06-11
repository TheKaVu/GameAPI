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

    private final HashSet<UUID> playersInGame = new HashSet<>();

    private final HashSet<UUID> playersOffGame = new HashSet<>();

    private final HashMap<Class<? extends Statistic>, RegisteredStatistic<?>> statistics = new HashMap<>();

    private final RuleSet rules = new RuleSet();

    private final MapManager mapManager;

    // Constructor
    public GameManager(Plugin plugin) {
        Validate.notNull(plugin, "plugin cannot be null");

        this.plugin = plugin;
        gameStateTimer = new GameStateTimer(plugin);
        mapManager = new MapManager(plugin.getDataFolder());
    }

    public GameManager(Plugin plugin, MapManager mapManager) {
        Validate.notNull(plugin, "plugin cannot be null");
        Validate.notNull(plugin, "mapManager cannot be null");

        this.plugin = plugin;
        gameStateTimer = new GameStateTimer(plugin);
        this.mapManager = mapManager;
    }

    public GameManager(Plugin plugin, Collection<? extends Player> playersInGame) {
        Validate.notNull(plugin, "plugin cannot be null");
        Validate.notNull(plugin, "playersInGame cannot be null");

        this.plugin = plugin;
        gameStateTimer = new GameStateTimer(plugin);
        mapManager = new MapManager(plugin.getDataFolder());
        for(Player p : playersInGame){
            this.playersInGame.add(p.getUniqueId());
        }
    }

    public GameManager(Plugin plugin, MapManager mapManager, Collection<? extends Player> playersInGame) {
        Validate.notNull(plugin, "plugin cannot be null");
        Validate.notNull(plugin, "mapManager cannot be null");
        Validate.notNull(plugin, "playersInGame cannot be null");

        this.plugin = plugin;
        gameStateTimer = new GameStateTimer(plugin);
        this.mapManager = mapManager;
        for(Player p : playersInGame){
            this.playersInGame.add(p.getUniqueId());
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

    public HashSet<UUID> getPlayersInGame() {
        return playersInGame;
    }

    public HashSet<UUID> getPlayersOffGame() {
        return playersOffGame;
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
