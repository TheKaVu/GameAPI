package dev.kavu.gameapi.world;

import org.bukkit.World;

import java.io.File;

/**
 * Representation of minecraft game map.
 */
public interface GameMap {

    /**
     * Called when map is loaded.
     * @param world Map world
     */
    void onLoad(World world);

    /**
     * @return Name of the map
     */
    String getName();

    /**
     * @return Source directory origin map is located in
     */
    File getSource();
}
