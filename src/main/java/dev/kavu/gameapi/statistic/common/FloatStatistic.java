package dev.kavu.gameapi.statistic.common;

import dev.kavu.gameapi.statistic.Statistic;

/**
 * Subinterface of {@link Statistic} interface of <tt>float</tt> type
 */
public interface FloatStatistic extends Statistic<Float> {

    /**
     * Incrementor for <tt>float</tt> numbers
     * @param a Number to be incremented
     * @return Incremented number
     */
    static float increment(float a){
        return ++a;
    }

    /**
     * Decrementor for <tt>float</tt> numbers
     * @param a Number to be decremented
     * @return Decremented number
     */
    static float decrement(float a){
        return --a;
    }
}
