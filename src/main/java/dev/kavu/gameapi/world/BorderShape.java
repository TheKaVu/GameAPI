package dev.kavu.gameapi.world;

import org.bukkit.Location;

public interface BorderShape {
    boolean compute(Border border, Location pos);
}
