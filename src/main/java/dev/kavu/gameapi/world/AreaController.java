package dev.kavu.gameapi.world;

import org.bukkit.Location;
import org.bukkit.Material;
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
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class AreaController {

    private final HashMap<Area, Integer> areas;

    private final HashMap<Player, Area> players = new HashMap<>();

    private boolean running = true;

    protected Listener moveListener = new Listener() {
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

    protected Listener blockPlaceListener = new Listener() {
        @EventHandler
        public void onBlockPlace(BlockPlaceEvent event){
            Player player = event.getPlayer();
            Area playerArea = players.get(player);
            Area blockArea = getLocationArea(event.getBlockPlaced().getLocation());

            Predicate<Player> playerFilter = playerArea != null ? playerArea::filterPlayer : (b) -> true;
            Predicate<Material> blockFilter = blockArea != null ? blockArea::filterBlock : (b) -> true;

            if(playerArea != null) {// Null check
                if (playerArea.getTarget().affectsPlayers() && playerArea.allowBlockPlacement() != playerFilter.test(player))
                    event.setCancelled(true);
            }
            if(blockArea != null) {// Null check
                if (blockArea.getTarget().affectsBlocks() && blockArea.allowBlockPlacement() != blockFilter.test(event.getBlockPlaced().getType()))
                    event.setCancelled(true);
            }
        }
    };

    protected Listener blockBreakListener = new Listener() {
        @EventHandler
        public void onBlockBreak(BlockBreakEvent event){
            Player player = event.getPlayer();
            Area playerArea = players.get(player);
            Area blockArea = getLocationArea(event.getBlock().getLocation());

            Predicate<Player> playerFilter = playerArea != null ? playerArea::filterPlayer : (b) -> true;
            Predicate<Material> blockFilter = blockArea != null ? blockArea::filterBlock : (b) -> true;

            if(playerArea != null) {// Null check
                if (playerArea.getTarget().affectsPlayers() && playerArea.allowBlockDestruction() != playerFilter.test(player))
                    event.setCancelled(true);
            }
            if(blockArea != null) {// Null check
                if (blockArea.getTarget().affectsBlocks() && blockArea.allowBlockDestruction() != blockFilter.test(event.getBlock().getType()))
                    event.setCancelled(true);
            }
        }
    };

    protected Listener blockInteractListener = new Listener() {
        @EventHandler
        public void onBlockInteraction(PlayerInteractEvent event){
            Player player = event.getPlayer();
            Area playerArea = players.get(player);

            Area blockArea = (event.getClickedBlock() != null) ? getLocationArea(event.getClickedBlock().getLocation()) : null;

            Action action = event.getAction();
            if (action != Action.RIGHT_CLICK_BLOCK) return;

            Predicate<Player> playerFilter = playerArea != null ? playerArea::filterPlayer : (b) -> true;
            Predicate<Material> blockFilter = blockArea != null ? blockArea::filterBlock : (b) -> true;

            if(playerArea != null) {// Null check
                if (playerArea.getTarget().affectsPlayers() && playerArea.allowBlockInteraction() != playerFilter.test(player))
                    event.setCancelled(true);
            }
            if(blockArea != null) {// Null check
                if (blockArea.getTarget().affectsBlocks() && blockArea.allowBlockInteraction() != blockFilter.test(event.getClickedBlock().getType()))
                    event.setCancelled(true);
            }
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

    public boolean addArea(Area area, int priority){
        return areas.putIfAbsent(area, priority) == null;
    }

    public Area getPlayerArea(Player player){
        int lastPriority = Integer.MIN_VALUE;
        AtomicReference<Area> currentArea = new AtomicReference<>(null);

        areas.forEach((area, priority) -> {
            if(area.hasPlayer(player) && priority == lastPriority) throw new AreaPickEquivocationException(currentArea.get(), area);
            if(area.hasPlayer(player) && priority > lastPriority) {
                currentArea.set(area);
            }
        });

        return currentArea.get();
    }

    public Area getLocationArea(Location location){
        int lastPriority = Integer.MIN_VALUE;
        AtomicReference<Area> currentArea = new AtomicReference<>(null);

        areas.forEach((area, priority) -> {
            if(area.hasLocation(location) && priority == lastPriority) throw new AreaPickEquivocationException(currentArea.get(), area);
            if(area.hasLocation(location) && priority > lastPriority) {
                currentArea.set(area);
            }
        });

        return currentArea.get();
    }

    public boolean isRunning(){
        return running;
    }

    public void start(){
        running = true;
    }

    public void stop(){
        running = false;
    }
}
