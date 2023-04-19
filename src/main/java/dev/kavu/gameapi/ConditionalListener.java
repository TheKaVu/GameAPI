package dev.kavu.gameapi;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.lang.reflect.Method;
import java.util.function.BooleanSupplier;

public class ConditionalListener implements Listener {

    // Fields
    private final Listener listener;

    private final BooleanSupplier condition;

    // Constructor
    public ConditionalListener(BooleanSupplier condition, Listener handledListener) {
        this.condition = condition;
        this.listener = handledListener;
    }

    // Getters
    public Listener getListener() {
        return listener;
    }

    // Functionality
    @EventHandler
    public final void onEvent(Event event){
        for(Method method : listener.getClass().getDeclaredMethods()){

            if(method.getParameterCount() != 1) return;
            if(!condition.getAsBoolean()) return;

            try {
                method.invoke(listener, event);
            } catch (Exception ignored) {
            }
        }
    }
}
