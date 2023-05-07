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

    public <T extends Statistic<V>> T getStatistic(Supplier<T> supplier) throws ClassCastException {
        T statistic = supplier.get();
        statistic.setLocked(false);
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
