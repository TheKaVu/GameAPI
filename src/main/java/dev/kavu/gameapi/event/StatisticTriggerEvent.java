package dev.kavu.gameapi.event;

import dev.kavu.gameapi.statistic.RegisteredStatistic;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

/**
 * Represents a statistic trigger event.
 */
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

    /**
     * @return Triggered statistic represented by {@link RegisteredStatistic} object
     */
    public RegisteredStatistic<?> getRegisteredStatistic() {
        return registeredStatistic;
    }

    /**
     * @return Member the statistic was triggered for
     */
    public UUID getMember() {
        return member;
    }

    /**
     * @return Value of the statistic associated with member the statistic was triggered for
     */
    public Number getValue() {
        return value;
    }

    /**
     * @return Event that caused a trigger; {@code null} if trigger was manual
     *
     * @see RegisteredStatistic#trigger
     */
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
