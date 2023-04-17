package dev.kavu.gameapi;

import java.util.function.Function;

public class Statistic {

    // Fields
    private int value;

    private final int initValue;

    private boolean locked;

    // Constructor
    public Statistic(int initValue) {
        this.initValue = initValue;
        reset();
    }

    // Getters & setters
    public int get() {
        return value;
    }

    public void set(int value) {
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

    public void modify(Function<Integer, Integer> modifier){
        if(!locked) value = modifier.apply(value);
    }
}
