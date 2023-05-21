package dev.kavu.gameapi.statistic;

import java.util.HashMap;
import java.util.UUID;

public abstract class LockableStatistic<T extends Number> implements Statistic<T>{

    private boolean locked;

    private final HashMap<UUID, Boolean> locks = new HashMap<>();

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setLocked(UUID member, boolean locked){
        locks.replace(member, locked);
    }

    @Condition(negate = true)
    public boolean isLocked() {
        return locked;
    }

    @Condition(negate = true)
    public boolean isLocked(UUID member) {
        return locks.getOrDefault(member, true);
    }
}
