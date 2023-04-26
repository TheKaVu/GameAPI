package dev.kavu.gameapi;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Area {

    // Fields
    private int dx;
    private int dy;
    private int dz;
    private final Location originPos;

    private boolean blockPlacement;
    private boolean blockDestruction;
    private boolean blockInteraction;

    // Constructors
    public Area(int dx, int dy, int dz, Location originPos) {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
        this.originPos = originPos;
    }

    public Area(Location corner1, Location corner2) {
        dx = Math.abs(corner1.getBlockX() - corner2.getBlockX());
        dy = Math.abs(corner1.getBlockY() - corner2.getBlockY());
        dz = Math.abs(corner1.getBlockZ() - corner2.getBlockZ());

        originPos = new Location(corner1.getWorld(),
                Math.min(corner1.getBlockX(), corner2.getBlockX()),
                Math.min(corner1.getBlockY(), corner2.getBlockY()),
                Math.min(corner1.getBlockZ(), corner2.getBlockZ())
        );
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

    // Functionality
    public Area resize(int xd, int yd, int zd){
        this.dx = xd;
        this.dy = yd;
        this.dz = zd;
        return this;
    }

    public boolean hasLocation(Location location){
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        if(x < originPos.getBlockX() || x > originPos.getBlockX() + dx) return false;
        if(y < originPos.getBlockY() || y > originPos.getBlockY() + dy) return false;
        return z >= originPos.getBlockX() && z <= originPos.getBlockX() + dz;
    }

    public boolean hasPlayer(Player player){
        return hasLocation(player.getLocation()) || hasLocation(player.getEyeLocation());
    }

    public void onEnter(Player player){

    }

    public void onLeave(Player player){

    }
}
