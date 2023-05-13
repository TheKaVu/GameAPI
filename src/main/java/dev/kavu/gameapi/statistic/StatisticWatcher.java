package dev.kavu.gameapi.statistic;

import java.util.HashMap;
import java.util.UUID;

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
}
