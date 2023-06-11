package dev.kavu.gameapi.world;

import org.apache.commons.io.FileUtils;
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
        this.mainFolder = new File(mainFolder);
    }

    public MapManager(File mainFolder){
        if(mainFolder == null){
            throw new NullPointerException();
        }
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

    public boolean load(GameMap map){

        if(map == null) {
            throw new NullPointerException();
        }

        if(isLoaded()) return true;

        this.currentMap = map;
        this.activeWorldFolder = new File(Bukkit.getWorldContainer().getParentFile(), map.getName() + "_active");

        try {
            FileUtils.copyDirectory(map.getSourceFolder(), activeWorldFolder);
        } catch (IOException ignored) {
            return false;
        }

        world = Bukkit.createWorld(new WorldCreator(activeWorldFolder.getName()));
        if(world != null) world.setAutoSave(false);

        map.onLoad(world);

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
        unload();
        return load(gameMap != null ? gameMap : currentMap);
    }

    public boolean isLoaded() {
        return world != null && activeWorldFolder != null && currentMap != null;
    }

    public GameMap createMap(String mapPath , boolean autoLoad){
        if(mapPath == null){
            throw new NullPointerException();
        }
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
