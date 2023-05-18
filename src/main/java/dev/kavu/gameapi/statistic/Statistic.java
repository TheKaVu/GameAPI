package dev.kavu.gameapi.statistic;

import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public interface Statistic<T extends Number> {

    T getDefault();

    Set<Trigger<?>> getTriggers();

    StatisticRegistry<T> getNewRegistry(Plugin plugin);

    StatisticRegistry<T> getNewRegistry(Collection<UUID> initialMembers, Plugin plugin);
}
