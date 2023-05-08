package dev.kavu.gameapi.statistic;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class StatisticWrapper<V extends Serializable> implements Serializable {

    private final V value;
    private final boolean locked;

    private StatisticWrapper(V value, boolean locked) {
        this.value = value;
        this.locked = locked;
    }

    public <T extends Statistic<V>> T getStatistic(Supplier<T> supplier) {
        T statistic = supplier.get();
        statistic.setLocked(false);
        statistic.set(value);
        statistic.setLocked(locked);
        return statistic;
    }

    public <T extends Statistic<V>> T getStatistic(Class<T> clazz) {
        try {
            T t = clazz.getConstructor().newInstance();
            return getStatistic(() -> t);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static <V extends Serializable> StatisticWrapper<V> wrap(Statistic<V> statistic) {
        return new StatisticWrapper<>(statistic.get(), statistic.isLocked());
    }

    public static <V extends Serializable> StatisticWrapper<V> wrap(Supplier<Statistic<V>> supplier){
        return wrap(supplier.get());
    }
}
