package dev.kavu.gameapi;

import dev.kavu.gameapi.statistic.Statistic;
import dev.kavu.gameapi.statistic.StatisticRegistry;
import dev.kavu.gameapi.world.MapManager;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;

/**
 * This class contains all the game controls and dynamic data. Also, it stores the registry of statistics for specific game. Moreover, it keeps the rules of the game. <br/>
 * The {@code GameManager} class does not have specific functionality. It consists of smaller managers and controls and associates them together.
 *
 * @see GameStateTimer
 * @see MapManager
 * @see RuleSet
 * @see Statistic
 */
public class GameManager {

    // Fields

    private final Plugin plugin;

    private final GameStateTimer gameStateTimer;

    private final HashSet<UUID> players = new HashSet<>();

    private final StatisticRegistry statisticRegistry;

    private final RuleSet rules = new RuleSet();

    private final MapManager mapManager;

    // Constructors

    /**
     * Creates the new instance of <tt>GameManager</tt> class in the most minimalistic way, basing just on the {@link Plugin} object.
     * @param plugin Plugin this manager runs for
     */
    public GameManager(Plugin plugin) {
        Validate.notNull(plugin, "plugin cannot be null");

        this.plugin = plugin;
        statisticRegistry = new StatisticRegistry(plugin);
        gameStateTimer = new GameStateTimer(plugin);
        mapManager = new MapManager();
    }

    /**
     * Creates the new instance of <tt>GameManager</tt> class with the custom {@link GameStateTimer}.
     * @param plugin Plugin this manager runs for
     * @param gameStateTimer Custom dedicated <tt>GameStateTimer</tt>
     */
    public GameManager(Plugin plugin, GameStateTimer gameStateTimer) {
        Validate.notNull(plugin, "plugin cannot be null");
        Validate.notNull(gameStateTimer, "gameStateTimer cannot be null");

        this.plugin = plugin;
        statisticRegistry = new StatisticRegistry(plugin);
        this.gameStateTimer = gameStateTimer;
        mapManager = new MapManager();
    }

    /**
     * Creates the new instance of <tt>GameManager</tt> class with the custom {@link MapManager}. Although, it is instantiated with no-args constructor, thus it is the option for its subclasses.
     * @param plugin Plugin this manager runs for
     * @param mapManager Custom dedicated map manager
     */
    public GameManager(Plugin plugin, MapManager mapManager) {
        Validate.notNull(plugin, "plugin cannot be null");
        Validate.notNull(mapManager, "mapManager cannot be null");

        this.plugin = plugin;
        statisticRegistry = new StatisticRegistry(plugin);
        gameStateTimer = new GameStateTimer(plugin);
        this.mapManager = mapManager;
    }

    /**
     * Creates the new instance of <tt>GameManager</tt> class with initial collection of players. Passed collection contains players considered as taking part in this game.
     * The following collection will be mapped to the {@code HashSet&lt;UUID&gt;}, where {@link UUID} object is the uuid of the player.
     * @param plugin Plugin this manager runs for
     * @param players Players playing this game
     */
    public GameManager(Plugin plugin, Collection<? extends Player> players) {
        Validate.notNull(plugin, "plugin cannot be null");
        Validate.notNull(players, "players cannot be null");

        this.plugin = plugin;
        statisticRegistry = new StatisticRegistry(plugin);
        gameStateTimer = new GameStateTimer(plugin);
        mapManager = new MapManager();
        for(Player p : players){
            this.players.add(p.getUniqueId());
        }
    }

    /**
     * Creates the new instance of <tt>GameManager</tt> class in the most explicit way, as allows a lot of customization.It accepts custom {@link GameStateTimer}, {@link MapManager} and the initial players' collection.
     * The following collection will be mapped to the {@code HashSet&lt;UUID&gt;}, where {@link UUID} object is the uuid of the player.
     * @param plugin Plugin this manager runs for
     * @param gameStateTimer Custom dedicated timer
     * @param mapManager Custom dedicated map manager
     * @param players Players playing this game
     */
    public GameManager(Plugin plugin, GameStateTimer gameStateTimer, MapManager mapManager, Collection<? extends Player> players) {
        Validate.notNull(plugin, "plugin cannot be null");
        Validate.notNull(gameStateTimer, "gameStateTimer cannot be null");
        Validate.notNull(mapManager, "mapManager cannot be null");
        Validate.notNull(players, "players cannot be null");

        this.plugin = plugin;
        statisticRegistry = new StatisticRegistry(plugin);
        this.gameStateTimer = gameStateTimer;
        this.mapManager = mapManager;
        for(Player p : players){
            this.players.add(p.getUniqueId());
        }
    }

    // Getters

    /**
     * @return Plugin this manager runs for
     */
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * @return Statistic registry storing all statistics for this game
     */
    public StatisticRegistry getStatisticRegistry() {
        return statisticRegistry;
    }

    /**
     * @return Associated {@link GameStateTimer} object
     */
    public GameStateTimer getGameStateTimer() {
        return gameStateTimer;
    }

    /**
     * @return {@link MapManager} object controlling the map actions in this game
     */
    public MapManager getMapManager() {
        return mapManager;
    }

    /**
     * @return {@link MapManager} object controlling the map actions in this game
     */
    public HashSet<UUID> getPlayers() {
        return players;
    }

    /**
     * @return Rules of this game represented by {@link RuleSet} object
     */
    public RuleSet getRules() {
        return rules;
    }
}
