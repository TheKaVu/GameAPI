package dev.kavu.gameapi.statistic;

public interface Statistic<T extends Number> {

    T getDefault();

    Trigger<?>[] getTriggers();
}
