package dev.kavu.gameapi.statistic;

import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.UUID;

public abstract class LockableStatistic<T extends Number> implements Statistic<T>{

    private boolean locked;

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Condition(negate = true)
    public boolean isLocked() {
        return locked;
    }

    @Override
    public StatisticRegistry<T> getNewRegistry(Plugin plugin){
        return new StatisticRegistry<>(this, plugin);
    }

    @Override
    public StatisticRegistry<T> getNewRegistry(Collection<UUID> initialMembers, Plugin plugin) {
        return new StatisticRegistry<>(this, initialMembers, plugin);
    }
}
