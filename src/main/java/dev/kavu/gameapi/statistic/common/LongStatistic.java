package dev.kavu.gameapi.statistic.common;

import dev.kavu.gameapi.statistic.Statistic;

/**
 * Subinterface of {@link Statistic} interface of <tt>long</tt> type
 */
public interface LongStatistic extends Statistic<Long> {

    /**
     * Incrementor for <tt>long</tt> numbers
     * @param a Number to be incremented
     * @return Incremented number
     */
    static long increment(long a){
        return ++a;
    }

    /**
     * Decrementor for <tt>long</tt> numbers
     * @param a Number to be decremented
     * @return Decremented number
     */
    static long decrement(long a){
        return --a;
    }
}
