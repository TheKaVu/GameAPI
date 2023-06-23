package dev.kavu.gameapi.world;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;

public class MapManager {

    // Fields
    private File activeWorldFolder;

    private World world;

    private GameMap currentMap;

    // Constructor
    public MapManager(String mainFolder) {

    }

    // Getters & setters
    public GameMap getCurrentMap() {
        return currentMap;
    }

    public World getWorld() {
        return world;
    }

    // Functionality

    public boolean load(GameMap gameMap){
        return load(gameMap, gameMap.getName() + "_" + System.currentTimeMillis());
    }

    public boolean load(GameMap gameMap, String worldName){
        Validate.notNull(gameMap, "map cannot be null");
        Validate.notNull(worldName, "worldName cannot be null");

        if(isLoaded()) return true;

        this.currentMap = gameMap;
        this.activeWorldFolder = new File(Bukkit.getWorldContainer().getParentFile(), worldName);

        try {
            FileUtils.copyDirectory(gameMap.getSource(), activeWorldFolder);
        } catch (IOException ignored) {
            return false;
        }

        world = Bukkit.getServer().createWorld(new WorldCreator(activeWorldFolder.getName()));

        if(world != null) world.setAutoSave(false);

        gameMap.onLoad(world);

        return isLoaded();
    }

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
        currentMap = null;
    }

    public boolean restore(){
        unload();
        return load(currentMap);
    }

    public boolean restore(GameMap gameMap){
        Validate.notNull(gameMap, "map cannot be null");
        unload();
        return load(gameMap);
    }

    public boolean isLoaded() {
        return world != null && activeWorldFolder != null && currentMap != null;
    }

    public GameMap createMap(File sourceFolder, String mapName, boolean autoLoad){
        Validate.notNull(sourceFolder, "sourceFolder cannot be null");
        Validate.notNull(mapName, "mapName cannot be null");

        GameMap map = new GameMap() {

            private final File source = new File(sourceFolder, mapName);

            @Override
            public void onLoad(World world) {

            }

            @Override
            public String getName() {
                return mapName;
            }

            @Override
            public File getSource() {
                return source;
            }

        };
        if(autoLoad) load(map);

        return map;
    }
}
