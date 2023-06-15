package dev.kavu.gameapi;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class GameEngine extends JavaPlugin {

    public abstract Game getGameType();

    public abstract GameManager getGameManager();
}
