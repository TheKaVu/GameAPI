package dev.kavu.gameapi.statistic;

public interface Statistic<T extends Number> {

    T getDefault();

    void setLocked(boolean locked);

    boolean isLocked();

    T onTrigger(T current);

    Trigger<?>[] getTriggers();
}
