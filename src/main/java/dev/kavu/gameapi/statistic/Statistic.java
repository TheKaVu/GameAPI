package dev.kavu.gameapi.statistic;

public interface Statistic<T extends Number> {

    void lock();

    boolean isLocked();

    T onTrigger(T current);
}
