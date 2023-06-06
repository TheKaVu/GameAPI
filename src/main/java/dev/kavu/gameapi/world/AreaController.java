package dev.kavu.gameapi.world;

import dev.kavu.gameapi.ConditionalListener;
import dev.kavu.gameapi.event.AreaEnterEvent;
import dev.kavu.gameapi.event.AreaLeaveEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class AreaController {

    private final HashMap<Area, Integer> areas;

    private final HashMap<Player, Area> players = new HashMap<>();

    private final Plugin plugin;

    private boolean running = true;

    public AreaController(Plugin plugin){
        this(new HashMap<>(), plugin);
    }

    public AreaController(HashMap<Area, Integer> areas, Plugin plugin){
        if(areas == null){
            throw new NullPointerException("areas was null");
        }
        if(plugin == null){
            throw new NullPointerException("plugin was null");
        }
        this.areas = areas;
        this.plugin = plugin;

        ConditionalListener conditionalListener = new ConditionalListener(new AreaControllerListener(), this::isRunning);
        conditionalListener.register(plugin);
    }

    public boolean addArea(Area area, int priority){
        if(area == null){
            throw new NullPointerException();
        }
        return areas.putIfAbsent(area, priority) == null;
    }

    public Area getArea(Player player){
        int lastPriority = Integer.MIN_VALUE;
        AtomicReference<Area> currentArea = new AtomicReference<>(null);

        areas.forEach((area, priority) -> {
            if(area.hasPlayer(player) && priority == lastPriority) throw new AreaPickEquivocationException();
            if(area.hasPlayer(player) && priority > lastPriority) {
                currentArea.set(area);
            }
        });

        return currentArea.get();
    }

    public Area getArea(Location location){
        if(location == null){
            throw new NullPointerException();
        }
        int lastPriority = Integer.MIN_VALUE;
        AtomicReference<Area> currentArea = new AtomicReference<>(null);

        areas.forEach((area, priority) -> {
            if(area.hasLocation(location) && priority == lastPriority) throw new AreaPickEquivocationException();
            if(area.hasLocation(location) && priority > lastPriority) {
                currentArea.set(area);
            }
        });

        return currentArea.get();
    }

    public HashSet<Area> getAreas(Location location) {
        if(location == null){
            throw new NullPointerException();
        }
        HashSet<Area> areaSet = new HashSet<>();
        areas.forEach((area, priority) -> {
            if(area.hasLocation(location)) {
                areaSet.add(area);
            }
        });
        return areaSet;
    }

    public HashSet<Area> getAreas(Player player) {
        if(player == null){
            throw new NullPointerException();
        }
        HashSet<Area> areaSet = new HashSet<>();
        areas.forEach((area, priority) -> {
            if(area.hasPlayer(player)) {
                areaSet.add(area);
            }
        });
        return areaSet;
    }

    public boolean isRunning(){
        return running;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public void start(){
        running = true;
    }

    public void stop(){
        running = false;
    }

    private class AreaControllerListener implements Listener {
        @EventHandler
        public void onPlayerMove(PlayerMoveEvent event) {
            Player player = event.getPlayer();
            Area newArea = getArea(event.getPlayer());
            Area lastArea = players.get(player);
            if(lastArea != newArea){
                if(lastArea != null) {
                    lastArea.onLeave(player);
                    plugin.getServer().getPluginManager().callEvent(new AreaLeaveEvent(lastArea, player));
                }
                if(newArea != null) {
                    newArea.onEnter(player);
                    plugin.getServer().getPluginManager().callEvent(new AreaEnterEvent(newArea, lastArea, player));
                }
                players.put(player, newArea);
            } else {
                if (newArea != null) {
                    newArea.onMove(player);
                }
            }
        }
    }
}
