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

public class RegisteredStatistic<T extends Number> {

    // Fields
    private final Statistic<T> statistic;
    private final HashMap<UUID, T> members = new HashMap<>();
    private final Plugin plugin;


    // Constructors
    public RegisteredStatistic(Statistic<T> statistic, Plugin plugin){
        Validate.notNull(statistic, "statistic cannot be null");
        Validate.notNull(plugin, "plugin cannot be null");

        this.statistic = statistic;
        this.plugin = plugin;
        for(Trigger<?> t : statistic.getTriggers()){
            plugin.getServer().getPluginManager().registerEvent(t.getEventClass(), new Listener() { }, EventPriority.NORMAL, (listener, event) -> onEventCall(event), plugin);
        }
    }

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
    public Statistic<T> getStatistic() {
        return statistic;
    }

    public HashMap<UUID, T> getMembers() {
        return members;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    // Functionality
    public void addMember(UUID member){
        Validate.notNull(member, "member cannot be null");

        members.putIfAbsent(member, statistic.getDefault());
    }

    public void trigger(UUID member){
        Validate.notNull(member, "member cannot be null");

        if(checkConditions(member)) {
            plugin.getServer().getPluginManager().callEvent(new StatisticTriggerEvent(this, member, members.get(member)));
        }
    }

    public boolean exec(Function<T, T> function) {
        Validate.notNull(function, "function cannot be null");

        if(members.isEmpty()) return false;

        boolean result = true;

        for (Map.Entry<UUID, T> e : members.entrySet()){
            result = execFor(e.getKey(), function) && result;
        }

        return result;
    }

    public boolean execFor(UUID member, Function<T, T> function) {
        Validate.notNull(member, "member cannot be null");
        Validate.notNull(function, "function cannot be null");

        if(members.isEmpty()) return false;

        if(function == null) throw new NullPointerException();

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
