package dev.kavu.gameapi.event;

import dev.kavu.gameapi.GameState;
import dev.kavu.gameapi.GameStateTimer;
import dev.kavu.gameapi.GstSchedule;
import org.bukkit.event.HandlerList;

/**
 * Represents game state timer schedule event.
 */
public class GstScheduleEndEvent extends GameStateTimerEvent{

    private static final HandlerList handlers = new HandlerList();

    private final GstSchedule schedule;

    public GstScheduleEndEvent(GameStateTimer timer, GameState gameState) {
        super(timer, gameState);
        this.schedule = timer.getSchedule();
    }

    /**
     * @return {@link GstSchedule} involved in this event
     */
    public GstSchedule getSchedule() {
        return schedule;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
