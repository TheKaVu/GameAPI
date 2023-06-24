package dev.kavu.gameapi;

import dev.kavu.gameapi.event.GameStateEndEvent;
import dev.kavu.gameapi.event.GameStateInitEvent;
import dev.kavu.gameapi.event.GstScheduleEndEvent;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.Plugin;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is a special timer that allows to split game timeline into sections called game states. It allows precise operation and control on game flow.
 */
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

    /**
     * Creates new instance of <tt>GameStateTimer</tt> class for the specific plugin.
     * @param plugin Plugin this timer is created for
     */
    public GameStateTimer(Plugin plugin){
        Validate.notNull(plugin, "plugin cannot be null");

        schedule = null;
        this.plugin = plugin;
        force(GameState.EMPTY);
    }

    /**
     * Creates new instance of <tt>GameStateTimer</tt> class for the specific plugin and immediately initializes given state.
     * @param initialState State to be initialized
     * @param plugin Plugin this timer is created for
     */
    public GameStateTimer(GameState initialState, Plugin plugin) {
        Validate.notNull(initialState, "initialState cannot be null");
        Validate.notNull(plugin, "plugin cannot be null");

        schedule = null;
        this.plugin = plugin;
        force(initialState);
    }

    /**
     * Creates new instance of <tt>GameStateTimer</tt> class for the specific plugin with the given schedule.
     * @param schedule Timer schedule represented by {@link GstSchedule} object
     * @param plugin Plugin this timer is created for
     */
    public GameStateTimer(GstSchedule schedule, Plugin plugin) {
        Validate.notNull(plugin, "plugin cannot be null");

        this.schedule = schedule;
        this.plugin = plugin;
        force(GameState.EMPTY);
    }

    /**
     * Creates new instance of <tt>GameStateTimer</tt> class for the specific plugin with the given schedule and immediately initializes given state.
     * @param initialState State to be initialized
     * @param schedule Timer schedule represented by {@link GstSchedule} object
     * @param plugin Plugin this timer is created for
     */
    public GameStateTimer(GameState initialState, GstSchedule schedule, Plugin plugin) {
        Validate.notNull(initialState, "initialState cannot be null");
        Validate.notNull(plugin, "plugin cannot be null");

        this.schedule = schedule;
        this.plugin = plugin;
        force(initialState);
    }

    // Getters & Setters

    /**
     * @return Game state this timer is currently running, {@code GameState.EMPTY} stands for no state running
     */
    public GameState getCurrentState() {
        return currentState;
    }

    /**
     * @return Current schedule of this timer, {@code null} if there is no schedule
     */
    public GstSchedule getSchedule() {
        return schedule;
    }

    /**
     * @return Plugin this timer is associated with
     */
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * @return Time lasted for current state expressed in milliseconds
     */
    public long getStateTime() {
        return stateTime;
    }

    /**
     * Sets current state time to specified value. If the value overpasses the state's time bound, the timer will automatically terminate that state.
     * @param stateTime New state time value
     */
    public void setStateTime(long stateTime) {
        this.stateTime = stateTime;
    }

    /**
     * @return {@code true} if timer counts back, {@code false} otherwise
     */
    public boolean isTimerReversed() {
        return timerReversed;
    }

    /**
     * @return {@code true} if timer is running, {@code false} otherwise
     */
    public boolean isRunning(){
        return running;
    }

    // Functionality

    /**
     * Initializes specified game state - starts a counting cycle. If the timer reaches the end, it will terminate this state automatically. Provided timer is stopped, it will be resumed.
     * @param gameState Game state to be initialized
     * @throws GameStateInterruptException When current game state is uninterruptible
     */
    public void initialize(GameState gameState) throws GameStateInterruptException {
        Validate.notNull(gameState, "gameState cannot be null");

        if(!currentState.isInterruptible()) {
            throw new GameStateInterruptException();
        }

        force(gameState);
    }

    /**
     * Terminates current game state. Has no effect if timer runs no state (runs {@code GameState.EMPTY}).
     * @param runNext If set {@code true}, initializes next state in the schedule, if it exists.
     * @throws GameStateInterruptException When current game state is uninterruptible
     */
    public void terminate(boolean runNext) throws GameStateInterruptException {
        if (currentState == GameState.EMPTY) return;

        if (!currentState.isInterruptible()) {
            throw new GameStateInterruptException();
        }

        plugin.getServer().getPluginManager().callEvent(new GameStateEndEvent(this, currentState, schedule != null ? schedule.next() : null, false));
        currentState.onEnd();
        if(runNext && schedule != null) {
            plugin.getServer().getPluginManager().callEvent(new GameStateEndEvent(this, currentState, schedule.next(), false));
            force(schedule.getCurrent());
        } else {
            plugin.getServer().getPluginManager().callEvent(new GameStateEndEvent(this, currentState, GameState.EMPTY, false));
            force(GameState.EMPTY);
        }
    }

    /**
     * If timer is running, prompts the timer - executes one independent iteration without counting up a time.
     */
    public void prompt(){
        if(running) shouldCancel();
    }

    /**
     * Pauses the timer, if it's running. Stopped timer won't perform any actions on game state, thus cannot be prompted.
     */
    public void pause(){
        running = false;
    }

    /**
     * Resumes the timer, if it's paused.
     */
    public void resume(){
        running = true;
    }

    /**
     * Forcefully initializes the specified game state, no matter current one is interruptible or not.
     */
    protected final void force(GameState gameState) {
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
                force(schedule.getCurrent());
            } else {
                currentState.onEnd();
            }

            return true;
        }

        return false;
    }
}
