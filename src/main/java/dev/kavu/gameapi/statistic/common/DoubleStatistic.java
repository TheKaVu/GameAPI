package dev.kavu.gameapi.statistic.common;

import dev.kavu.gameapi.statistic.Statistic;

/**
 * Subinterface of {@link Statistic} interface of <tt>double</tt> type
 */
public interface DoubleStatistic extends Statistic<Double> {

    /**
     * Incrementor for <tt>double</tt> numbers
     * @param a Number to be incremented
     * @return Incremented number
     */
    static double increment(double a){
        return ++a;
    }

    /**
     * Decrementor for <tt>double</tt> numbers
     * @param a Number to be decremented
     * @return Decremented number
     */
    static double decrement(double a){
        return --a;
    }
}
