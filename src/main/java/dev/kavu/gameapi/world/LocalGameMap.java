package dev.kavu.gameapi.world;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;


import java.io.File;
import java.io.IOException;

public class LocalGameMap implements GameMap{

    private final File source;

    private File activeWorldFolder;

    private World world;

    public LocalGameMap(File sourceFolder, String worldName, boolean loadOnInit){
        this.source = new File(sourceFolder, worldName);
        if(loadOnInit) load();
    }

    @Override
    public boolean load(){
        if(isLoaded()) return true;

        this.activeWorldFolder = new File(Bukkit.getWorldContainer().getParentFile(), source.getName() + "_active");

        try {
            FileUtils.copyDirectory(source, activeWorldFolder);
        } catch (IOException ignored) {
            return false;
        }

        world = Bukkit.createWorld(new WorldCreator(activeWorldFolder.getName()));
        if(world != null) world.setAutoSave(false);

        return isLoaded();
    }

    @Override
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
    }

    @Override
    public boolean restore() {
        unload();
        return load();
    }

    @Override
    public boolean isLoaded() {
        return world != null && activeWorldFolder != null;
    }

    @Override
    public World getWorld() {
        return world;
    }
}
