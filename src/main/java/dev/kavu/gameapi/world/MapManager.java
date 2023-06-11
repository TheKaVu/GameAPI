package dev.kavu.gameapi.world;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MapManager {

    // Fields
    private final File mainFolder;

    private File activeWorldFolder;

    private World world;

    private GameMap currentMap;

    // Constructor
    public MapManager(String mainFolder){
        Validate.notNull(mainFolder, "mainFolder cannot be null");

        this.mainFolder = new File(mainFolder);
    }

    public MapManager(File mainFolder){
        Validate.notNull(mainFolder, "mainFolder cannot be null");

        this.mainFolder = mainFolder;
    }

    // Getters & setters
    public File getMainFolder() {
        return mainFolder;
    }

    public GameMap getCurrentMap() {
        return currentMap;
    }

    public World getWorld() {
        return world;
    }

    // Functionality

    public boolean load(GameMap gameMap){
        Validate.notNull(gameMap, "map cannot be null");

        if(isLoaded()) return true;

        this.currentMap = gameMap;
        this.activeWorldFolder = new File(Bukkit.getWorldContainer().getParentFile(), gameMap.getName() + "_active");

        try {
            FileUtils.copyDirectory(gameMap.getSourceFolder(), activeWorldFolder);
        } catch (IOException ignored) {
            return false;
        }

        world = Bukkit.createWorld(new WorldCreator(activeWorldFolder.getName()));
        if(world != null) world.setAutoSave(false);

        gameMap.onLoad(world);

        return isLoaded();
    }

    public void unload() {

        if(world != null && currentMap != null) {
            Bukkit.unloadWorld(world, false);
            currentMap.onUnload(world);
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

    public GameMap createMap(String mapPath , boolean autoLoad){
        Validate.notNull(mapPath, "mapPath cannot be null");

        GameMap map = new GameMap() {

            @Override
            public void onLoad(World world) {

            }

            @Override
            public void onUnload(World world) {

            }

            @Override
            public String getName() {
                return getSourceFolder().getName();
            }

            @Override
            public File getSourceFolder() {
                return new File(mainFolder + mapPath);
            }

        };
        if(autoLoad) load(map);

        return map;
    }
}
