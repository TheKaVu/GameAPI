package dev.kavu.gameapi.world;

import java.io.File;

/**
 * Representation of minecraft game map.
 *
 * @see MapManager
 */
public interface GameMap extends Cloneable{

    /**
     * @return Name of the map
     */
    String getName();

    /**
     * @return Source directory origin map is located in
     */
    File getMapFolder();

    /**
     * The stricter version of {@link Cloneable#clone() clone()} function form {@link Cloneable} interface, with return type of this interface.
     * @return Clone of this object
     *
     * @see Cloneable#clone()
     */
    GameMap clone();
}
