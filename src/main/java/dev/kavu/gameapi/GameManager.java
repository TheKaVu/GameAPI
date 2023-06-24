package dev.kavu.gameapi;

import dev.kavu.gameapi.statistic.Statistic;
import dev.kavu.gameapi.statistic.RegisteredStatistic;
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

    private final HashMap<Class<? extends Statistic>, RegisteredStatistic<?>> statistics = new HashMap<>();

    private final RuleSet rules = new RuleSet();

    private final MapManager mapManager;

    // Constructors

    /**
     * Creates the new instance of {@code GameManager} class in the most minimalistic way, basing just on the {@link Plugin} object.
     * @param plugin Plugin this manager runs for
     */
    public GameManager(Plugin plugin) {
        Validate.notNull(plugin, "plugin cannot be null");

        this.plugin = plugin;
        gameStateTimer = new GameStateTimer(plugin);
        mapManager = new MapManager();
    }

    /**
     * Creates the new instance of {@code GameManager} class with the custom {@link GameStateTimer}.
     * @param plugin Plugin this manager runs for
     * @param gameStateTimer Custom dedicated timer
     */
    public GameManager(Plugin plugin, GameStateTimer gameStateTimer) {
        Validate.notNull(plugin, "plugin cannot be null");
        Validate.notNull(gameStateTimer, "gameStateTimer cannot be null");

        this.plugin = plugin;
        this.gameStateTimer = gameStateTimer;
        mapManager = new MapManager();
    }

    /**
     * Creates the new instance of {@code GameManager} class with the custom {@link MapManager}. Although, it is instantiated with no-args constructor, thus it is the option for its subclasses.
     * @param plugin Plugin this manager runs for
     * @param mapManager Custom dedicated map manager
     */
    public GameManager(Plugin plugin, MapManager mapManager) {
        Validate.notNull(plugin, "plugin cannot be null");
        Validate.notNull(mapManager, "mapManager cannot be null");

        this.plugin = plugin;
        gameStateTimer = new GameStateTimer(plugin);
        this.mapManager = mapManager;
    }

    /**
     * Creates the new instance of {@code GameManager} class with initial collection of players. Passed collection contains players considered as taking part in this game.
     * The following collection will be mapped to the {@code HashSet&lt;UUID&gt;}, where {@link UUID} object is the uuid of the player.
     * @param plugin Plugin this manager runs for
     * @param players Players playing this game
     */
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

    /**
     * Creates the new instance of {@code GameManager} class in the most explicit way, as allows a lot of customization.It accepts custom {@link GameStateTimer}, {@link MapManager} and the initial players' collection.
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

    /**
     * Registers the specified statistic by mapping its class to automatically created {@link RegisteredStatistic} object. Same statistic <b>cannot</b> be registered more than once. Future attempts will have no effect.
     * @param statistic Statistic base represented by {@link Statistic} object
     */
    public void registerStatistic(Statistic<?> statistic){
        Validate.notNull(statistic, "statistic cannot be null");

        statistics.putIfAbsent(statistic.getClass(), new RegisteredStatistic<>(statistic, plugin));
    }

    /**
     * Registers the specified statistic by mapping its class to automatically created {@link RegisteredStatistic} object. Same statistic <b>cannot</b> be registered more than once. Future attempts will have no effect.
     * @param statistic Statistic base represented by {@link Statistic} object
     * @param initialMembers Initial collection of members for this statistic
     */
    public void registerStatistic(Statistic<?> statistic, Collection<UUID> initialMembers){
        Validate.notNull(statistic, "statistic cannot be null");

        statistics.putIfAbsent(statistic.getClass(), new RegisteredStatistic<>(statistic, initialMembers, plugin));
    }

    /**
     * Returns the registered statistic by its class represented by {@link RegisteredStatistic} object.
     * @param clazz Class of desired statistic
     * @param <N> Numeric type; class or subclass of {@link Number}
     * @param <E> Statistic type; class or subclass of {@link Statistic}
     * @return If registry exists, {@code RegisteredStatistic} object of this statistic, otherwise {@code null}
     */
    public <N extends Number, E extends Statistic<N>> RegisteredStatistic<N> getRegisteredStatistic(Class<E> clazz){
        Validate.notNull(clazz, "clazz cannot be null");

        return (RegisteredStatistic<N>) statistics.get(clazz);
    }
}
