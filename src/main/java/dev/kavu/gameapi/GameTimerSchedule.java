package dev.kavu.gameapi;

import java.util.ArrayList;
import java.util.List;

public class GameTimerSchedule {

    private final List<GameState> schedule;
    private int index = -1;

    public GameTimerSchedule(){
        schedule = new ArrayList<>();
    }

    public GameTimerSchedule(List<GameState> schedule) {
        this.schedule = schedule;
    }

    public GameState next() {
        GameState state;

        index++;
        if(index < schedule.size()) {
            state = schedule.get(index);
        } else {
            state = GameState.EMPTY;
        }
        return state;
    }

    public boolean isDone(){
        return index < schedule.size();
    }

    public void redo(){
        index = -1;
    }

    public static class AwaitState extends GameState{
        public AwaitState(long duration) {
            super("await", duration, false);
        }
    }
}
