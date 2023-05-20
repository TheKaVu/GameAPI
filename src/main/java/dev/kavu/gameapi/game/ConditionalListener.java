package dev.kavu.gameapi.game;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.BooleanSupplier;

public class ConditionalListener {

    // Fields
    private final Listener handledListener;

    private final BooleanSupplier condition;

    // Constructor
    public ConditionalListener(Listener handledListener, BooleanSupplier condition) {
        this.condition = (condition == null) ? () -> true : condition;
        if(handledListener == null){
            throw new NullPointerException();
        }
        this.handledListener = handledListener;
    }

    // Getters
    public Listener getHandledListener() {
        return handledListener;
    }

    // Functionality
    public void register(Plugin plugin){
        if(plugin == null) {
            throw new NullPointerException();
        }

        for(Method method : handledListener.getClass().getDeclaredMethods()){
            method.setAccessible(true);
            if(!method.isAnnotationPresent(EventHandler.class)) continue;
            if(!Modifier.isPublic(method.getModifiers())) continue;
            if(method.getParameterCount() != 1) continue;
            if(!Event.class.isAssignableFrom(method.getParameterTypes()[0])) continue;

            System.out.println("Proper event handler");

            try {

                BooleanSupplier finalCondition = condition;
                Class<? extends Event> eventClass = (Class<? extends Event>) method.getParameterTypes()[0];
                EventExecutor executor = (listener, event) -> {
                    try {
                        if(finalCondition.getAsBoolean()){
                            System.out.println("condition check passed");
                            method.invoke(listener, event);
                            System.out.println("event passed down to listener");
                        }
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassCastException ex) {
                        ex.printStackTrace();
                    }
                };
                System.out.println("executor assigned");

                plugin.getServer().getPluginManager().registerEvent(eventClass, handledListener, EventPriority.NORMAL, executor, plugin);
                System.out.println("executor registered");
            } catch (ClassCastException ignored) {
            }
        }
    }
}
