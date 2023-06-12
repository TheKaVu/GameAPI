package dev.kavu.gameapi;

import org.apache.commons.lang.Validate;

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

    private final boolean interruptible;

    private final String name;

    // Constructors
    public GameState(String name, long duration, boolean reverseTimer) {
        Validate.notNull(name, "name cannot be null");

        this.duration = duration;
        this.period = 1;
        this.reverseTimer = reverseTimer;
        this.interruptible = true;
        this.name = name;
    }

    public GameState(String name, long duration, int period, boolean reverseTimer, boolean interruptible) {
        Validate.notNull(name, "name cannot be null");
        Validate.isTrue(period > 0, "period must be greater than 0");

        this.duration = duration;
        this.period = period;
        this.reverseTimer = reverseTimer;
        this.interruptible = interruptible;
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

    public boolean isInterruptible() {
        return interruptible;
    }

    public String getName() {
        return name;
    }

    // Functionality
    public void onInit() { }

    public void tick() { }

    public void onEnd() { }

    public boolean shouldEnd() {
        return false;
    }
}
