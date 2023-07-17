package dev.kavu.gameapi.world;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import org.bukkit.Location;

/**
 * Representation of 3-Dimensional shape created using {@link Area} object. <br/>
 * This interface contains predefined, usually used shapes.
 *
 * @see #SPHERE
 * @see #CYLINDER
 * @see #CUBOID
 */
public interface AreaShape {

    /**
     * Determines if specific location shall be in the specific area
     * @param area {@link Area} object to depend on
     * @param pos Location to be checked
     * @return {@code true} if location shall be in the specified area; {@code false} if shall not
     */
    boolean compute(Area area, Location pos);

    /**
     * Ellipsoid shape with each radius equal to area's <i>x</i>, <i>y</i> and <i>z</i> size
     */
    AreaShape SPHERE = (area, pos) -> {
        Location center = area.getCenter();
        Point3D centerPoint = new Point3D(center.getX(), center.getY(), center.getZ());
        Point3D posPoint = new Point3D(pos.getX(), pos.getY(), pos.getZ());

        return (posPoint.getX() - centerPoint.getX()) / area.getSizeX() + (posPoint.getY() - centerPoint.getY()) / area.getSizeY() + (posPoint.getZ() - centerPoint.getZ()) / area.getSizeZ() <= 1;
    };

    /**
     * Vertical cylinder shape with base of ellipse with radius equal to area's <i>x</i> and <i>z</i> size, and height equal to doubled <i>z</i> size
     */
    AreaShape CYLINDER= (area, pos) -> {
        Location center = area.getCenter();
        Point2D centerPoint = new Point2D(center.getX(), center.getZ());
        Point2D posPoint = new Point2D(pos.getX(), pos.getZ());

        return (posPoint.getX() - centerPoint.getX()) / area.getSizeX() + (posPoint.getY() - centerPoint.getY()) / area.getSizeZ() <= 1 && Math.abs(center.getY() - pos.getY()) <= area.getSizeY();
    };

    /**
     * Cuboid shape with borders' size equal to doubled area's <i>x</i>, <i>y</i> and <i>z</i> size
     */
    AreaShape CUBOID = (area, pos) -> {
        Location center = area.getCenter();
        Point3D centerPoint = new Point3D(center.getX(), center.getY(), center.getZ());
        Point3D posPoint = new Point3D(pos.getX(), pos.getY(), pos.getZ());
        Point3D diff = centerPoint.subtract(posPoint);
        return Math.abs(diff.getX()) <= area.getSizeX() && Math.abs(diff.getY()) <= area.getSizeY() && Math.abs(diff.getZ()) <= area.getSizeZ();
    };
}
