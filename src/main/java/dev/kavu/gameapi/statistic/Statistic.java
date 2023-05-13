package dev.kavu.gameapi.statistic;

public interface Statistic<T extends Number> {

    T getValue();

    void setValue(T value);

    void lock();

    boolean isLocked();
}
