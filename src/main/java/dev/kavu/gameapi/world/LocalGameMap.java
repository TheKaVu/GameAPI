package dev.kavu.gameapi.world;

import org.apache.commons.lang.Validate;

import java.io.File;

public class LocalGameMap implements GameMap, Cloneable{

    private final File mapFolder;
    private final String name;

    public LocalGameMap(File mapFolder) throws MapCreationException {
        Validate.notNull(mapFolder, "mapFolder cannot be null");
        if(mapFolder.exists()) throw new MapCreationException("Following directory does not exist: " + mapFolder.getAbsolutePath());
        Validate.isTrue(mapFolder.isDirectory(), "mapFolder has to be a directory");

        this.mapFolder = mapFolder;
        this.name = mapFolder.getName();
    }

    public LocalGameMap(File mapFolder, String name) throws MapCreationException {
        Validate.notNull(mapFolder, "mapFolder cannot be null");
        if(mapFolder.exists()) throw new MapCreationException("Following directory does not exist: " + mapFolder.getAbsolutePath());
        Validate.isTrue(mapFolder.isDirectory(), "mapFolder has to be a directory");
        Validate.notNull(name, "name cannot be null");

        this.mapFolder = mapFolder;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public File getMapFolder() {
        return mapFolder;
    }

    @Override
    public LocalGameMap clone() {
        try {
            return new LocalGameMap(mapFolder, name);
        } catch (MapCreationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
