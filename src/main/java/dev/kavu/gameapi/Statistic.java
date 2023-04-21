package dev.kavu.gameapi;

public abstract class Statistic<T extends Number> {

    // Fields
    private final T defaultValue;

    private boolean locked;

    // Constructor
    public Statistic(T defaultValue){
        this.defaultValue = defaultValue;
        locked = false;
    }

    // Getters & setters
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isLocked() {
        return locked;
    }

    public T getDefaultValue(){
        return defaultValue;
    }

    // Abstract functionality
    public abstract void reset();
}
