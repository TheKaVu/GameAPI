package dev.kavu.gameapi.world;

import dev.kavu.gameapi.ConditionalListener;
import dev.kavu.gameapi.event.AreaEnterEvent;
import dev.kavu.gameapi.event.AreaLeaveEvent;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Controller class designated to manage all areas present in the world. It is necessary for area to work.
 */
public class AreaController {

    private final HashMap<Area, Integer> areas;

    private final HashMap<Player, Area> players = new HashMap<>();

    private final Plugin plugin;

    private boolean running = true;

    /**
     * Creates new instance of <tt>AreaController</tt> just with {@link Plugin} object.
     * @param plugin Plugin this controller is bound to
     */
    public AreaController(Plugin plugin){
        this(new HashMap<>(), plugin);
    }

    /**
     * Creates new instance of <tt>AreaController</tt> with initial set of prioritized areas.
     * @param areas Map of {@link Area} objects associated with its priority represented by <tt>int</tt> value
     * @param plugin Plugin this controller is bound to
     */
    public AreaController(HashMap<Area, Integer> areas, Plugin plugin){
        Validate.notNull(areas, "areas cannot be null");
        Validate.notNull(plugin, "plugin cannot be null");

        this.areas = areas;
        this.plugin = plugin;

        ConditionalListener conditionalListener = new ConditionalListener(new AreaMoveListener(), this::isRunning);
        conditionalListener.register(plugin);
    }

    /**
     * Adds new {@link Area} object with its priority if it does not exist yet.
     * @param area Area to be added
     * @param priority Area's priority
     * @return {@code true} if area was successfully added; {@code false} otherwise
     */
    public boolean addArea(Area area, int priority){
        Validate.notNull(area, "area cannot be null");

        return areas.putIfAbsent(area, priority) == null;
    }

    /**
     * Gets the most prioritized area specified player is in. <p/>
     * <b>Warning: </b> If there are two or more overlapping areas with same priority, when having specified player, {@link AreaPickEquivocationException} will be thrown.
     * @param player Player to be checked
     * @return {@link Area} object with the highest priority containing specified player; {@code null} if there is no such area
     *
     * @see #getAreas
     */
    public Area getArea(Player player){
        Validate.notNull(player, "player cannot be null");

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

    /**
     * Gets the most prioritized area containing specified location. <p/>
     * <b>Warning: </b> If there are two or more overlapping areas with same priority, when having specified location, {@link AreaPickEquivocationException} will be thrown.
     * @param location Designated location
     * @return {@link Area} object with the highest priority containing specified location; {@code null} if there is no such area
     *
     * @see #getAreas
     */
    public Area getArea(Location location){
        Validate.notNull(location, "location cannot be null");

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

    /**
     * Gets all areas containing specified location.
     * @param location Designated location
     * @return HashSet of {@link Area} objects having all areas containing specified location; {@code null} if there is no such area
     *
     * @see #getArea
     */

    public HashSet<Area> getAreas(Location location) {
        Validate.notNull(location, "location cannot be null");

        HashSet<Area> areaSet = new HashSet<>();
        areas.forEach((area, priority) -> {
            if(area.hasLocation(location)) {
                areaSet.add(area);
            }
        });
        return areaSet;
    }

    /**
     * Gets all areas containing specified location.
     * @param player Player to be checked
     * @return HashSet of {@link Area} objects having all areas specified player is in; {@code null} if there is no such area
     *
     * @see #getArea
     */
    public HashSet<Area> getAreas(Player player) {
        Validate.notNull(player, "player cannot be null");

        HashSet<Area> areaSet = new HashSet<>();
        areas.forEach((area, priority) -> {
            if(area.hasPlayer(player)) {
                areaSet.add(area);
            }
        });
        return areaSet;
    }

    /**
     * @return {@code true} if controller is running; {@code false} if is not
     */
    public boolean isRunning(){
        return running;
    }

    /**
     * @return Associated plugin
     */
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * Starts or resumes the controller
     */
    public void start(){
        running = true;
    }

    /**
     * Stops the controller
     */
    public void stop(){
        running = false;
    }

    private final class AreaMoveListener implements Listener {
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
