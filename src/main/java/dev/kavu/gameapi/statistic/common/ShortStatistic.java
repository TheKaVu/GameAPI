package dev.kavu.gameapi.statistic.common;

import dev.kavu.gameapi.statistic.Statistic;

public interface ShortStatistic extends Statistic<Short> {

    static short increment(short a){
        return ++a;
    }

    static short decrement(short a){
        return --a;
    }
}
