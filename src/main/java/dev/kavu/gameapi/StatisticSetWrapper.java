package dev.kavu.gameapi;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class StatisticSetWrapper<V extends Serializable> implements Serializable  {

    private final boolean locked;
    private final UUID[] keys;
    private final StatisticWrapper<V>[] values;

    private StatisticSetWrapper(boolean locked, UUID[] keys, StatisticWrapper<V>[] values) {
        this.locked = locked;
        this.keys = keys;
        this.values = values;
    }

    public static <V extends Serializable> StatisticSetWrapper<V> wrap(StatisticSet<V> statistic) {
        int size = statistic.get().size();
        UUID[] keys = new UUID[size];
        StatisticWrapper<V>[] values = new StatisticWrapper[size];

        AtomicInteger i = new AtomicInteger();

        statistic.get().forEach((k, v) -> {
            keys[i.get()] = k;
            values[i.get()] = StatisticWrapper.wrap(v);
            i.incrementAndGet();
        });
        return new StatisticSetWrapper<>(statistic.isLocked(), keys, values);
    }

    public <T extends StatisticSet<V>> T getStatisticSet(Supplier<T> supplier) {
        T statisticSet = supplier.get();
        for(int i = 0; i < keys.length; i++){
            statisticSet.get().put(keys[i], values[i].getStatistic(() -> new Statistic<V>(statisticSet.getDefaultEntry()) {}));
        }
        statisticSet.setLocked(locked);
        return statisticSet;
    }

    public <T extends StatisticSet<V>> T getStatisticSet(Class<T> clazz) {
        try {
            T t = clazz.getConstructor().newInstance();
            return getStatisticSet(() -> t);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ignored) {
        }
        return null;
    }
}
