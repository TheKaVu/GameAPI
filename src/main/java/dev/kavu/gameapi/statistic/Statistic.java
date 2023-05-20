package dev.kavu.gameapi.statistic;

import java.util.Set;

public interface Statistic<T extends Number> {

    T getDefault();

    Set<Trigger<?>> getTriggers();

    // Utility

    static float increment(float a){
        return ++a;
    }

    static float decrement(float a){
        return --a;
    }
}
