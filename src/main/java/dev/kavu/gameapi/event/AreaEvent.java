package dev.kavu.gameapi.event;

import dev.kavu.gameapi.world.Area;
import org.bukkit.event.Event;

public abstract class AreaEvent extends Event {

    private final Area area;

    public AreaEvent(Area area) {
        this.area = area;
    }

    public Area getArea() {
        return area;
    }
}
