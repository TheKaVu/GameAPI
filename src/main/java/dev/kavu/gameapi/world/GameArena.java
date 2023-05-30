package dev.kavu.gameapi.world;

import dev.kavu.gameapi.AffectTarget;
import org.bukkit.Location;

public class GameArena extends Area{

    public GameArena(Location center, int xRadius, int yRadius, int zRadius) {
        super(center, xRadius, yRadius, zRadius, AffectTarget.PLAYERS_ONLY, true);
    }


}
