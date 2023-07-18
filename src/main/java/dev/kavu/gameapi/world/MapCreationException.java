package dev.kavu.gameapi.world;

/**
 * Thrown where game map cannot be created with given data.
 */
public class MapCreationException extends Exception{

    public MapCreationException(){
        super("Couldn't successfully create new map");
    }

    public MapCreationException(String message){
        super(message);
    }
}
