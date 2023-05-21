package dev.kavu.gameapi.statistic.common;

import dev.kavu.gameapi.statistic.Statistic;

public interface DoubleStatistic extends Statistic<Double> {

    static double increment(double a){
        return ++a;
    }

    static double decrement(double a){
        return --a;
    }
}
