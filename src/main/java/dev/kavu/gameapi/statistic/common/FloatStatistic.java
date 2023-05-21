package dev.kavu.gameapi.statistic.common;

import dev.kavu.gameapi.statistic.Statistic;

public interface FloatStatistic extends Statistic<Float> {

    static float increment(float a){
        return ++a;
    }

    static float decrement(float a){
        return --a;
    }
}
