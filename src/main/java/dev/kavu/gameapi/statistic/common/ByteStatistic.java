package dev.kavu.gameapi.statistic.common;

import dev.kavu.gameapi.statistic.Statistic;

public interface ByteStatistic extends Statistic<Byte> {

    static byte increment(byte a){
        return ++a;
    }

    static byte decrement(byte a){
        return --a;
    }
}
