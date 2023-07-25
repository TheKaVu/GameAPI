package dev.kavu.gameapi.event;

import dev.kavu.gameapi.world.Area;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Represents an area enter event.
 */
public class AreaEnterEvent extends AreaEvent {

    private static final HandlerList handlerList = new HandlerList();

    private final Area previousArea;
    private final Player player;

    public AreaEnterEvent(Area area, Area previousArea, Player player) {
        super(area);
        this.player = player;
        this.previousArea = previousArea;
    }

    /**
     * @return The area player left by entering following area; {@code null} if wasn't in any
     */
    public Area getPreviousArea() {
        return previousArea;
    }

    /**
     * @return Player that entered following area
     */
    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
