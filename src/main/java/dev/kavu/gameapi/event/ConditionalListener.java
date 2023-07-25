package dev.kavu.gameapi.event;

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

/**
 * This class is the special listener that can be surrounded with a condition that determines whether event will be passed forward or not.
 * <tt>ConditionalListener</tt> is the wrapping for any Bukkit {@link Listener} object.
 */
public class ConditionalListener {

    // Fields
    private final Listener handledListener;

    private final BooleanSupplier noArgsCondition;
    private final Predicate<Event> condition;

    // Constructor

    /**
     * Creates new instance of Conditional listener with condition consuming no args.
     * @param handledListener Listener to wrap
     * @param noArgsCondition Condition with no arguments to be checked on event call
     */
    public ConditionalListener(Listener handledListener, BooleanSupplier noArgsCondition) {
        this.noArgsCondition = (noArgsCondition == null) ? () -> true : noArgsCondition;
        this.condition = event -> true;
        if(handledListener == null){
            throw new NullPointerException();
        }
        this.handledListener = handledListener;
    }

    /**
     * Creates new instance of Conditional listener with argument condition.
     * @param handledListener Listener to wrap
     * @param condition Condition to be checked on event call
     */
    public ConditionalListener(Listener handledListener, Predicate<Event> condition) {
        this.condition = (condition == null) ? event -> true : condition;
        this.noArgsCondition = () -> true;
        if(handledListener == null){
            throw new NullPointerException();
        }
        this.handledListener = handledListener;
    }

    /**
     * Creates new instance of Conditional listener with both no argument and argument conditions.
     * @param handledListener Listener to wrap
     * @param noArgsCondition Condition with no arguments to be checked on event call
     * @param condition Condition to be checked on event call
     */
    public ConditionalListener(Listener handledListener, BooleanSupplier noArgsCondition, Predicate<Event> condition) {
        this.condition = (condition == null) ? event -> true : condition;
        this.noArgsCondition = (noArgsCondition == null) ? () -> true : noArgsCondition;
        if(handledListener == null){
            throw new NullPointerException();
        }
        this.handledListener = handledListener;
    }

    // Getters

    /**
     * @return Wrapped {@link Listener}
     */
    public Listener getHandledListener() {
        return handledListener;
    }

    // Functionality

    /**
     * Registers this listener in specified plugin.
     * @param plugin Plugin to register this listener in
     */
    public void register(Plugin plugin){
        Validate.notNull(plugin, "plugin cannot be null");

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