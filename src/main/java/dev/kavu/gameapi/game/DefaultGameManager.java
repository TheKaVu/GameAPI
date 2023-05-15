package dev.kavu.gameapi.game;

import dev.kavu.gameapi.world.MapManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collection;

public class DefaultGameManager extends GameManager<GameType>{

    public DefaultGameManager(Plugin plugin, GameType gameType) {
        super(plugin, gameType);
    }

    public DefaultGameManager(Plugin plugin, GameType gameType, MapManager mapManager) {
        super(plugin, gameType, mapManager);
    }

    public DefaultGameManager(Plugin plugin, GameType gameType, Collection<? extends Player> playersInGame) {
        super(plugin, gameType, playersInGame);
    }

    public DefaultGameManager(Plugin plugin, GameType gameType, MapManager mapManager, Collection<? extends Player> playersInGame) {
        super(plugin, gameType, mapManager, playersInGame);
    }
}
