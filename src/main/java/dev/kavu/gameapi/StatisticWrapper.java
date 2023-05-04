package dev.kavu.gameapi;

import java.io.Serializable;
import java.util.function.Supplier;

public class StatisticWrapper<V extends Serializable> implements Serializable {

    private final V defaultValue;
    private final V value;
    private final boolean locked;

    private StatisticWrapper(V defaultValue, V value, boolean locked) {
        this.defaultValue = defaultValue;
        this.value = value;
        this.locked = locked;
    }

    public <T extends Statistic<V>> T getStatistic(Class<T> clazz) throws ClassCastException {
        T statistic = clazz.cast(new Statistic<V>(defaultValue){});
        statistic.set(value);
        statistic.setLocked(locked);
        return statistic;
    }

    public static <V extends Serializable> StatisticWrapper<V> wrap(Statistic<V> statistic) {
        return new StatisticWrapper<>(statistic.getDefault(), statistic.get(), statistic.isLocked());
    }

    public static <V extends Serializable> StatisticWrapper<V> wrap(Supplier<Statistic<V>> supplier){
        return wrap(supplier.get());
    }
}
