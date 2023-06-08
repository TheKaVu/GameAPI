package dev.kavu.gameapi.world;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.function.Supplier;

public abstract class Area {

    // Fields
    private double sizeX;
    private double sizeY;
    private double sizeZ;
    private final Supplier<Location> center;
    private final AreaShape shape;

    // Constructors

    public Area(Supplier<Location> center, double x, double y, double z, AreaShape shape) {
        if(x < 0) throw new IllegalArgumentException("x was less than 0");
        if(y < 0) throw new IllegalArgumentException("y was less than 0");
        if(z < 0) throw new IllegalArgumentException("z was less than 0");

        this.center = center;
        this.sizeX = 2 * x;
        this.sizeY = 2 * y;
        this.sizeZ = 2 * z;
        this.shape = shape;

    }

    public Area(Location center, double x, double y, double z, AreaShape shape) {
        this(() -> center, x, y, z, shape);
    }

    // Getters & Setters
    public double getSizeX() {
        return sizeX;
    }

    public double getSizeY() {
        return sizeY;
    }

    public double getSizeZ() {
        return sizeZ;
    }

    public Location getCenter() {
        return center.get();
    }

    public AreaShape getShape() {
        return shape;
    }

    // Functionality
    public Area resize(int x, int y, int z){
        this.sizeX = x;
        this.sizeY = y;
        this.sizeZ = z;
        return this;
    }

    public Area scale(double scaleX, double scaleY, double scaleZ){
        sizeX *= scaleX;
        sizeY *= scaleY;
        sizeZ *= scaleZ;
        return this;
    }

    public Area scale(double scale){
        return scale(scale, scale, scale);
    }

    public boolean hasLocation(Location location){
        if(location == null){
            throw new NullPointerException();
        }
        if(location.getWorld() != getCenter().getWorld()) return false;

        return shape.compute(this, getCenter());
    }

    public boolean hasPlayer(Player player){
        if(player == null){
            throw new NullPointerException();
        }
        return hasLocation(player.getLocation()) || hasLocation(player.getEyeLocation());
    }

    public abstract void onEnter(Player player);

    public abstract void onMove(Player player);

    public abstract void onLeave(Player player);
}