package dev.kavu.gameapi.world;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.function.Predicate;

public class Area {

    // Fields
    private int dx;
    private int dy;
    private int dz;
    private final Location originPos;

    private boolean blockPlacement = true;
    private boolean blockDestruction = true;
    private boolean blockInteraction = true;
    private Target target;

    private Predicate<Material> blockFilter = (material) -> true;
    private Predicate<Player> playerFilter = (player) -> true;

    // Constructors
    public Area(Location originPos, int x, int y, int z, Target affectTarget, boolean center) {

        if(center) {
            this.dx = 2 * x;
            this.dy = 2 * y;
            this.dz = 2 * z;
            this.originPos = originPos.add(new Vector(-x, -y, -z));
        } else {
            this.dx = x;
            this.dy = y;
            this.dz = z;
            this.originPos = originPos;
        }
        target = affectTarget;
    }

    public Area(Location corner1, Location corner2, Target affectTarget) {
        dx = Math.abs(corner1.getBlockX() - corner2.getBlockX());
        dy = Math.abs(corner1.getBlockY() - corner2.getBlockY());
        dz = Math.abs(corner1.getBlockZ() - corner2.getBlockZ());

        originPos = new Location(corner1.getWorld(),
                Math.min(corner1.getBlockX(), corner2.getBlockX()),
                Math.min(corner1.getBlockY(), corner2.getBlockY()),
                Math.min(corner1.getBlockZ(), corner2.getBlockZ())
        );

        target = affectTarget;
    }

    // Getters & Setters
    public int getSizeX() {
        return dx;
    }

    public int getSizeY() {
        return dy;
    }

    public int getSizeZ() {
        return dz;
    }

    public Location getOriginPos() {
        return originPos;
    }

    public boolean allowBlockPlacement() {
        return blockPlacement;
    }

    public void setBlockPlacement(boolean blockPlacement) {
        this.blockPlacement = blockPlacement;
    }

    public boolean allowBlockDestruction() {
        return blockDestruction;
    }

    public void setBlockDestruction(boolean blockDestruction) {
        this.blockDestruction = blockDestruction;
    }

    public boolean allowBlockInteraction() {
        return blockInteraction;
    }

    public void setBlockInteraction(boolean blockInteraction) {
        this.blockInteraction = blockInteraction;
    }

    public Target getTarget(){
        return target;
    }

    public void setTarget(Target target){
        this.target = target;
    }

    public boolean filterBlock(Material material) {
        return blockFilter.test(material);
    }

    public void setBlockFilter(Predicate<Material> blockFilter) {
        this.blockFilter = blockFilter;
    }

    public boolean filterPlayer(Player player) {
        return playerFilter.test(player);
    }

    public void setPlayerFilter(Predicate<Player> playerFilter) {
        this.playerFilter = playerFilter;
    }

    // Functionality
    public Area resize(int x, int y, int z){
        this.dx = x;
        this.dy = y;
        this.dz = z;
        return this;
    }

    public boolean hasLocation(Location location){
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        if(x < originPos.getBlockX() || x > originPos.getBlockX() + dx) return false;
        if(y < originPos.getBlockY() || y > originPos.getBlockY() + dy) return false;
        if(z < originPos.getBlockZ() || z > originPos.getBlockZ() + dz) return false;

        return true;
    }

    public boolean hasPlayer(Player player){
        return hasLocation(player.getLocation()) || hasLocation(player.getEyeLocation());
    }

    public void onEnter(Player player){

    }

    public void onLeave(Player player){

    }

    public enum Target {
        BLOCKS_ONLY(false, true),
        PLAYERS_ONLY(true, false),
        BLOCKS_AND_PLAYERS(true, true);

        private final boolean affectPlayers;
        private final boolean affectBlocks;

        Target(boolean affectPlayers, boolean affectBlocks) {
            this.affectPlayers = affectPlayers;
            this.affectBlocks = affectBlocks;
        }

        public boolean affectsPlayers() {
            return affectPlayers;
        }

        public boolean affectsBlocks() {
            return affectBlocks;
        }
    }

}
