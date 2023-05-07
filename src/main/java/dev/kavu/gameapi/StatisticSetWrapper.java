package dev.kavu.gameapi;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class StatisticSetWrapper<V extends Serializable> implements Serializable  {

    private final V defaultValue;
    private final boolean locked;
    private final UUID[] keys;
    private final StatisticWrapper<V>[] values;

    private StatisticSetWrapper(V defaultValue, boolean locked, UUID[] keys, StatisticWrapper<V>[] values) {
        this.defaultValue = defaultValue;
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
        return new StatisticSetWrapper<>(statistic.getDefaultEntry(), statistic.isLocked(), keys, values);
    }

    public <T extends StatisticSet<V>> T getStatisticSet(Supplier<T> supplier) throws ClassCastException {
        T statisticSet = supplier.get();
        for(int i = 0; i < keys.length; i++){
            statisticSet.get().put(keys[i], values[i].getStatistic(() -> new Statistic<V>(statisticSet.getDefaultEntry()) {}));
        }
        statisticSet.setLocked(locked);
        return statisticSet;
    }
}
