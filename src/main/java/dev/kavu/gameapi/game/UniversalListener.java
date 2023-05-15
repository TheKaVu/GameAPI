package dev.kavu.gameapi.game;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public abstract class UniversalListener<E extends Event> {

    private final Class<E> eventClass;

    public UniversalListener(Class<E> eventClass, Plugin plugin){
        this.eventClass = eventClass;
        plugin.getServer().getPluginManager().registerEvent(eventClass, new Listener() { }, EventPriority.NORMAL, (listener, event) -> onEvent(eventClass.cast(event)), plugin);
    }

    public Class<E> getEventClass() {
        return eventClass;
    }

    public abstract void onEvent(E event);
}
