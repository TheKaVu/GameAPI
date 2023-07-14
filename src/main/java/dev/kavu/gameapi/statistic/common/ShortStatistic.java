package dev.kavu.gameapi.statistic.common;

import dev.kavu.gameapi.statistic.Statistic;

/**
 * Subinterface of {@link Statistic} interface of <tt>short</tt> type
 */
public interface ShortStatistic extends Statistic<Short> {

    /**
     * Incrementor for <tt>short</tt> numbers
     * @param a Number to be incremented
     * @return Incremented number
     */
    static short increment(short a){
        return ++a;
    }

    /**
     * Decrementor for <tt>short</tt> numbers
     * @param a Number to be decremented
     * @return Decremented number
     */
    static short decrement(short a){
        return --a;
    }
}
