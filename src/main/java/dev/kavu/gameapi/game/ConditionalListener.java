package dev.kavu.gameapi.game;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;
import java.util.function.BooleanSupplier;

public class ConditionalListener extends UniversalListener<Event> {

    // Fields
    private final Listener handledListener;

    private final BooleanSupplier condition;

    // Constructor
    public ConditionalListener(Listener handledListener, BooleanSupplier condition, Plugin plugin) {
        super(Event.class, plugin);
        this.condition = condition == null ? () -> true : condition;
        if(handledListener == null){
            throw new NullPointerException("handledListener was null");
        }
        this.handledListener = handledListener;
    }

    // Getters
    public Listener getHandledListener() {
        return handledListener;
    }

    // Functionality
    @Override
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
