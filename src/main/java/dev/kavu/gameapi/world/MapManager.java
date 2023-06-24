package dev.kavu.gameapi.world;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class MapManager {

    // Fields
    private File activeWorldFolder;

    private World world;

    private GameMap currentMap;

    // Constructor
    public MapManager() {

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
    }

    public boolean restore() {
        return restore(currentMap);
    }

    public boolean restore(GameMap gameMap){
        Validate.notNull(gameMap, "map cannot be null");
        unload();
        return load(gameMap);
    }

    public boolean isLoaded() {
        return world != null && activeWorldFolder != null && currentMap != null;
    }

    public GameMap createMap(File sourceFolder, String mapName, boolean autoLoad) throws MapCreationException{
        Validate.notNull(sourceFolder, "sourceFolder cannot be null");
        Validate.isTrue(sourceFolder.isDirectory(), "sourceFolder has to be a directory");
        Validate.notNull(mapName, "mapName cannot be null");

        File file = new File(sourceFolder, mapName);

        if(!file.exists()) throw new MapCreationException("Following directory does not exist: " + file.getAbsolutePath());
        if(!file.isDirectory()) throw new MapCreationException("Subdirectory of given name is not a directory");

        GameMap map = new GameMap() {

            private final File source = file;

            @Override
            public void onLoad(World world) {}

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

    public GameMap randomizeMap(File sourceFolder, boolean autoLoad) throws MapCreationException {
        Validate.isTrue(sourceFolder.isDirectory(), "sourceFolder has to be a directory");

        Random random = new Random();
        File[] files = sourceFolder.listFiles(file -> file.isDirectory());
        if(files == null) throw new MapCreationException();
        if (files.length == 0) throw new MapCreationException("There are no subdirectories in path: " + sourceFolder.getAbsolutePath());

        return createMap(sourceFolder, files[random.nextInt(files.length)].getName(), autoLoad) ;
    }
}
