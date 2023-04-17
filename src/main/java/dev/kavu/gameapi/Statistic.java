package dev.kavu.gameapi;

public class Statistic {

    // Fields
    private int value;

    private final int initValue;

    private final String name;

    // Constructor
    public Statistic(int initValue, String name) {
        this.initValue = initValue;
        this.name = name;
        reset();
    }

    // Getters & setters
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    // Functionality
    public void reset(){
        value = initValue;
    }
}
