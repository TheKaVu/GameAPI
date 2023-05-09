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

    public <T extends Statistic<V>> T getStatistic(T newStatistic) {
        if(newStatistic == null){
            throw new NullPointerException();
        }
        newStatistic.setLocked(false);
        newStatistic.set(value);
        newStatistic.setLocked(locked);
        return newStatistic;
    }

    public <T extends Statistic<V>> T getStatistic(Class<T> clazz) throws InstantiationException, InvocationTargetException{
        if(clazz == null){
            throw new NullPointerException();
        }
        try {
            T statistic = clazz.getConstructor().newInstance();
            statistic.setLocked(false);
            statistic.set(value);
            statistic.setLocked(locked);
            return statistic;
        } catch (IllegalAccessException | NoSuchMethodException ignored) {
            return null;
        }
    }

    public static <V extends Serializable> StatisticWrapper<V> wrap(Statistic<V> statistic) {
        if(statistic == null){
            throw new NullPointerException();
        }
        return new StatisticWrapper<>(statistic.get(), statistic.isLocked());
    }

    public static <V extends Serializable> StatisticWrapper<V> wrap(Supplier<Statistic<V>> supplier){
        if(supplier == null){
            throw new NullPointerException();
        }
        return wrap(supplier.get());
    }
}
