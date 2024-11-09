package org.example.dungeon.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.example.dungeon.DungeonPlugin;

public class RespawnListener implements Listener {
    private final DungeonPlugin plugin;

    public RespawnListener(DungeonPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        // Logic for respawn titles and phases
    }
}
