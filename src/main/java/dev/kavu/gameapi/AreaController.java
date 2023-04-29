package dev.kavu.gameapi;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class AreaController {

    private final HashMap<Area, Integer> areas;

    private final HashMap<Player, Area> players = new HashMap<>();

    protected final Listener moveListener = new Listener() {
        @EventHandler
        public void onPlayerMove(PlayerMoveEvent event){
            Player player = event.getPlayer();
            Area newArea = getPlayerArea(event.getPlayer());
            Area lastArea = players.get(player);
            if(lastArea != newArea){
                if(lastArea != null) lastArea.onLeave(event.getPlayer());
                if(newArea != null) newArea.onEnter(event.getPlayer());
                players.put(player, newArea);
            }
        }
    };

    protected final Listener blockPlaceListener = new Listener() {
        @EventHandler
        public void onBlockPlace(BlockPlaceEvent event){
            Player player = event.getPlayer();
            Area playerArea = players.get(player);
            Area blockArea = getLocationArea(event.getBlockPlaced().getLocation());

            if(playerArea != null) // Null check
                event.setCancelled(!playerArea.allowBlockPlacement() && playerArea.getTarget().affectsPlayers());
            if(blockArea != null) // Null check
                event.setCancelled(!blockArea.allowBlockPlacement() && blockArea.getTarget().affectsBlocks());
        }
    };

    protected final Listener blockBreakListener = new Listener() {
        @EventHandler
        public void onBlockBreak(BlockBreakEvent event){
            Player player = event.getPlayer();
            Area playerArea = players.get(player);
            Area blockArea = getLocationArea(event.getBlock().getLocation());

            if(playerArea != null) // Null check
                event.setCancelled(!playerArea.allowBlockDestruction() && playerArea.getTarget().affectsPlayers());
            if(blockArea != null) // Null check
                event.setCancelled(!blockArea.allowBlockDestruction() && blockArea.getTarget().affectsBlocks());
        }
    };

    protected final Listener blockInteractListener = new Listener() {
        @EventHandler
        public void onBlockInteraction(PlayerInteractEvent event){
            Player player = event.getPlayer();
            Area playerArea = players.get(player);

            if(!event.hasBlock()) return;
            if(event.isBlockInHand()) return;
            if(event.getAction() == Action.LEFT_CLICK_BLOCK) return;

            Area blockArea = (event.getClickedBlock() != null) ? getLocationArea(event.getClickedBlock().getLocation()) : null;

            if(playerArea != null) // Null check
                event.setCancelled(!playerArea.allowBlockInteraction() && playerArea.getTarget().affectsPlayers());
            if(blockArea != null) // Null check
                event.setCancelled(!blockArea.allowBlockInteraction() && blockArea.getTarget().affectsBlocks());
        }
    };

    public AreaController(Plugin plugin){
        this(new HashMap<>(), plugin);
    }

    public AreaController(HashMap<Area, Integer> areas, Plugin plugin){
        this.areas = areas;
        PluginManager manager = plugin.getServer().getPluginManager();
        manager.registerEvents(moveListener, plugin);
        manager.registerEvents(blockPlaceListener, plugin);
        manager.registerEvents(blockBreakListener, plugin);
        manager.registerEvents(blockInteractListener, plugin);
    }

    public HashMap<Area, Integer> getAreas() {
        return areas;
    }

    public Area getPlayerArea(Player player){
        int lastPriority = Integer.MIN_VALUE;
        AtomicReference<Area> currentArea = new AtomicReference<>(null);

        areas.forEach((area, priority) -> {
            if(area.hasPlayer(player) && priority > lastPriority) {
                currentArea.set(area);
            }
        });

        return currentArea.get();
    }

    public Area getLocationArea(Location location){
        for(Area area : areas.keySet()){
            if(area.hasLocation(location)) return area;
        }

        return null;
    }
}
