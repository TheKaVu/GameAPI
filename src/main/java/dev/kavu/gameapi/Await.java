package dev.kavu.gameapi;

/**
 * Subclass of {@link GameState} class used to create a time gap between separate states. As this class is in fact a <tt>GameState</tt>, it can be also added to the schedule.
 */
public final class Await extends GameState{

    /**
     * Creates new instance of <tt>Await</tt> class with specific duration.
     * @param duration Duration of this state expressed in milliseconds
     */
    public Await(long duration) {
        super("await", duration, false);
    }
}
