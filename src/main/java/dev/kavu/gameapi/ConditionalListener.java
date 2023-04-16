package dev.kavu.gameapi;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;
import java.util.function.Predicate;

public class ConditionalListener implements Listener {

    // Fields
    private final Listener listener;

    private final Predicate<Plugin> condition;

    private final Plugin plugin;

    // Constructor
    public ConditionalListener(Predicate<Plugin> condition, Listener handledListener, Plugin plugin) {
        this.condition = condition;
        this.listener = handledListener;
        this.plugin = plugin;
    }

    // Getters
    public Listener getListener() {
        return listener;
    }

    public Predicate<Plugin> getCondition() {
        return condition;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    // Functionality
    public final void onEvent(Event event){
        for(Method method : listener.getClass().getDeclaredMethods()){

            if(method.getParameterCount() != 1) return;
            if(!condition.test(plugin)) return;

            try {
                method.invoke(listener, event);
            } catch (Exception ignored) {
            }
        }
    }
}
