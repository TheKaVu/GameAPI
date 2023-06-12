package dev.kavu.gameapi;

public class GameStateInterruptException extends Exception {

    public GameStateInterruptException() {
        super("Tried to initialize a new game state over an uninterruptible one");
    }

    public GameStateInterruptException(String message) {
        super(message);
    }
}
