package dev.kavu.gameapi.statistic;

import java.util.Set;

public interface Statistic<T extends Number> {

    T getDefault();

    Set<Trigger<?>> getTriggers();
}
