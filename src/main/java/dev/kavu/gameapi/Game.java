package dev.kavu.gameapi;

import org.apache.commons.lang.Validate;

import java.util.Properties;

public abstract class Game {

    // Fields
    private final String name;

    private final String systemName;

    private final String prefix;

    private final int minPlayers;

    private final int maxPlayers;

    private final Properties properties;

    private final Class<? extends Category<Game>> category;

    // Constructors
    public Game(String name, String systemName, String prefix, int minPlayers, int maxPlayers, Class<? extends Category<Game>> category) {
        this(name, systemName, prefix, minPlayers, maxPlayers, new Properties(), category);
    }

    public Game(String name, String systemName, String prefix, int minPlayers, int maxPlayers, Properties properties, Class<? extends Category<Game>> category) {
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
        this.category = category;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getSystemName() {
        return systemName;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public Properties getProperties() {
        return properties;
    }

    public Class<? extends Category<Game>> getCategory() {
        return category;
    }

    // Overrides
    @Override
    public String toString(){
        return name;
    }
}
