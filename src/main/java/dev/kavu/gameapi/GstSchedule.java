package dev.kavu.gameapi;

import java.util.ArrayList;
import java.util.List;

public class GstSchedule {

    private final List<GameState> schedule;
    private int index = -1;

    public GstSchedule(){
        schedule = new ArrayList<>();
    }

    public GstSchedule(List<GameState> schedule) {
        this.schedule = schedule;
    }

    public void addState(GameState gameState){
        schedule.add(gameState);
    }

    public void addStates(List<GameState> gameStates){
        schedule.addAll(gameStates);
    }

    public GameState getCurrent() {
        if(index < schedule.size()) {
            return schedule.get(index);
        }
        return GameState.EMPTY;
    }

    public GameState next() {
        GameState state;
        if(index + 1 < schedule.size()) {
            index++;
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

    public void skip(){
        index = schedule.size() - 1;
    }

    public static class AwaitState extends GameState{
        public AwaitState(long duration) {
            super("await", duration, false);
        }
    }
}