package dev.kavu.gameapi.statistic;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class StatisticTriggerEvent extends Event {

    // Fields
    private final StatisticRegistry<?> statisticRegistry;
    private final UUID member;
    private final Number value;

    public StatisticTriggerEvent(StatisticRegistry<?> statisticRegistry, UUID member, Number value) {
        this.statisticRegistry = statisticRegistry;
        this.member = member;
        this.value = value;
    }

    public StatisticRegistry<?> getStatisticRegistry() {
        return statisticRegistry;
    }

    public UUID getMember() {
        return member;
    }

    public Number getValue() {
        return value;
    }

    @Override
    public HandlerList getHandlers() {
        return new HandlerList();
    }
}
