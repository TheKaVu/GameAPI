package dev.kavu.gameapi.statistic;

import org.apache.commons.lang.Validate;

import java.util.HashMap;
import java.util.UUID;

/**
 * An abstract class providing the locking mechanic to {@link Statistic} interface. <br/>
 * To classify <tt>LockableStatistic</tt> as one of predefined ones, generic parameters has to be equal, as shown below:
 * <pre>
 *      class IntLockable extends LockableStatistic&#60;Integer&#62; implements IntStatistic {
 *
 *      }
 * </pre>
 * @param <T> Numeric type of this statistic
 */
public abstract class LockableStatistic<T extends Number> implements Statistic<T>{

    private boolean locked;

    private final HashMap<UUID, Boolean> locks = new HashMap<>();

    /**
     * Locks or unlocks whole statistic.
     * @param locked {@code true} to lock, {@code false} to unlock this statistic
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * Locks or unlocks statistic for single member. Locked statistic's value cannot be altered in any way.
     * @param locked {@code true} to lock, {@code false} to unlock this statistic
     * @param member Member this statistic is going to be locked for
     */
    public void setLocked(UUID member, boolean locked){
        Validate.notNull(member, "member cannot be null");

        locks.replace(member, locked);
    }

    /**
     * @return {@code true} if this statistic is locked, {@code false} otherwise
     */
    @Condition(negate = true)
    public boolean isLocked() {
        return locked;
    }

    /**
     * Checks if statistic is locked for particular member.
     * @param member Member this statistic can be locked for
     * @return {@code true} if statistic is locked for specified member, {@code false} otherwise
     */
    @Condition(negate = true)
    public boolean isLocked(UUID member) {
        Validate.notNull(member, "member cannot be null");

        return locks.getOrDefault(member, true);
    }
}
