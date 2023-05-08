package dev.kavu.gameapi.world;

import org.bukkit.World;

import java.io.File;

public interface GameMap {

    void onLoad(World world);

    void onUnload();

    String getName();

    File getSourceFolder();
}
