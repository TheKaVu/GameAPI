package dev.kavu.gameapi.world;

import org.bukkit.Location;

public class Border {

    // Fields
    private double radius;
    private double height;
    private final Location center;
    private final BorderShape borderShape;

    // Constructor
    public Border(double radius, double height, Location center, BorderShape borderShape) {
        this.radius = radius;
        this.height = height;
        this.center = center;
        this.borderShape = borderShape;
    }

    // Getters
    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Location getCenter() {
        return center;
    }

    public boolean hasLocation(Location location) {
        if(location.getWorld() != center.getWorld()) return false;

        return borderShape.compute(this, center);
    }

    public Border scale(double radiusScale, double heightScale){
        setRadius(getRadius() * radiusScale);
        setHeight(getHeight() * heightScale);
        return this;
    }

    public Border resize(double radiusOff, double heightOff) {
        setRadius(getRadius() + radius);
        setHeight(getHeight() + height);
        return this;
    }
}
