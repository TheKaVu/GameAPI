package dev.kavu.gameapi.statistic.common;

import dev.kavu.gameapi.statistic.Statistic;

public interface LongStatistic extends Statistic<Long> {

    static long increment(long a){
        return ++a;
    }

    static long decrement(long a){
        return --a;
    }
}
