package dev.kavu.gameapi;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class AreaController {

    private final HashMap<Area, Integer> areas;

    private final HashMap<Player, Area> players = new HashMap<>();

    private final Listener listener = new Listener() {
        @EventHandler
        public void onPlayerMove(PlayerMoveEvent event){
            Player player = event.getPlayer();
            Area newArea = currentPlayerArea(event.getPlayer());
            Area lastArea = players.get(player);
            if(lastArea != newArea){
                lastArea.onLeave(event.getPlayer());
                newArea.onEnter(event.getPlayer());
                players.put(player, newArea);
            }
        }
    };

    public AreaController(Plugin plugin){
        areas = new HashMap<>();
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    public AreaController(HashMap<Area, Integer> areas, Plugin plugin){
        this.areas = areas;
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    public Area currentPlayerArea(Player player){
        int lastPriority = Integer.MIN_VALUE;
        AtomicReference<Area> currentArea = new AtomicReference<>(null);

        areas.forEach((area, priority) -> {
            if(area.hasPlayer(player) && priority > lastPriority) {
                currentArea.set(area);
            }
        });

        return currentArea.get();
    }
}
