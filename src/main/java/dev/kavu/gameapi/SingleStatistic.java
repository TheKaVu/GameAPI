package dev.kavu.gameapi;

import java.util.function.Function;

public class SingleStatistic<T extends Number> extends Statistic<T> {

    // Fields
    private T value;

    private boolean locked;

    // Constructor
    public SingleStatistic(T defaultValue) {
        super(defaultValue);
        reset();
    }

    // Getters & setters
    public T get() {
        return value;
    }

    public void set(T value) {
        if(!locked) this.value = value;
    }

    // Functionality
    @Override
    public void reset(){
        if(!locked) value = getDefaultValue();
    }

    public void modify(Function<T, T> modifier){
        if(!locked) value = modifier.apply(value);
    }
}
