package dev.kavu.gameapi;

import org.apache.commons.lang.Validate;

import java.util.Properties;

/**
 * Abstract class being the base for any game. Contains major information about the game. Games can also be categorized using {@link Category} interface.
 */
public abstract class Game {

    // Fields
    private final String name;

    private final String systemName;

    private final String prefix;

    private final int minPlayers;

    private final int maxPlayers;

    private final Properties properties;

    // Constructors

    /**
     * Creates the new instance of Game class with following values.
     * @param name Name of the game for the display use
     * @param systemName Systematic name of the game, without spaces and capital letters
     * @param prefix The prefix referencing to this game
     * @param minPlayers Minimum number of players needed to start the game
     * @param maxPlayers Maximum number of players able to play the same game
     */
    public Game(String name, String systemName, String prefix, int minPlayers, int maxPlayers) {
        this(name, systemName, prefix, minPlayers, maxPlayers, new Properties());
    }

    /**
     * Creates the new instance of Game class with following values.
     * @param name Name of the game for the display use
     * @param systemName Systematic name of the game, without spaces and capital letters
     * @param prefix The prefix referencing to this game
     * @param minPlayers Minimum number of players needed to start the game
     * @param maxPlayers Maximum number of players able to play the same game
     * @param properties Additional properties for the game
     */
    public Game(String name, String systemName, String prefix, int minPlayers, int maxPlayers, Properties properties) {
        Validate.notNull(name, "name cannot be null");
        Validate.notNull(systemName, "systemName cannot be null");
        Validate.notNull(prefix, "prefix cannot be null");
        Validate.isTrue(minPlayers <= maxPlayers, "minPlayers cannot be greater that maxPlayers");

        this.name = name;
        this.systemName = systemName;
        this.prefix = prefix;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.properties = properties;
        properties.setProperty("min_players", String.valueOf(minPlayers));
        properties.setProperty("max_players", String.valueOf(maxPlayers));
    }

    // Getters

    /**
     * @return Name of this game
     */
    public String getName() {
        return name;
    }

    /**
     * @return Systematic name of this game
     */
    public String getSystemName() {
        return systemName;
    }

    /**
     * @return Prefix associated with this game
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @return Minimum number of players
     */
    public int getMinPlayers() {
        return minPlayers;
    }

    /**
     * @return Maximum number of players
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * @return Properties of this game
     */
    public Properties getProperties() {
        return properties;
    }


    /**
     * Returns the category of this game represented by {@link Category} interface. The category can be separate object or the Game subclass itself.
     * @return Category of this game
     */
    public abstract Class<? extends Game> getCategory();

    // Overrides
    @Override
    public String toString(){
        return name;
    }
}
