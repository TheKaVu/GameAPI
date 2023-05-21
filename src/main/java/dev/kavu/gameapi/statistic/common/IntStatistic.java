package dev.kavu.gameapi.statistic.common;

import dev.kavu.gameapi.statistic.Statistic;

public interface IntStatistic extends Statistic<Integer> {

    static int increment(int a){
        return ++a;
    }

    static int decrement(int a){
        return --a;
    }
}
