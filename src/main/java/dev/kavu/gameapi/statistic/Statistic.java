package dev.kavu.gameapi.statistic;

public interface Statistic<T extends Number> {

    void setLocked(boolean locked);

    boolean isLocked();

    T onTrigger(T current);
}
