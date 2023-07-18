package dev.kavu.gameapi.world;

/**
 * Thrown when area choice for specific location is ambiguous - when two overlapping areas have same priority.
 */
public class AreaPickEquivocationException extends RuntimeException{

    public AreaPickEquivocationException(){
        super("Equivocation while picking the most prioritized area");
    }

    public AreaPickEquivocationException(String message){
        super(message);
    }
}
