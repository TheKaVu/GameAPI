package dev.kavu.gameapi.world;

public class AreaPickEquivocationException extends RuntimeException{

    public AreaPickEquivocationException(){
        super();
    }

    public AreaPickEquivocationException(String message){
        super(message);
    }

    public AreaPickEquivocationException(Throwable cause){
        super(cause);
    }

    public AreaPickEquivocationException(String message, Throwable cause){
        super(message, cause);
    }
}
