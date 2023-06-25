package dev.kavu.gameapi;

import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to define a schedule for {@link GameStateTimer}. Schedule behaves similar to queue of {@link GameState} objects, but the index of element can be warped backwards.
 * States will be initialized automatically after natural end; when interrupted, they won't unless specified.
 */
public class GstSchedule {

    private final List<GameState> schedule;
    private int index = -1;

    /**
     * Creates new instance of GstSchedule with no states.
     */
    public GstSchedule(){
        schedule = new ArrayList<>();
    }

    /**
     * Creates new instance of GstSchedule with initial order of states. First element in the list is the first element in the schedule.
     *
     * @param schedule Initial order of game states
     */
    public GstSchedule(List<GameState> schedule) {
        Validate.notNull(schedule, "schedule cannot be null");
        this.schedule = schedule;
    }

    /**
     * Adds specified game state at the end of the schedule.
     *
     * @param gameState State to be added
     */
    public void addState(GameState gameState){
        Validate.notNull(gameState, "gameState cannot be null");
        schedule.add(gameState);
    }
    /**
     * Adds specified game states at the end of the schedule in listed order.
     *
     * @param gameStates States to be added
     */
    public void addStates(List<GameState> gameStates){
        schedule.addAll(gameStates);
    }
    /**
     * @return State the iterator is at currently
     */
    public GameState getCurrent() {
        if(index < schedule.size()) {
            return schedule.get(index);
        }
        return GameState.EMPTY;
    }

    /**
     * Increments the iterator and returns the state it is at.
     *
     * @return Next game state in the schedule, {@link GameState#EMPTY} if such doesn't exist
     */
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

    /**
     * @return {@code true} if the schedule reached the end, {@code false} otherwise
     */
    public boolean isDone(){
        return index >= schedule.size();
    }

    /**
     * Sets iterator to the first element of the schedule.
     */
    public void redo(){
        index = 0;
    }

    /**
     * Sets iterator to the nonexistent element at the end of the schedule.
     */
    public void skip(){
        index = schedule.size();
    }
}
