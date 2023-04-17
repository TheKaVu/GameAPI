package dev.kavu.gameapi.world;

import org.bukkit.World;

public interface GameMap {

    boolean load();

    void unload();

    boolean restore();

    boolean isLoaded();

    World getWorld();
}
