package dev.kavu.gameapi.world;

import org.apache.commons.lang.Validate;

import java.io.File;
import java.util.Random;

public class RandomGameMap implements GameMap, Cloneable{

    private final File source;
    private File mapFolder;
    private String name;

    public RandomGameMap(File source) throws MapCreationException {
        Validate.notNull(source, "sourceFolder cannot be null");
        if(source.exists()) throw new MapCreationException("Following directory does not exist: " + source.getAbsolutePath());
        Validate.isTrue(source.isDirectory(), "sourceFolder has to be a directory");

        this.source = source;
        randomize();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public File getMapFolder() {
        return mapFolder;
    }

    public File getSource() {
        return source;
    }

    public void randomize() throws MapCreationException {
        File[] mapFiles = source.listFiles((f ,s) -> f.isDirectory());
        if (mapFiles.length == 0) throw new MapCreationException("Following directory is empty: " + source.getAbsolutePath());

        Random random = new Random();
        int i = random.nextInt(mapFiles.length);
        mapFolder = mapFiles[i];
        name = mapFolder.getName();


    }

    @Override
    public RandomGameMap clone() {
        try {
            return new RandomGameMap(source);
        } catch (MapCreationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
