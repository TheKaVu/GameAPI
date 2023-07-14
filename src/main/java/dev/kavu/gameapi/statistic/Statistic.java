package dev.kavu.gameapi.statistic;

import java.util.Set;

/**
 * This interface is the base for any statistic. It classifies the triggers this statistic will be counted with as well as its default value. <br/>
 * There are predefined subinterfaces of this interface for every numeric type in {@link dev.kavu.gameapi.statistic.common .common} package. <br/><br/>
 * <b>Note: </b> For statistic to work it has to be registered using {@link dev.kavu.gameapi.GameManager GameManager}.
 *
 * @param <T> Numeric type of this statistic
 *
 * @see LockableStatistic
 * @see RegisteredStatistic
 */
public interface Statistic<T extends Number> {

    /**
     * @return Default value of this statistic
     */
    T getDefault();

    /**
     * @return Set of {@link Trigger} objects this statistic will be counted with
     */
    Set<Trigger<?>> getTriggers();
}
