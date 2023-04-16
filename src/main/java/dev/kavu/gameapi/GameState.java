package dev.kavu.gameapi;

public abstract class GameState {


    private final long duration;

    private final int period;

    private final boolean reverseTimer;

    private final String name;


    public GameState(long duration, boolean reverseTimer, String name) {
        this.duration = duration;
        this.period = 1;
        this.reverseTimer = reverseTimer;
        this.name = name;
    }

    public GameState(long duration, int period, boolean reverseTimer, String name) {
        this.duration = duration;
        this.period = period;
        this.reverseTimer = reverseTimer;
        this.name = name;
    }


    public long getDuration() {
        return duration;
    }

    public int getPeriod() {
        return period;
    }

    public boolean doesReverseTimer() {
        return reverseTimer;
    }

    public String getName() {
        return name;
    }


    public abstract void onInit();

    public abstract void tick();

    public abstract void onEnd();

    public abstract boolean shouldEnd();
}
