package dev.kavu.gameapi.event;

import dev.kavu.gameapi.world.Area;
import org.bukkit.event.Event;

/**
 * Represents an area event.
 */
public abstract class AreaEvent extends Event {

    private final Area area;

    public AreaEvent(Area area) {
        this.area = area;
    }

    /**
     * @return The area this event applies to
     */
    public Area getArea() {
        return area;
    }
}
