package dev.kavu.gameapi.world;

import java.io.File;

/**
 * Representation of minecraft game map.
 *
 * @see MapManager
 */
public interface GameMap {

    /**
     * @return Name of the map
     */
    String getName();

    /**
     * @return Source directory origin map is located in
     */
    File getSource();
}
