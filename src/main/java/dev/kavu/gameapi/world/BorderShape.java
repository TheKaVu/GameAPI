package dev.kavu.gameapi.world;

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
}
