package dev.kavu.gameapi.world;

import org.apache.commons.lang.Validate;
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
        Validate.notNull(center, "center cannot be null");
        Validate.notNull(shape, "shape cannot be null");
        Validate.isTrue(x >= 0, "x must be greater or equal to 0; x: ", x);
        Validate.isTrue(y >= 0, "y must be greater or equal to 0; y: ", y);
        Validate.isTrue(z >= 0, "z must be greater or equal to 0; z: ", z);

        this.center = center;
        this.sizeX = x;
        this.sizeY = y;
        this.sizeZ = z;
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
        Validate.isTrue(x >= 0, "x must be greater or equal to 0; x: ", x);
        Validate.isTrue(y >= 0, "y must be greater or equal to 0; y: ", y);
        Validate.isTrue(z >= 0, "z must be greater or equal to 0; z: ", z);

        this.sizeX = x;
        this.sizeY = y;
        this.sizeZ = z;
        return this;
    }

    public Area scale(double scaleX, double scaleY, double scaleZ){
        Validate.isTrue(scaleX >= 0, "scaleX must be greater or equal to 0; scaleX: ", scaleX);
        Validate.isTrue(scaleY >= 0, "scaleY must be greater or equal to 0; scaleY: ", scaleY);
        Validate.isTrue(scaleZ >= 0, "scaleZ must be greater or equal to 0; scaleZ: ", scaleZ);

        sizeX *= scaleX;
        sizeY *= scaleY;
        sizeZ *= scaleZ;
        return this;
    }

    public Area scale(double scale){
        Validate.isTrue(scale >= 0, "scale must be greater or equal to 0; scale: ", scale);

        return scale(scale, scale, scale);
    }

    public boolean hasLocation(Location location){
        Validate.notNull(location, "location cannot be null");

        if(location.getWorld() != getCenter().getWorld()) return false;

        return shape.compute(this, getCenter());
    }

    public boolean hasPlayer(Player player){
        Validate.notNull(player, "player cannot be null");

        return hasLocation(player.getLocation()) || hasLocation(player.getEyeLocation());
    }

    public abstract void onEnter(Player player);

    public abstract void onMove(Player player);

    public abstract void onLeave(Player player);
}