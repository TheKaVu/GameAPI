package dev.kavu.gameapi;

import org.apache.commons.lang.Validate;

/**
 * Representation of the particular state of the game, split into sections operated by {@link GameStateTimer}. <br/>
 *
 * As this class is abstract, it can be instantiated directly. It is recommended to initialize it as an anonymous subclass, <i>final</i> and <i>static</i>: <pre>
 * public static final GameState gameState = new GameState("Game State", 60, 1000, true, true) {
 *     &#64Override;
 *     public void onInit() {
 *         // Code here...
 *     }
 * }
 * </pre>
 */
public abstract class GameState {

    // Constants
    /**
     * Plain, interruptible state with no functionality; replacement for the {@code null} value.
     */
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

    /**
     * Creates the new instance of {@code GameState} class with specific name and duration expressed in milliseconds, interruptible by default. As period is not specified, it is equal to {@code 1}.
     * @param name Name of this state
     * @param duration Duration of this state expressed in milliseconds
     * @param reverseTimer If {@code true}, timer will count back from the duration down to {@code 0}
     */
    public GameState(String name, long duration, boolean reverseTimer) {
        Validate.notNull(name, "name cannot be null");

        this.name = name;
        this.duration = duration;
        this.period = 1;
        this.reverseTimer = reverseTimer;
        this.interruptible = true;
    }

    /**
     * Creates the new instance of {@code GameState} class with specific name and duration of periods of specific length expressed in milliseconds.
     * @param name Name of this state
     * @param duration Duration of this state expressed periods
     * @param period Period duration expressed in milliseconds, time between two following timer iterations
     * @param reverseTimer If {@code true}, timer will count back from the duration down to {@code 0}
     * @param interruptible Determines if this state can be interrupted (ex. when other state is initialized meanwhile)
     */
    public GameState(String name, long duration, int period, boolean reverseTimer, boolean interruptible) {
        Validate.notNull(name, "name cannot be null");
        Validate.isTrue(period > 0, "period must be greater than 0");

        this.name = name;
        this.duration = duration;
        this.period = period;
        this.reverseTimer = reverseTimer;
        this.interruptible = interruptible;
    }

    // Getters

    /**
     * @return Name of this state
     */
    public String getName() {
        return name;
    }

    /**
     * @return Duration of this state expressed in periods
     */
    public long getDuration() {
        return duration;
    }

    /**
     * @return Duration of single period, time between two following timer iterations
     */
    public int getPeriod() {
        return period;
    }

    /**
     * @return {@code true} if the timer counting type shall be reversed
     */
    public boolean doesReverseTimer() {
        return reverseTimer;
    }

    /**
     * @return {@code true} if the state can be interrupted
     */
    public boolean isInterruptible() {
        return interruptible;
    }

    // Functionality

    /**
     * An action to be performed when the state is initialized.
     */
    public void onInit() { }

    /**
     * An action to be performed on each timer iteration.
     */
    public void tick() { }

    /**
     * An action to be performed any time the state is ended.
     */
    public void onEnd() { }

    /**
     * Called on each timer iteration, determines if state should be ended or not.
     * @return {@code true} if current state should be terminated, {@code false} by default
     */
    public boolean shouldEnd() {
        return false;
    }
}
