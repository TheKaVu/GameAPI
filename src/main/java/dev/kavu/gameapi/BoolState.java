package dev.kavu.gameapi;

public enum BoolState {
    TRUE (true),
    FALSE (false);

    private final boolean value;

    BoolState(boolean value) {
        this.value = value;
    }

    public boolean get() {
        return value;
    }
}
