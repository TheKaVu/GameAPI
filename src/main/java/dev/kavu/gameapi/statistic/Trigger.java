package dev.kavu.gameapi.statistic;

import org.apache.commons.lang.Validate;
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
        Validate.notNull(eventClass, "eventClass cannot be null");
        Validate.notNull(mapper, "mapper cannot be null");

        this.eventClass = eventClass;
        this.mapper = mapper;
        validator = event -> true;
        response = n -> n;
    }

    public Trigger(Class<E> eventClass, Function<E, UUID> mapper, Predicate<E> validator) {
        Validate.notNull(eventClass, "eventClass cannot be null");
        Validate.notNull(mapper, "mapper cannot be null");
        Validate.notNull(validator, "validator cannot be null");

        this.eventClass = eventClass;
        this.mapper = mapper;
        this.validator = validator;
        response = n -> n;
    }

    public <T extends Number> Trigger(Class<E> eventClass, Function<E, UUID> mapper, Function<T, T> response) {
        Validate.notNull(eventClass, "eventClass cannot be null");
        Validate.notNull(mapper, "mapper cannot be null");
        Validate.notNull(response, "response cannot be null");

        this.eventClass = eventClass;
        this.mapper = mapper;
        this.response = response;
        validator = e -> true;
    }

    public <T extends Number> Trigger(Class<E> eventClass, Function<E, UUID> mapper, Predicate<E> validator, Function<T, T> response) {
        Validate.notNull(eventClass, "eventClass cannot be null");
        Validate.notNull(mapper, "mapper cannot be null");
        Validate.notNull(validator, "validator cannot be null");
        Validate.notNull(response, "response cannot be null");

        this.eventClass = eventClass;
        this.mapper = mapper;
        this.validator = validator;
        this.response = response;
    }

    public Class<E> getEventClass(){
        return eventClass;
    }

    public UUID compute(Event event){
        Validate.notNull(event, "event cannot be null");

        return mapper.apply(eventClass.cast(event));
    }

    public boolean validate(Event event){
        Validate.notNull(event, "event cannot be null");

        return validator.test(eventClass.cast(event));
    }

    public <T extends Number> Function<T, T> getResponse() {
        return (Function<T, T>) response;
    }
}
