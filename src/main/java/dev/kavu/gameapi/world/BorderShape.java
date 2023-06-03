package dev.kavu.gameapi.world;

import com.sun.javafx.geom.Vec3d;

public interface BorderShape {
    boolean compute(double radius, double height, Vec3d pos);
}
