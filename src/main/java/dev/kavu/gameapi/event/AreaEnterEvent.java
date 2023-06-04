package dev.kavu.gameapi.event;

import dev.kavu.gameapi.world.Area;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class AreaEnterEvent extends AreaEvent {

    private static final HandlerList handlerList = new HandlerList();

    private final Area previousArea;
    private final Player player;

    public AreaEnterEvent(Area area, Player player, Area previousArea) {
        super(area);
        this.player = player;
        this.previousArea = previousArea;
    }

    public Area getPreviousArea() {
        return previousArea;
    }

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
