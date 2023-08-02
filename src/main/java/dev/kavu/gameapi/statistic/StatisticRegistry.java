package dev.kavu.gameapi.statistic;

import org.apache.commons.lang.Validate;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

/**
 * This class stores all statistics for the game.
 */
public class StatisticRegistry {

    private final Plugin plugin;
    private final HashMap<Class<? extends Statistic>, RegisteredStatistic<?>> statistics = new HashMap<>();

    /**
     * Creates new instance of <tt>StatisticRegistry</tt> class for specified plugin.
     * @param plugin Plugin to create new registry for
     */
    public StatisticRegistry(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * @return Plugin this registry is done for
     */
    public Plugin getPlugin() {
        return plugin;
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
     * @return If registry exists, <tt>RegisteredStatistic</tt> object of this statistic, otherwise {@code null}
     */
    public <N extends Number, E extends Statistic<N>> RegisteredStatistic<N> getRegisteredStatistic(Class<E> clazz){
        Validate.notNull(clazz, "clazz cannot be null");

        return (RegisteredStatistic<N>) statistics.get(clazz);
    }
}
