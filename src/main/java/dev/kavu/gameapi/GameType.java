package dev.kavu.gameapi;

import java.util.Properties;

public class GameType {

    // Fields
    private final String name;

    private final String systemName;

    private final String prefix;

    private final int minPlayers;

    private final int maxPlayers;

    private final Properties properties;

    // Constructors
    public GameType(String name, String systemName, String prefix, int minPlayers, int maxPlayers) {
        this(name, systemName, prefix, minPlayers, maxPlayers, new Properties());
    }


    // Constructor
    public GameType(String name, String systemName, String prefix, int minPlayers, int maxPlayers, Properties properties) {
        if(name == null){
            throw new NullPointerException("name was null");
        }
        if(systemName == null){
            throw new NullPointerException("systemName was null");
        }
        if(prefix == null){
            throw new NullPointerException("prefix was null");
        }
        if(minPlayers > maxPlayers){
            throw new ArithmeticException("Minimum value was bigger than maximum value");
        }
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

    // Overrides
    @Override
    public String toString(){
        return name;
    }
}
