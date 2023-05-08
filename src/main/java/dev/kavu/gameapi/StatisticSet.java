package dev.kavu.gameapi;

import java.util.*;

public abstract class StatisticSet<V> extends Statistic<HashMap<UUID, Statistic<V>>>   {

    private final V defaultEntryValue;

    public StatisticSet(V defaultValue) {
        super(new HashMap<>());
        defaultEntryValue = defaultValue;
        reset();
    }

    public StatisticSet(V defaultValue, Collection<UUID> members) {
        super(new HashMap<>());
        defaultEntryValue = defaultValue;
        for (UUID id : members) {
            get().put(id, new Statistic<V>(defaultValue) {});
        }
    }

    public Set<UUID> getMembers() {
        return get().keySet();
    }

    public V getDefaultEntry() {
        return defaultEntryValue;
    }

    public boolean put(UUID uuid){
        return get().put(uuid, new Statistic<V>(defaultEntryValue) {}) == null;
    }

    public boolean put(UUID uuid, V value){
        Statistic<V> s =  new Statistic<V>(defaultEntryValue) {};
        s.set(value);
        return get().put(uuid, s) == null;
    }

    public boolean remove(UUID uuid){
        return get().remove(uuid) != null;
    }

    @Override
    public void reset() {
        get().forEach((k, v) -> get().get(k).reset());
    }

}
