package dev.kavu.gameapi.world;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import org.bukkit.Location;

public interface AreaShape {
    boolean compute(Area area, Location pos);

    AreaShape SPHERE = (area, pos) -> {
        Location center = area.getCenter();
        Point3D centerPoint = new Point3D(center.getX(), center.getY(), center.getZ());
        Point3D posPoint = new Point3D(pos.getX(), pos.getY(), pos.getZ());

        return (posPoint.getX() - centerPoint.getX()) / area.getSizeX() + (posPoint.getY() - centerPoint.getY()) / area.getSizeY() + (posPoint.getZ() - centerPoint.getZ()) / area.getSizeZ() <= 1;
    };

    AreaShape CYLINDER= (area, pos) -> {
        Location center = area.getCenter();
        Point2D centerPoint = new Point2D(center.getX(), center.getZ());
        Point2D posPoint = new Point2D(pos.getX(), pos.getZ());

        return (posPoint.getX() - centerPoint.getX()) / area.getSizeX() + (posPoint.getY() - centerPoint.getY()) / area.getSizeY() <= 1 && Math.abs(center.getY() - pos.getY()) <= area.getSizeZ();
    };

    AreaShape CUBOID = (area, pos) -> {
        Location center = area.getCenter();
        Point3D centerPoint = new Point3D(center.getX(), center.getY(), center.getZ());
        Point3D posPoint = new Point3D(pos.getX(), pos.getY(), pos.getZ());
        Point3D diff = centerPoint.subtract(posPoint);
        return Math.abs(diff.getX()) <= area.getSizeX() && Math.abs(diff.getY()) <= area.getSizeY() && Math.abs(diff.getZ()) <= area.getSizeZ();
    };
}
