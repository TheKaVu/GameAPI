package dev.kavu.gameapi;

import java.util.*;
import java.util.function.BiPredicate;

public abstract class StatisticSet<V> extends Statistic<V> {

    private final HashMap<UUID, Statistic<V>> map = new HashMap<>();

    public StatisticSet(V defaultValue) {
        super(defaultValue);
    }

    public StatisticSet(V defaultValue, Collection<UUID> members) {
        super(defaultValue);
        for (UUID id : members) {
            map.put(id, new Statistic<V>(defaultValue) {});
        }
    }

    @Override
    public void set(V value) {
        map.forEach((k, v) -> v.set(value));
    }

    public void set(BiPredicate<UUID, V> selector, V value) {
        map.forEach((k, v) -> {
            if(selector.test(k, v.get())) v.set(value);
        });
    }

    @Override
    public V get(){
        return getDefault();
    }

    public Statistic<V> get(UUID uuid){
        return map.get(uuid);
    }

    public Set<UUID> getMembers(){
        return map.keySet();
    }

    @Override
    public void reset(){
        map.forEach((k, v) -> v.reset());
    }

}
