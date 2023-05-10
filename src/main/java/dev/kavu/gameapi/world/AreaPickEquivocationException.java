package dev.kavu.gameapi.world;

public class AreaPickEquivocationException extends RuntimeException{

    public AreaPickEquivocationException(){
        super("Equivocation while picking the most prioritized area");
    }

    public AreaPickEquivocationException(String message){
        super(message);
    }
}
