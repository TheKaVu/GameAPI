package dev.kavu.gameapi.world;

import dev.kavu.gameapi.ConditionalListener;
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

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class AreaController {

    private final HashMap<Area, Integer> areas;

    private final HashMap<Player, Area> players = new HashMap<>();

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

        ConditionalListener conditionalListener = new ConditionalListener(new AreaControllerListener(), this::isRunning);
        conditionalListener.register(plugin);
    }

    public boolean addArea(Area area, int priority){
        if(area == null){
            throw new NullPointerException();
        }
        return areas.putIfAbsent(area, priority) == null;
    }

    public Area getPlayerArea(Player player){
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

    public Area getLocationArea(Location location){
        if(location == null){
            throw new NullPointerException("areas was null");
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

    public boolean isRunning(){
        return running;
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
            Area newArea = getPlayerArea(event.getPlayer());
            Area lastArea = players.get(player);
            if(lastArea != newArea){
                if(lastArea != null) lastArea.onLeave(event.getPlayer());
                if(newArea != null) newArea.onEnter(event.getPlayer());
                players.put(player, newArea);
            }
        }

        @EventHandler
        public void onBlockPlace(BlockPlaceEvent event) {
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

        @EventHandler
        public void onBlockBreak(BlockBreakEvent event) {
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

        @EventHandler
        public void onBlockInteraction(PlayerInteractEvent event) {
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
    }
}
