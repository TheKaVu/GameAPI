package dev.kavu.gameapi.world;

public class MapCreationException extends Exception{

    public MapCreationException(){
        super("Couldn't successfully create new map");
    }

    public MapCreationException(String message){
        super(message);
    }
}
