package dev.kavu.gameapi.event;

import dev.kavu.gameapi.GameState;
import dev.kavu.gameapi.GameStateTimer;
import dev.kavu.gameapi.GameTimerSchedule;
import org.bukkit.event.HandlerList;

public class GameTimerScheduleEndEvent extends GameStateTimerEvent{

    private static final HandlerList handlers = new HandlerList();

    private final GameTimerSchedule schedule;

    public GameTimerScheduleEndEvent(GameStateTimer timer, GameState gameState) {
        super(timer, gameState);
        this.schedule = timer.getSchedule();
    }

    public GameTimerSchedule getSchedule() {
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
