package dev.kavu.gameapi;

/**
 * An enum representing {@code boolean} value.
 */
public enum BoolState {

    /**
     * Represents {@code false} value
     */
    FALSE (false),

    /**
     * Represents {@code true} value
     */
    TRUE (true);

    private final boolean value;

    BoolState(boolean value) {
        this.value = value;
    }

    /**
     * @return {@code true} if equal to {@link #FALSE}, {@code false} otherwise
     */
    public boolean isFalse() {
        return !value;
    }

    /**
     * @return {@code true} if equal to {@link #TRUE}, {@code false} otherwise
     */
    public boolean isTrue() {
        return value;
    }
}
