package dev.kavu.gameapi.statistic;

import java.util.Set;

public interface Statistic<T extends Number> {

    T getDefault();

    void setLocked(boolean locked);

    boolean isLocked();

    T onTrigger(T current);

    Trigger<?>[] getTriggers();
}
