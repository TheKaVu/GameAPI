package dev.kavu.gameapi;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * The abstract class being the root for all the game engine plugins. It works as the JavaPlugin class, but provides some additional methods significant for the game.
 */
public abstract class GameEngine extends JavaPlugin {

    /**
     * @return Game info represented by {@link Game} object
     */
    public abstract Game getGame();

    /**
     * @return {@link GameManager} object, which controls the whole game
     */
    public abstract GameManager getGameManager();
}
