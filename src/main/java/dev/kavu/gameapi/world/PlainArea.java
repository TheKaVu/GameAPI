package dev.kavu.gameapi.world;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlainArea extends Area{

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
