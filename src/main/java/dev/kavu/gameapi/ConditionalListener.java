package dev.kavu.gameapi;

import org.apache.commons.lang.Validate;
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
import java.util.function.Predicate;

public class ConditionalListener {

    // Fields
    private final Listener handledListener;

    private final BooleanSupplier noArgsCondition;
    private final Predicate<Event> condition;

    // Constructor
    public ConditionalListener(Listener handledListener, BooleanSupplier noArgsCondition) {
        this.noArgsCondition = (noArgsCondition == null) ? () -> true : noArgsCondition;
        this.condition = event -> true;
        if(handledListener == null){
            throw new NullPointerException();
        }
        this.handledListener = handledListener;
    }

    public ConditionalListener(Listener handledListener, Predicate<Event> condition) {
        this.condition = (condition == null) ? event -> true : condition;
        this.noArgsCondition = () -> true;
        if(handledListener == null){
            throw new NullPointerException();
        }
        this.handledListener = handledListener;
    }

    public ConditionalListener(Listener handledListener, BooleanSupplier noArgsCondition, Predicate<Event> condition) {
        this.condition = (condition == null) ? event -> true : condition;
        this.noArgsCondition = (noArgsCondition == null) ? () -> true : noArgsCondition;
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
        Validate.notNull(plugin);

        for(Method method : handledListener.getClass().getDeclaredMethods()){
            method.setAccessible(true);
            if(!method.isAnnotationPresent(EventHandler.class)) continue;
            if(!Modifier.isPublic(method.getModifiers())) continue;
            if(method.getParameterCount() != 1) continue;
            if(!Event.class.isAssignableFrom(method.getParameterTypes()[0])) continue;

            System.out.println("Proper event handler");

            try {

                BooleanSupplier finalNoArgsCondition = noArgsCondition;
                Predicate<Event> finalCondition = condition;
                Class<? extends Event> eventClass = (Class<? extends Event>) method.getParameterTypes()[0];
                EventExecutor executor = (listener, event) -> {
                    try {
                        if(finalNoArgsCondition.getAsBoolean() && finalCondition.test(event)){
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