package dev.kavu.gameapi.statistic;

import java.util.Set;

public interface Statistic<T extends Number> {

    T getDefault();

    Set<Trigger<?>> getTriggers();

    // Utility

    static long increment(long a){
        return ++a;
    }

    static double increment(double a){
        return ++a;
    }

    static float increment(float a){
        return ++a;
    }

    static long decrement(long a){
        return --a;
    }

    static double decrement(double a){
        return --a;
    }

    static float decrement(float a){
        return --a;
    }
}
