package dev.kavu.gameapi;

/**
 * Thrown when uninterruptible is ended in an unnatural way. This includes:
 * <ul>
 * <li>Initializing new game state over this state</li>
 * <li>Terminating this state by hand when state is still running</li>
 * </ul>
 */
public class GameStateInterruptException extends Exception {

    /**
     * Creates new instance of <tt>GameStateInterruptException</tt> with no detail message.
     */
    public GameStateInterruptException() {
        super("Tried to initialize a new game state over an uninterruptible one");
    }

    /**
     * Creates new instance of <tt>GameStateInterruptException</tt> with specified message.
     */
    public GameStateInterruptException(String message) {
        super(message);
    }
}
