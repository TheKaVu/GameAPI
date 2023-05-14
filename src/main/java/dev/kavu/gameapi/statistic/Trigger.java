package dev.kavu.gameapi.statistic;

import org.bukkit.event.Event;

import java.util.UUID;
import java.util.function.Function;

public class Trigger<E extends Event> {

    private final Class<E> eventClass;
    private final Function<E, UUID> mapper;

    public Trigger(Class<E> eventClass, Function<E, UUID> mapper) {
        this.eventClass = eventClass;
        this.mapper = mapper;
    }

    public Class<E> getEventClass(){
        return eventClass;
    }

    public UUID compute(Event event){
        return mapper.apply(eventClass.cast(event));
    }

    public boolean match(Class<? extends Event> clazz){
        return clazz.equals(eventClass);
    }
}
