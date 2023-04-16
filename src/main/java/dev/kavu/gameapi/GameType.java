package dev.kavu.gameapi;

public class GameType {

    // Fields
    private final String name;

    private final String systemName;

    private final String prefix;

    private final int minPlayers;

    private final int maxPlayers;

    // Constructor
    public GameType(String name, String systemName, String prefix, int minPlayers, int maxPlayers) {
        this.name = name;
        this.systemName = systemName;
        this.prefix = prefix;
        this.minPlayers = Math.min(minPlayers, maxPlayers);
        this.maxPlayers = maxPlayers;
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

    // Overrides
    @Override
    public String toString(){
        return name;
    }
}