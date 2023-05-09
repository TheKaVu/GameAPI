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

    public AreaPickEquivocationException(Area area1, Area area2){
        super("Equivocation between area: " + area1 + " and area:" + area2 + " while picking most prioritized - both have the same priority");
    }
}
