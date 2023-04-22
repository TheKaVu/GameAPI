package dev.kavu.gameapi;

import org.bukkit.World;

import java.io.File;

public interface GameMap {

    void onLoad(World world);

    void onUnload();

    String getName();

    File getSourceFolder();
}
