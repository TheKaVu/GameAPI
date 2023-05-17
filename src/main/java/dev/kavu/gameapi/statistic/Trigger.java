package dev.kavu.gameapi.statistic;

import org.bukkit.event.Event;

import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

public class Trigger<E extends Event> {

    private final Class<E> eventClass;
    private final Function<E, UUID> mapper;
    private final Predicate<E> validator;

    public Trigger(Class<E> eventClass, Function<E, UUID> mapper) {
        this.eventClass = eventClass;
        this.mapper = mapper;
        validator = event -> true;
    }

    public Trigger(Class<E> eventClass, Function<E, UUID> mapper, Predicate<E> validator) {
        this.eventClass = eventClass;
        this.mapper = mapper;
        this.validator = validator;
    }

    public Class<E> getEventClass(){
        return eventClass;
    }

    public UUID compute(Event event){
        return mapper.apply(eventClass.cast(event));
    }

    public boolean validate(E event){
        return validator.test(event);
    }

    public boolean match(Class<? extends Event> clazz){
        return clazz.equals(eventClass);
    }
}
