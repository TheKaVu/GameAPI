package dev.kavu.gameapi;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

public class StatisticGroup<T extends Number> {

    // Fields
    private final HashMap<UUID, T> values;

    private final T initValue;

    private boolean locked;

    // Constructors
    public StatisticGroup(T initValue) {
        this.initValue = initValue;
        values = new HashMap<>();
    }

    public StatisticGroup(T initValue, Set<UUID> members) {
        this.initValue = initValue;
        values = new HashMap<>();
        for(UUID m : members){
            values.put(m, initValue);
        }
    }

    // Getters & setters
    public T get(UUID member) {
        if(!values.containsKey(member)) values.put(member, initValue);
        return values.get(member);
    }

    public void set(UUID member, T value) {
        if(!locked) this.values.replace(member, value);
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isLocked() {
        return locked;
    }

    // Functionality
    public boolean addMember(UUID newMember){
        if(values.containsKey(newMember)) return false;

        values.put(newMember, initValue);
        return true;
    }

    public void reset(){
        if(!locked) values.forEach((k, v) -> values.put(k, initValue));
    }

    public void modify(Function<T, T> modifier, UUID member){
        if(locked) return;

        if(member == null) {
            values.forEach((k, v) -> values.put(k, modifier.apply(v)));
        } else {
            values.replace(member, modifier.apply(values.get(member)));
        }
    }
}
