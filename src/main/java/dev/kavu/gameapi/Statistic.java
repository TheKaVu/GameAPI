package dev.kavu.gameapi;

import java.util.function.Function;

public class Statistic {

    // Fields
    private int value;

    private final int initValue;

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
        this.value = value;
    }

    // Functionality
    public void reset(){
        value = initValue;
    }
}
