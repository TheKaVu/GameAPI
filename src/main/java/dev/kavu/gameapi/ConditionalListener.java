package dev.kavu.gameapi;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.lang.reflect.Method;
import java.util.function.BooleanSupplier;

public class ConditionalListener implements Listener {

    // Fields
    private final Listener handledListener;

    private final BooleanSupplier condition;

    // Constructor
    public ConditionalListener(Listener handledListener, BooleanSupplier condition) {
        this.condition = condition;
        this.handledListener = handledListener;
    }

    // Getters
    public Listener getHandledListener() {
        return handledListener;
    }

    // Functionality
    @EventHandler
    public final void onEvent(Event event){
        for(Method method : handledListener.getClass().getDeclaredMethods()){

            if(method.getParameterCount() != 1) return;
            if(!condition.getAsBoolean()) return;

            try {
                method.invoke(handledListener, event);
            } catch (Exception ignored) {
            }
        }
    }
}
