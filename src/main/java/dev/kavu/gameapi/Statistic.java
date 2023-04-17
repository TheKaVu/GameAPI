package dev.kavu.gameapi;

import java.util.function.Function;

public class Statistic<T extends Number> {

    // Fields
    private T value;

    private final T initValue;

    private boolean locked;

    // Constructor
    public Statistic(T initValue) {
        this.initValue = initValue;
        reset();
    }

    // Getters & setters
    public T get() {
        return value;
    }

    public void set(T value) {
        if(!locked) this.value = value;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isLocked() {
        return locked;
    }

    // Functionality
    public void reset(){
        if(!locked) value = initValue;
    }

    public void modify(Function<T, T> modifier){
        if(!locked) value = modifier.apply(value);
    }
}
