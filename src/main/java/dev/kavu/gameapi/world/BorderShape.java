package dev.kavu.gameapi.world;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import org.bukkit.Location;

public interface BorderShape {
    boolean compute(Border border, Location pos);

    BorderShape SPHERE = (border, pos) -> {
        Location center = border.getCenter();
        Point3D centerPoint = new Point3D(center.getX(), center.getY(), center.getZ());
        Point3D posPoint = new Point3D(pos.getX(), pos.getY(), pos.getZ());

        return centerPoint.distance(posPoint) <= border.getRadius();
    };

    BorderShape CYLINDER = (border, pos) -> {
        Location center = border.getCenter();
        Point2D centerPoint = new Point2D(center.getX(), center.getZ());
        Point2D posPoint = new Point2D(pos.getX(), pos.getZ());

        return centerPoint.distance(posPoint) <= border.getRadius() && Math.abs(center.getY() - pos.getY()) <= border.getHeight();
    };

    BorderShape RECTANGLE = (border, pos) -> {
        Location center = border.getCenter();
        Point3D centerPoint = new Point3D(center.getX(), center.getY(), center.getZ());
        Point3D posPoint = new Point3D(pos.getX(), pos.getY(), pos.getZ());
        Point3D diff = centerPoint.subtract(posPoint);
        return Math.abs(diff.getX()) <= border.getRadius() && Math.abs(diff.getZ()) <= border.getRadius() && Math.abs(diff.getY()) <= border.getHeight();
    };
}
