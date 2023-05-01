package dev.kavu.gameapi;

import java.util.function.Consumer;
import java.util.function.Function;

public class Statistic<V> implements ValueContainer<V>{

    private V value;
    private final V initValue;
    private boolean locked;

    public Statistic(V initValue){
        this.initValue = initValue;
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

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void reset(){
        set(initValue);
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
