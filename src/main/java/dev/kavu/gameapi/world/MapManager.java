package dev.kavu.gameapi.world;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * This class manages all maps of one game. It allows loading, unloading and restoring maps for the game. It operates on {@link GameMap} objects which are representation of single game maps.
 */
public class MapManager {

    // Fields
    private File activeWorldFolder;

    private World world;

    private GameMap currentMap;

    // Constructor

    /**
     * Creates new instance of <tt>MapManager</tt> class.
     */
    public MapManager() {

    }

    // Getters & setters

    /**
     * @return Current loaded map; {@code null} if no map is loaded
     */
    public GameMap getCurrentMap() {
        return currentMap;
    }

    /**
     * @return World of currently loaded map
     */
    public World getWorld() {
        return world;
    }

    // Functionality

    /**
     * Loads specified map. If any map is loaded already, new map won't be loaded<br/>
     * Loading process consists in creating <i>new world</i> by copying its insides from source stored in {@link GameMap} object.
     * Player won't be warped to this world automatically, thus it has to be done by hand.
     * You can see how to do it in <a href="https://github.com/TheKaVu/GameAPI/wiki/World-Management">GameAPI wiki</a>
     * @param gameMap Game map to be loaded
     * @return {@code true} if map was loaded; {@code false} otherwise
     */
    public boolean load(GameMap gameMap){
        return load(gameMap, gameMap.getName() + "_" + System.currentTimeMillis());
    }

    /**
     * Loads specified map and designates the world name. If any map is loaded already, new map won't be loaded<br/>
     *      * Loading process consists in creating <i>new world</i> by copying its insides from source stored in {@link GameMap} object.
     *      * Player won't be warped to this world automatically, thus it has to be done by hand.
     *      * You can see how to do it in <a href="https://github.com/TheKaVu/GameAPI/wiki/World-Management">GameAPI wiki</a>
     *      * @param gameMap Game map to be loaded
     *      * @return {@code true} if map was loaded; {@code false} otherwise
     */
    public boolean load(GameMap gameMap, String worldName){
        Validate.notNull(gameMap, "map cannot be null");
        Validate.notNull(worldName, "worldName cannot be null");

        if(isLoaded()) return true;

        this.currentMap = gameMap;
        this.activeWorldFolder = new File(Bukkit.getWorldContainer().getParentFile(), worldName);

        try {
            FileUtils.copyDirectory(gameMap.getMapFolder(), activeWorldFolder);
        } catch (IOException ignored) {
            return false;
        }

        world = Bukkit.getServer().createWorld(new WorldCreator(activeWorldFolder.getName()));

        if(world != null) world.setAutoSave(false);

        return isLoaded();
    }

    /**
     * Unloads currently loaded map. That includes unloading and removing associated world and its directory.
     */
    public void unload() {

        if(world != null) {
            Bukkit.getServer().unloadWorld(world, false);
        }

        if(activeWorldFolder != null) {
            try {
                FileUtils.deleteDirectory(activeWorldFolder);
            } catch (IOException ignored) {
            }
        }

        world = null;
        activeWorldFolder = null;
    }

    /**
     * Restores - unloads and loads, currently stored map.
     * @return {@code true} if map was successfully restored; {@code false} if not
     */
    public boolean restore() {
        return restore(currentMap.clone());
    }

    /**
     * Unloads currently stored map and loads new one.
     * @return {@code true} if map was successfully restored; {@code false} if not
     */
    public boolean restore(GameMap gameMap){
        Validate.notNull(gameMap, "map cannot be null");
        unload();
        return load(gameMap);
    }

    /**
     * @return {@code true} if any map is already loaded; {@code false} otherwise
     */
    public boolean isLoaded() {
        return world != null && activeWorldFolder != null && currentMap != null;
    }
}
