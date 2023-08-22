package dev.kavu.gameapi;

/**
 * Abstract class being the base for any game. Contains major information about the game. Games can also be categorized using {@link Category} interface.
 */
public abstract class Game {

    protected int minPlayers;
    protected int maxPlayers;
    protected String name;
    protected String systemName;

    public Game(int minPlayers, int maxPlayers, String name, String systemName) {
        if(minPlayers > maxPlayers) throw new IllegalArgumentException("'minPlayers' cannot be greater than 'maxPlayers'");
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.name = name;
        this.systemName = systemName;
    }

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
     * Returns the category of this game represented by {@link Category} interface. The category can be separate object or the Game subclass itself.
     * @return Category of this game
     */
    public abstract Class<? extends Category<?>> getCategory();

    /**
     * @return String representation of this object
     */
    @Override
    public String toString(){
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return getClass().equals(obj.getClass());
    }
}
