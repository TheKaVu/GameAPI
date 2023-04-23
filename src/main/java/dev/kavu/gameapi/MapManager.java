package dev.kavu.gameapi;

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
        if(isLoaded()) return true;

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
        if(world != null) Bukkit.unloadWorld(world, false);
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

    public boolean restore(Supplier<GameMap> mapSupplier){
        unload();
        return load(mapSupplier != null ? mapSupplier.get() : currentMap);
    }

    public boolean isLoaded() {
        return world != null && activeWorldFolder != null && currentMap != null;
    }

    public GameMap createMap(String mapPath , boolean autoLoad){
        GameMap map = new GameMap() {

            @Override
            public void onLoad(World world) {

            }

            @Override
            public void onUnload() {

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

    public GameMap createMap(String mapPath, Consumer<World> onLoadAction, Runnable onUnloadAction , boolean autoLoad){
        GameMap map = new GameMap() {

            @Override
            public void onLoad(World world) {
                if(onLoadAction != null) onLoadAction.accept(world);
            }

            @Override
            public void onUnload() {
                if(onUnloadAction != null) onUnloadAction.run();
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
