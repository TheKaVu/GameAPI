package dev.kavu.gameapi.statistic;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class StatisticTriggerEvent extends Event {

    // Fields
    private static final HandlerList handlers = new HandlerList();

    private final RegisteredStatistic<?> registeredStatistic;
    private final UUID member;
    private final Number value;
    private final Event triggerEvent;

    public StatisticTriggerEvent(RegisteredStatistic<?> registeredStatistic, UUID member, Number value) {
        this.registeredStatistic = registeredStatistic;
        this.member = member;
        this.value = value;
        this.triggerEvent = null;
    }

    public StatisticTriggerEvent(RegisteredStatistic<?> registeredStatistic, UUID member, Number value, Event triggerEvent) {
        this.registeredStatistic = registeredStatistic;
        this.member = member;
        this.value = value;
        this.triggerEvent = triggerEvent;
    }

    public RegisteredStatistic<?> getRegisteredStatistic() {
        return registeredStatistic;
    }

    public UUID getMember() {
        return member;
    }

    public Number getValue() {
        return value;
    }

    public Event getTriggerEvent(){
        return triggerEvent;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }
}
