package dev.kavu.gameapi;

public final class Await extends GameState{
    public Await(long duration) {
        super("await", duration, false);
    }
}
