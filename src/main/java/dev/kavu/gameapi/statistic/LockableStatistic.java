package dev.kavu.gameapi.statistic;

public abstract class LockableStatistic<T extends Number> implements Statistic<T>{

    private boolean locked;

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isLocked() {
        return locked;
    }

}
