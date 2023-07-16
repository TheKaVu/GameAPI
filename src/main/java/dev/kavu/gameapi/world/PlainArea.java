package dev.kavu.gameapi.world;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.function.Supplier;

/**
 * Simple area with no enter, move and leave events. In other cases it behaves in the same way any other area does.
 *
 * @see Area
 */
public class PlainArea extends Area {

    /**
     * Creates new instance of <tt>PlainArea</tt> in the same way as in {@link Area} class.
     * @param center Center of the area
     * @param x Size of the area on X axis
     * @param y Size of the area on Y axis
     * @param z Size of the area on Z axis
     * @param shape Shape of the area represented by {@link AreaShape} object
     *
     * @see Area#Area(Supplier, double, double, double, AreaShape)
     */
    public PlainArea(Supplier<Location> center, double x, double y, double z, AreaShape shape) {
        super(center, x, y, z, shape);
    }

    /**
     * Creates new instance of <tt>PlainArea</tt> in the same way as in {@link Area} class.
     * @param center Center of the area
     * @param x Size of the area on X axis
     * @param y Size of the area on Y axis
     * @param z Size of the area on Z axis
     * @param shape Shape of the area represented by {@link AreaShape} object
     *
     * @see Area#Area(Location, double, double, double, AreaShape)
     */
    public PlainArea(Location center, double x, double y, double z, AreaShape shape) {
        super(center, x, y, z, shape);
    }

    @Override
    public void onEnter(Player player) {

    }

    @Override
    public void onMove(Player player) {

    }

    @Override
    public void onLeave(Player player) {

    }
}
