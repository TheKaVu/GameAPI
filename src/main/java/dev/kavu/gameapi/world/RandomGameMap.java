package dev.kavu.gameapi.world;

import org.apache.commons.lang.Validate;

import java.io.File;
import java.util.Random;

/**
 * Represents a map randomizer being picked map itself.
 *
 * @see GameMap
 * @see LocalGameMap
 */
public class RandomGameMap implements GameMap, Cloneable{

    private final File source;
    private File mapFolder;
    private String name;

    /**
     * Creates randomizer that will pick map folders from the source directory
     * @param source Directory to pick map folders from
     * @throws MapCreationException When map cannot be created due to invalid data
     */
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

    /**
     * @return Directory maps are being picked from
     */
    public File getSource() {
        return source;
    }

    /**
     * Picks random map folder inside source directory and makes itself a map representation
     * @throws MapCreationException When map cannot be created due to invalid data
     */
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
