package dev.kavu.gameapi.statistic;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;

public class StatisticWatcher<T extends Number> {

    // Fields
    private final Statistic<T> statistic;
    private final HashMap<UUID, T> members = new HashMap<>();
    private final HashSet<Class<? extends PlayerEvent>> triggers = new HashSet<>();

    private final Listener listener = new Listener() {
        @EventHandler
        public void onPlayerEvent(PlayerEvent playerEvent){
            if(!triggers.contains(playerEvent.getClass())) return;
            if(!checkConditions()) return;

            execFor(playerEvent.getPlayer().getUniqueId(), statistic::onTrigger);
        }
    };

    // Constructor
    public StatisticWatcher(Statistic<T> statistic){
        this.statistic = statistic;
    }

    // Getters
    public Statistic<T> getStatistic() {
        return statistic;
    }

    public HashMap<UUID, T> getMembers() {
        return members;
    }

    // Functionality
    public void addMember(UUID uuid){
        members.putIfAbsent(uuid, statistic.getDefault());
    }

    public void addEventAsTrigger(Class<? extends PlayerEvent> event){
        triggers.add(event);
    }

    public void trigger(UUID uuid){
        if(!checkConditions()) return;
        execFor(uuid, statistic::onTrigger);
    }

    public boolean exec(Function<T, T> function) {

        if(members.isEmpty()) return false;

        boolean result = true;

        for (Map.Entry<UUID, T> e : members.entrySet()){
            result = execFor(e.getKey(), function) && result;
        }

        return result;
    }

    public boolean execFor(UUID member, Function<T, T> function) {

        if(statistic.isLocked()) return false;
        if(members.isEmpty()) return false;

        if(function == null) throw new NullPointerException();

        return members.replace(member, function.apply(members.get(member))) == null;
    }

    private boolean checkConditions() throws IllegalArgumentException{
        boolean result = true;
        boolean alternativeResult = false;

        for(Method method : statistic.getClass().getDeclaredMethods()){
            if(method.isAnnotationPresent(Condition.class)){
                Condition condition = method.getAnnotation(Condition.class);

                method.setAccessible(true);

                try {
                    if(!condition.alternative()) {
                        result = ((boolean) method.invoke(statistic) == !condition.negate()) && result;
                    } else {
                        alternativeResult = ((boolean) method.invoke(statistic) && !condition.negate()) || alternativeResult;
                    }
                } catch (IllegalAccessException | InvocationTargetException ignored) {
                    return false;
                }
            }
        }
        return result || alternativeResult;
    }
}
