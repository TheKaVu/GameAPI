package dev.kavu.gameapi;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Statistic<V> implements ValueContainer<V>{

    private V value;
    private final V defaultValue;
    private boolean locked;

    public Statistic(V defaultValue){
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        locked = false;
    }

    @Override
    public V get() {
        return value;
    }

    @Override
    public void set(V value) {
        if(!locked){
            this.value = value;
        }
    }

    public V getDefault() {
        return defaultValue;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public V getDefaultValue() {
        return defaultValue;
    }

    public void reset(){
        set(defaultValue);
    }

    @Override
    public void modify(Consumer<V> modifier) {
        if(!locked){
            modifier.accept(value);
        }
    }

    @Override
    public void modify(Function<V, V> modifier) {
        set(modifier.apply(value));
    }
}
