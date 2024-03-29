package dev.kavu.gameapi.event;

import dev.kavu.gameapi.world.Area;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Represents an area leave event.
 */
public class AreaLeaveEvent extends AreaEvent{

    private static final HandlerList handlerList = new HandlerList();

    private final Player player;

    public AreaLeaveEvent(Area area, Player player) {
        super(area);
        this.player = player;
    }

    /**
     * @return Player that left the area
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
