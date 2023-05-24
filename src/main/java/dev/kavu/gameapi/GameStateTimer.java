package dev.kavu.gameapi;

import java.util.Timer;
import java.util.TimerTask;

public class GameStateTimer {

    // Fields
    private Timer timer = new Timer();

    private TimerTask task;

    private GameState currentState;

    private final GameTimerSchedule schedule;

    private long stateTime = 0;

    private boolean timerReversed;

    private boolean running = false;

    // Constructors
    public GameStateTimer(){
        currentState = GameState.EMPTY;
        schedule = null;
    }

    public GameStateTimer(GameState initialState){
        if(initialState == null){
            throw new NullPointerException();
        }
        currentState = initialState;
        schedule = null;
    }

    public GameStateTimer(GameTimerSchedule schedule){
        currentState = GameState.EMPTY;
        this.schedule = schedule;
    }

    public GameStateTimer(GameState initialState, GameTimerSchedule schedule){
        if(initialState == null){
            throw new NullPointerException();
        }
        currentState = initialState;
        this.schedule = schedule;
    }

    // Getters & Setters
    public GameState getCurrentState() {
        return currentState;
    }

    public GameTimerSchedule getSchedule() {
        return schedule;
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
        if(gameState == null){
            throw new NullPointerException();
        }

        running = true;
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
            currentState.onEnd();
            if(schedule != null){
                initialize(schedule.next());
            }
            return true;
        }

        return false;
    }

    public void terminate(boolean runNext){
        if(runNext && schedule != null) {
            initialize(schedule.next());
        } else {
            initialize(GameState.EMPTY);
        }
        pause();
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

}