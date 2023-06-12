package dev.kavu.gameapi;

import dev.kavu.gameapi.event.GameStateEndEvent;
import dev.kavu.gameapi.event.GameStateInitEvent;
import dev.kavu.gameapi.event.GstScheduleEndEvent;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.Plugin;

import java.util.Timer;
import java.util.TimerTask;

public class GameStateTimer {

    // Fields
    private Timer timer = new Timer();

    private TimerTask task;

    private GameState currentState;

    private final GstSchedule schedule;

    private final Plugin plugin;

    private long stateTime = 0;

    private boolean timerReversed;

    private boolean running = false;

    // Constructors
    public GameStateTimer(Plugin plugin){
        Validate.notNull(plugin, "plugin cannot be null");

        this.plugin = plugin;
        currentState = GameState.EMPTY;
        schedule = null;
    }

    public GameStateTimer(GameState initialState, Plugin plugin) {
        Validate.notNull(initialState, "initialState cannot be null");
        Validate.notNull(plugin, "plugin cannot be null");

        currentState = initialState;
        schedule = null;
        this.plugin = plugin;
    }

    public GameStateTimer(GstSchedule schedule, Plugin plugin) {
        Validate.notNull(plugin, "plugin cannot be null");

        currentState = GameState.EMPTY;
        this.schedule = schedule;
        this.plugin = plugin;
    }

    public GameStateTimer(GameState initialState, GstSchedule schedule, Plugin plugin) {
        Validate.notNull(initialState, "initialState cannot be null");
        Validate.notNull(plugin, "plugin cannot be null");

        currentState = initialState;
        this.schedule = schedule;
        this.plugin = plugin;
    }

    // Getters & Setters
    public GameState getCurrentState() {
        return currentState;
    }

    public GstSchedule getSchedule() {
        return schedule;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public long getStateTime() {
        return stateTime;
    }

    public void setStateTime(long stateTime) {
        this.stateTime = stateTime;
    }

    public boolean isTimerReversed() {
        return timerReversed;
    }

    public boolean isRunning(){
        return running;
    }

    // Functionality
    public void initialize(GameState gameState){
        Validate.notNull(gameState, "gameState cannot be null");

        running = true;
        plugin.getServer().getPluginManager().callEvent(new GameStateInitEvent(this, currentState, gameState));
        currentState = gameState;
        currentState.onInit();
        timerReversed = currentState.doesReverseTimer();

        stateTime = timerReversed ? currentState.getDuration() : 0;

        if (task != null) task.cancel();
        task = new TimerTask() {
            @Override
            public void run() {
                if(!running) return;
                if (shouldCancel()) {
                    cancel();
                }
                if (currentState.getDuration() >= 0) {
                    currentState.tick();
                    stateTime += timerReversed ? -1 : 1;
                }
            }
        };

        timer.cancel();
        timer.purge();
        timer = new Timer();
        timer.schedule(task, 0, currentState.getPeriod());
    }

    private boolean shouldCancel(){

        boolean timerOvercount = (timerReversed ? (stateTime <= 0) : (stateTime >= currentState.getDuration())) && currentState.getDuration() >= 0;

        if (timerOvercount || currentState.shouldEnd()) {
            plugin.getServer().getPluginManager().callEvent(new GameStateEndEvent(this, currentState, schedule != null ? schedule.next() : null, true));

            if(schedule != null) {
                if (schedule.isDone()) {
                    plugin.getServer().getPluginManager().callEvent(new GstScheduleEndEvent(this, currentState));
                }
                currentState.onEnd();
                initialize(schedule.getCurrent());
            } else {
                currentState.onEnd();
            }

            return true;
        }

        return false;
    }

    public void terminate(boolean runNext) {
        plugin.getServer().getPluginManager().callEvent(new GameStateEndEvent(this, currentState, schedule != null ? schedule.next() : null, false));
        currentState.onEnd();
        if(runNext && schedule != null) {
            plugin.getServer().getPluginManager().callEvent(new GameStateEndEvent(this, currentState, schedule.next(), false));
            initialize(schedule.getCurrent());
        } else {
            plugin.getServer().getPluginManager().callEvent(new GameStateEndEvent(this, currentState, GameState.EMPTY, false));
            initialize(GameState.EMPTY);
        }
    }

    public void prompt(){
        if(running) shouldCancel();
    }

    public void pause(){
        running = false;
    }

    public void resume(){
        running = true;
    }

    public final static class Await extends GameState{
        public Await(long duration) {
            super("await", duration, false);
        }
    }
}
