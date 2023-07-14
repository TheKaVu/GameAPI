package dev.kavu.gameapi.statistic;

import dev.kavu.gameapi.event.StatisticTriggerEvent;
import org.apache.commons.lang.Validate;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;

/**
 * Representation of registered {@link Statistic} containing values mapped to members of following statistic. Statistic registered using {@link dev.kavu.gameapi.GameManager GameManager}
 * will create an object of this class and associate it with registered statistic.
 * @param <T> Numeric type of wrapped statistic
 */
public class RegisteredStatistic<T extends Number> {

    // Fields
    private final Statistic<T> statistic;
    private final HashMap<UUID, T> members = new HashMap<>();
    private final Plugin plugin;


    // Constructors

    /**
     * Creates new instance of <tt>RegisteredStatistic</tt> with base of specified statistic.
     * @param statistic Registered statistic
     * @param plugin Plugin the statistic is registered for
     */
    public RegisteredStatistic(Statistic<T> statistic, Plugin plugin){
        Validate.notNull(statistic, "statistic cannot be null");
        Validate.notNull(plugin, "plugin cannot be null");

        this.statistic = statistic;
        this.plugin = plugin;
        for(Trigger<?> t : statistic.getTriggers()){
            plugin.getServer().getPluginManager().registerEvent(t.getEventClass(), new Listener() { }, EventPriority.NORMAL, (listener, event) -> onEventCall(event), plugin);
        }
    }

    /**
     * Creates new instance of <tt>RegisteredStatistic</tt> with base of specified statistic with initial members.
     * @param statistic Registered statistic
     * @param initialMembers Collection if members that will be instantly added to the statistic members
     * @param plugin Plugin the statistic is registered for
     */
    public RegisteredStatistic(Statistic<T> statistic, Collection<UUID> initialMembers, Plugin plugin){
        Validate.notNull(statistic, "statistic cannot be null");
        Validate.notNull(initialMembers, "initialMembers cannot be null");
        Validate.notNull(plugin, "plugin cannot be null");

        this.statistic = statistic;
        this.plugin = plugin;
        for(UUID member : initialMembers){
            addMember(member);
        }
        for(Trigger<?> t : statistic.getTriggers()){
            plugin.getServer().getPluginManager().registerEvent(t.getEventClass(), new Listener() { }, EventPriority.NORMAL, (listener, event) -> onEventCall(event), plugin);
        }
    }

    // Getters

    /**
     * @return Registered statistic
     */
    public Statistic<T> getStatistic() {
        return statistic;
    }

    /**
     * @return Map of members represented by {@link UUID} objects associated with their values
     */
    public HashMap<UUID, T> getMembers() {
        return members;
    }

    /**
     * @return Plugin the statistic is registered for
     */
    public Plugin getPlugin() {
        return plugin;
    }

    // Functionality

    /**
     * Adds member to the statistic if it is not yet present.
     * @param member Member to be added represented by {@link UUID} object
     */
    public void addMember(UUID member){
        Validate.notNull(member, "member cannot be null");

        members.putIfAbsent(member, statistic.getDefault());
    }

    /**
     * Triggers this statistic if its possible thus calls {@link StatisticTriggerEvent} with no parent {@link Trigger} object.
     * @param member Member the statistic will be triggered for
     */
    public void trigger(UUID member){
        Validate.notNull(member, "member cannot be null");

        if(checkConditions(member)) {
            plugin.getServer().getPluginManager().callEvent(new StatisticTriggerEvent(this, member, members.get(member)));
        }
    }

    /**
     * Executes the function for all members of the statistic.
     * @param function Function to be called
     * @return {@code true} if all actions were done, {@code false} otherwise
     */
    public boolean exec(Function<T, T> function) {
        Validate.notNull(function, "function cannot be null");

        if(members.isEmpty()) return false;

        boolean result = true;

        for (Map.Entry<UUID, T> e : members.entrySet()){
            result = execFor(e.getKey(), function) && result;
        }

        return result;
    }

    /**
     * Executes the function for specific member of the statistic.
     * @param member Member the function fill be called for, represented by {@link UUID} object
     * @param function Function to be called
     * @return {@code true} if the action was done, {@code false} otherwise
     */
    public boolean execFor(UUID member, Function<T, T> function) {
        Validate.notNull(member, "member cannot be null");
        Validate.notNull(function, "function cannot be null");

        if(members.isEmpty()) return false;

        return members.replace(member, function.apply(members.get(member))) == null;
    }

    private boolean checkConditions(UUID targetMember) {
        boolean result = true;
        boolean alternativeResult = false;

        for(Method method : statistic.getClass().getDeclaredMethods()){

            if(!method.isAnnotationPresent(Condition.class)) continue;
            if(!method.getReturnType().equals(boolean.class))
            if(method.getParameterCount() > 1) continue;
            method.setAccessible(true);

            Condition condition = method.getAnnotation(Condition.class);

            boolean invokeResult = false;
            try {
                if (method.getParameterCount() == 0) {
                    invokeResult = (boolean) method.invoke(statistic);
                } else {
                    invokeResult = (boolean) method.invoke(statistic, targetMember);
                }
            } catch (InvocationTargetException | IllegalAccessException ignored) {
            }

            if(!condition.alternative()) {
                result = (invokeResult == !condition.negate()) && result;
            } else {
                alternativeResult = (invokeResult && !condition.negate()) || alternativeResult;
            }
        }
        return result || alternativeResult;
    }

    private void onEventCall(Event event){
        for(Trigger<?> trigger : statistic.getTriggers()){
            Class<? extends Event> clazz = trigger.getEventClass();
            if(clazz.equals(event.getClass())){
                if(!trigger.validate(clazz.cast(event))) continue;
                UUID uuid = trigger.compute(clazz.cast(event));
                if(checkConditions(uuid)) {
                    execFor(uuid, trigger.getResponse());
                    plugin.getServer().getPluginManager().callEvent(new StatisticTriggerEvent(this, uuid, members.get(uuid), event));
                    return;
                }
            }
        }
    }
}
