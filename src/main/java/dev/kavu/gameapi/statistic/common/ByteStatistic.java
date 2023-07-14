package dev.kavu.gameapi.statistic.common;

import dev.kavu.gameapi.statistic.Statistic;

/**
 * Subinterface of {@link Statistic} interface of <tt>byte</tt> type
 */
public interface ByteStatistic extends Statistic<Byte> {

    /**
     * Incrementor for <tt>byte</tt> numbers
     * @param a Number to be incremented
     * @return Incremented number
     */
    static byte increment(byte a){
        return ++a;
    }

    /**
     * Decrementor for <tt>byte</tt> numbers
     * @param a Number to be decremented
     * @return Decremented number
     */
    static byte decrement(byte a){
        return --a;
    }
}
