package dev.kavu.gameapi.statistic.common;

import dev.kavu.gameapi.statistic.Statistic;

/**
 * Subinterface of {@link Statistic} interface of <tt>int</tt> type
 */
public interface IntStatistic extends Statistic<Integer> {

    /**
     * Incrementor for <tt>int</tt> numbers
     * @param a Number to be incremented
     * @return Incremented number
     */
    static int increment(int a){
        return ++a;
    }

    /**
     * Decrementor for <tt>int</tt> numbers
     * @param a Number to be decremented
     * @return Decremented number
     */
    static int decrement(int a){
        return --a;
    }
}
