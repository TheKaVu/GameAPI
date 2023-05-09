package dev.kavu.gameapi.statistic;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class StatisticSetWrapper<V extends Serializable> implements Serializable  {

    private final boolean locked;
    private final UUID[] keys;
    private final StatisticWrapper<V>[] values;

    private StatisticSetWrapper(boolean locked, UUID[] keys, StatisticWrapper<V>[] values) {
        this.locked = locked;
        this.keys = keys;
        this.values = values;
    }

    public <T extends StatisticSet<V>> T getStatisticSet(T statisticSet) {
        if(statisticSet == null){
            throw new NullPointerException();
        }
        for(int i = 0; i < keys.length; i++){
            statisticSet.get().put(keys[i], values[i].getStatistic(new Statistic<V>(statisticSet.getDefaultEntry()) {}));
        }
        statisticSet.setLocked(locked);
        return statisticSet;
    }

    public <T extends StatisticSet<V>> T getStatisticSet(Class<T> clazz) throws InstantiationException, InvocationTargetException{
        if(clazz == null){
            throw new NullPointerException();
        }
        try {
            T statisticSet = clazz.getConstructor().newInstance();
            for(int i = 0; i < keys.length; i++){
                statisticSet.get().put(keys[i], values[i].getStatistic(new Statistic<V>(statisticSet.getDefaultEntry()) {}));
            }
            statisticSet.setLocked(locked);
            return statisticSet;
        } catch (IllegalAccessException | NoSuchMethodException ignored) {
        }
        return null;
    }

    public static <V extends Serializable> StatisticSetWrapper<V> wrap(StatisticSet<V> statisticSet) {
        if(statisticSet == null){
            throw new NullPointerException();
        }
        int size = statisticSet.get().size();
        UUID[] keys = new UUID[size];
        StatisticWrapper<V>[] values = new StatisticWrapper[size];

        AtomicInteger i = new AtomicInteger();

        statisticSet.get().forEach((k, v) -> {
            keys[i.get()] = k;
            values[i.get()] = StatisticWrapper.wrap(v);
            i.incrementAndGet();
        });
        return new StatisticSetWrapper<>(statisticSet.isLocked(), keys, values);
    }
}
