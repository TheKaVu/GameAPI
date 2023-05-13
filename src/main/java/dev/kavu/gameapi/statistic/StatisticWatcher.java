package dev.kavu.gameapi.statistic;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class StatisticWatcher<T extends Number> {

    // Fields
    private final Statistic<T> statistic;
    private final HashMap<UUID, T> members = new HashMap<>();

    // Constructor
    public StatisticWatcher(Statistic<T> statistic){
        this.statistic = statistic;
    }

    // Getters
    public Statistic<T> getStatistic() {
        return statistic;
    }

    public HashMap<UUID, T> getMembers() {
        return members;
    }

    // Functionality
    public boolean exec(Function<T, T> function) {

        if(members.isEmpty()) return false;

        boolean result = true;

        for (Map.Entry<UUID, T> e : members.entrySet()){
            result = execFor(e.getKey(), function) && result;
        }

        return result;
    }

    public boolean execFor(UUID member, Function<T, T> function) {

        if(statistic.isLocked()) return false;
        if(members.isEmpty()) return false;

        if(function == null) throw new NullPointerException();

        return members.replace(member, function.apply(members.get(member))) == null;
    }
}
