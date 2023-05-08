package dev.kavu.gameapi.game;

public abstract class GameState {

    // Constants
    public static final GameState EMPTY = new GameState("",0, false) {
        @Override
        public void onInit() {
        }

        @Override
        public void onEnd() {
        }

        @Override
        public void tick() {

        }

        @Override
        public boolean shouldEnd() {
            return false;
        }
    };

    // Fields
    private final long duration;

    private final int period;

    private final boolean reverseTimer;

    private final String name;

    // Constructors
    public GameState(String name, long duration, boolean reverseTimer) {
        this.duration = duration;
        this.period = 1;
        this.reverseTimer = reverseTimer;
        this.name = name;
    }

    public GameState(String name, long duration, int period, boolean reverseTimer) {
        this.duration = duration;
        if (period < 1)
            throw new IllegalArgumentException("period is less than 1");
        this.period = period;
        this.reverseTimer = reverseTimer;
        this.name = name;
    }

    // Getters
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

    // Abstract functionality
    public abstract void onInit();

    public abstract void tick();

    public abstract void onEnd();

    public abstract boolean shouldEnd();
}
