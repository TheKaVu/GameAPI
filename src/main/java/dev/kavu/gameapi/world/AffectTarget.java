package dev.kavu.gameapi.world;

public enum AffectTarget {
    BLOCKS_ONLY(false, true),
    PLAYERS_ONLY(true, false),
    BLOCKS_AND_PLAYERS(true, true),
    NONE(false, false);

    private final boolean affectPlayers;
    private final boolean affectBlocks;

    AffectTarget(boolean affectPlayers, boolean affectBlocks) {
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
