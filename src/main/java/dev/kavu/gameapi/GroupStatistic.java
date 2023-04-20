package dev.kavu.gameapi;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

public class GroupStatistic<T extends Number> extends Statistic<T>{

    // Fields
    private final HashMap<UUID, T> values;

    private boolean locked;

    // Constructors
    public GroupStatistic(T defaultValue) {
        super(defaultValue);
        values = new HashMap<>();
    }

    public GroupStatistic(T defaultValue, Set<UUID> members) {
        super(defaultValue);
        values = new HashMap<>();
        for(UUID m : members){
            values.put(m, defaultValue);
        }
    }

    // Getters & setters
    public T get(UUID member) {
        if(!values.containsKey(member)) values.put(member, getDefaultValue());
        return values.get(member);
    }

    public void set(UUID member, T value) {
        if(!locked) this.values.replace(member, value);
    }

    // Functionality
    public boolean addMember(UUID newMember){
        if(values.containsKey(newMember)) return false;

        values.put(newMember, getDefaultValue());
        return true;
    }

    @Override
    public void reset(){
        if(!locked) values.forEach((k, v) -> values.put(k, getDefaultValue()));
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
