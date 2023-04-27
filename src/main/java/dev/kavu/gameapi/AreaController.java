package dev.kavu.gameapi;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class AreaController {

    private final HashMap<Area, Integer> areas;

    private final HashMap<Player, Area> players = new HashMap<>();

    protected final Listener moveListener = new Listener() {
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

    protected final Listener blockPlaceListener = new Listener() {
        @EventHandler
        public void onBlockPlace(BlockPlaceEvent event){
            Player player = event.getPlayer();
            if(!players.get(player).allowBlockPlacement()){
                event.setCancelled(true);
            }
        }
    };

    protected final Listener blockBreakListener = new Listener() {
        @EventHandler
        public void onBlockBreak(BlockBreakEvent event){
            Player player = event.getPlayer();
            if(!players.get(player).allowBlockDestruction()){
                event.setCancelled(true);
            }
        }
    };

    protected final Listener blockInteractListener = new Listener() {
        @EventHandler
        public void onBlockInteraction(PlayerInteractEvent event){
            Player player = event.getPlayer();
            if(!players.get(player).allowBlockInteraction()){
                event.setCancelled(true);
            }
        }
    };

    public AreaController(Plugin plugin){
        this(new HashMap<>(), plugin);
    }

    public AreaController(HashMap<Area, Integer> areas, Plugin plugin){
        this.areas = areas;
        plugin.getServer().getPluginManager().registerEvents(moveListener, plugin);
        plugin.getServer().getPluginManager().registerEvents(blockPlaceListener, plugin);
        plugin.getServer().getPluginManager().registerEvents(blockBreakListener, plugin);
        plugin.getServer().getPluginManager().registerEvents(blockInteractListener, plugin);
    }

    public HashMap<Area, Integer> getAreas() {
        return areas;
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
