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
        if(name == null){
            throw new NullPointerException();
        }
        this.duration = duration;
        this.period = 1;
        this.reverseTimer = reverseTimer;
        this.name = name;
    }

    public GameState(String name, long duration, int period, boolean reverseTimer) {
        if(name == null){
            throw new NullPointerException();
        }
        if (period < 1) {
            throw new IllegalArgumentException("period is less than 1");
        }
        this.duration = duration;
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

    // Functionality
    public void onInit() { }

    public void tick() { }

    public void onEnd() { }

    public boolean shouldEnd() {
        return false;
    }
}
