package dev.kavu.gameapi.statistic.common;

import dev.kavu.gameapi.statistic.Statistic;

/**
 * Subinterface of {@link Statistic} interface of {@code byte} type
 */
public interface ByteStatistic extends Statistic<Byte> {

    /**
     * Incrementor for {@code byte} numbers
     * @param a Number to be incremented
     * @return Incremented number
     */
    static byte increment(byte a){
        return ++a;
    }

    /**
     * Decrementor for {@code byte} numbers
     * @param a Number to be decremented
     * @return Decremented number
     */
    static byte decrement(byte a){
        return --a;
    }
}
