package dev.kavu.gameapi.statistic;

import org.apache.commons.lang.Validate;

import java.util.HashMap;
import java.util.UUID;

public abstract class LockableStatistic<T extends Number> implements Statistic<T>{

    private boolean locked;

    private final HashMap<UUID, Boolean> locks = new HashMap<>();

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setLocked(UUID member, boolean locked){
        Validate.notNull(member, "member cannot be null");

        locks.replace(member, locked);
    }

    @Condition(negate = true)
    public boolean isLocked() {
        return locked;
    }

    @Condition(negate = true)
    public boolean isLocked(UUID member) {
        Validate.notNull(member, "member cannot be null");

        return locks.getOrDefault(member, true);
    }
}
