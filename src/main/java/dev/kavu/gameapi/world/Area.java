package dev.kavu.gameapi.world;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.function.Supplier;

/**
 * Representation of 3-dimensional space in particular world. This space can be checked if a location or player is inside or not. Area shape is defined using {@link AreaShape} object.
 *
 * @see PlainArea
 */
public abstract class Area {

    // Fields
    private double sizeX;
    private double sizeY;
    private double sizeZ;
    private final Supplier<Location> center;
    private final AreaShape shape;

    // Constructors

    /**
     * Creates new instance of <tt>Area</tt> class with specific size and shape and dynamic center. {@link Supplier} object allows moving the area in a particular manner. <p/>
     * <b>Note: </b> All sizes are treated as radius, what makes the real size two times bigger.
     * @param center Center of the area
     * @param x Size of the area on X axis
     * @param y Size of the area on Y axis
     * @param z Size of the area on Z axis
     * @param shape Shape of the area represented by {@link AreaShape} object
     */
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
    /**
     * Creates new instance of <tt>Area</tt> class with specific size and shape and static center. <p/>
     *      * <b>Note: </b> All sizes are treated as radius, what makes the real size two times bigger.
     * @param center Center of the area
     * @param x Size of the area on X axis
     * @param y Size of the area on Y axis
     * @param z Size of the area on Z axis
     * @param shape Shape of the area represented by {@link AreaShape} object
     */
    public Area(Location center, double x, double y, double z, AreaShape shape) {
        this(() -> center, x, y, z, shape);
    }

    // Getters & Setters

    /**
     * @return Size of the area on X axis
     */
    public double getSizeX() {
        return sizeX;
    }

    /**
     * @return Size of the area on X axis
     */
    public double getSizeY() {
        return sizeY;
    }

    /**
     * @return Size of the area on X axis
     */
    public double getSizeZ() {
        return sizeZ;
    }

    /**
     * @return Current center of the area
     */
    public Location getCenter() {
        return center.get();
    }

    /**
     * @return Shape of the area represented by {@link AreaShape} object
     */
    public AreaShape getShape() {
        return shape;
    }

    // Functionality

    /**
     * Changes each size of the area and returns itself
     * @param x New x size
     * @param y New y size
     * @param z New z size
     * @return Resized area
     *
     * @see #scale
     */
    public Area resize(double x, double y, double z){
        Validate.isTrue(x >= 0, "x must be greater or equal to 0; x: ", x);
        Validate.isTrue(y >= 0, "y must be greater or equal to 0; y: ", y);
        Validate.isTrue(z >= 0, "z must be greater or equal to 0; z: ", z);

        this.sizeX = x;
        this.sizeY = y;
        this.sizeZ = z;
        return this;
    }

    /**
     * Scales whole area by multiplying each size of the area by specific scale and returns itself
     * @param scaleX X size multiplier
     * @param scaleY Y size multiplier
     * @param scaleZ Z size multiplier
     * @return Scaled area
     *
     * @see #resize
     */
    public Area scale(double scaleX, double scaleY, double scaleZ){
        Validate.isTrue(scaleX >= 0, "scaleX must be greater or equal to 0; scaleX: ", scaleX);
        Validate.isTrue(scaleY >= 0, "scaleY must be greater or equal to 0; scaleY: ", scaleY);
        Validate.isTrue(scaleZ >= 0, "scaleZ must be greater or equal to 0; scaleZ: ", scaleZ);

        sizeX *= scaleX;
        sizeY *= scaleY;
        sizeZ *= scaleZ;
        return this;
    }

    /**
     * Scales whole area with single multiplier
     * @param scale Size multiplier
     * @return Scaled area
     *
     * @see #resize
     */
    public Area scale(double scale){
        Validate.isTrue(scale >= 0, "scale must be greater or equal to 0; scale: ", scale);

        return scale(scale, scale, scale);
    }

    /**
     * Indicates if location is within the bounds of the area
     * @param location Location to be checked
     * @return {@code true} if location is in the area; {@code false} if is not
     */
    public boolean hasLocation(Location location){
        Validate.notNull(location, "location cannot be null");

        if(location.getWorld() != getCenter().getWorld()) return false;

        return shape.compute(this, getCenter());
    }

    /**
     * Indicates if player is within the bounds of the area. That includes player's location and eye location.
     * @param player Players to be checked
     * @return {@code true} if players is in the area; {@code false} if is not
     */
    public boolean hasPlayer(Player player){
        Validate.notNull(player, "player cannot be null");

        return hasLocation(player.getLocation()) || hasLocation(player.getEyeLocation());
    }

    /**
     * Called when player enters this area.
     * @param player Player that entered the area
     */
    public abstract void onEnter(Player player);

    /**
     * Called when player moves inside this area.
     * @param player Player that moved in the area
     */
    public abstract void onMove(Player player);

    /**
     * Called when player leaves this area, no matter if enters new one or not.
     * @param player Player that left the area
     */
    public abstract void onLeave(Player player);
}