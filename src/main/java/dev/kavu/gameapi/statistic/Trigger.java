package dev.kavu.gameapi.statistic;

import org.bukkit.event.Event;

import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

public class Trigger<E extends Event> {

    private final Class<E> eventClass;
    private final Function<E, UUID> mapper;
    private final Predicate<E> validator;
    private final Function<? extends Number, ? extends Number> response;

    public Trigger(Class<E> eventClass, Function<E, UUID> mapper) {
        this.eventClass = eventClass;
        this.mapper = mapper;
        validator = event -> true;
        response = n -> n;
    }

    public Trigger(Class<E> eventClass, Function<E, UUID> mapper, Predicate<E> validator) {
        this.eventClass = eventClass;
        this.mapper = mapper;
        this.validator = validator;
        response = n -> n;
    }

    public Trigger(Class<E> eventClass, Function<E, UUID> mapper, Function<? extends Number, ? extends Number> response) {
        this.eventClass = eventClass;
        this.mapper = mapper;
        this.response = response;
        validator = e -> true;
    }

    public Trigger(Class<E> eventClass, Function<E, UUID> mapper, Predicate<E> validator, Function<? extends Number, ? extends Number> response) {
        this.eventClass = eventClass;
        this.mapper = mapper;
        this.validator = validator;
        this.response = response;
    }

    public Class<E> getEventClass(){
        return eventClass;
    }

    public UUID compute(Event event){
        return mapper.apply(eventClass.cast(event));
    }

    public boolean validate(Event event){
        return validator.test(eventClass.cast(event));
    }

    public boolean match(Class<? extends Event> clazz){
        return clazz.equals(eventClass);
    }

    public Function<? extends Number, ? extends Number> getResponse() {
        return response;
    }
}
