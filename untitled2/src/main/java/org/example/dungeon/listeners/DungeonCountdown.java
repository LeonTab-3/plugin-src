// File: src/main/java/org/example/dungeon/DungeonCountdown.java
package org.example.dungeon.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.example.dungeon.DungeonPlugin;

public class DungeonCountdown {
    private final DungeonPlugin plugin;
    private int countdownTaskId;
    private int timeLeft;  // Configurable from the plugin's config file

    public DungeonCountdown(DungeonPlugin plugin) {
        this.plugin = plugin;
        this.timeLeft = plugin.getConfig().getInt("dungeon.lobby_time", 300); // default 5 mins
    }

    public void startCountdown() {
        countdownTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (timeLeft <= 0) {
                broadcastMessage("&8[&cDungeon&8] &aDungeon have started!");
                playSoundToAll(Sound.BLOCK_NOTE_BLOCK_BASS, 0.5f, 1);
                Bukkit.getScheduler().cancelTask(countdownTaskId);
                return;
            }

            if (timeLeft % 60 == 0 || timeLeft <= 5) { // Custom messages at milestones
                broadcastMessage("&8[&cDungeon&8] &aDungeon is starting in &7&n" + timeLeft / 60 + "m&a!");
                playSoundToAll(Sound.BLOCK_NOTE_BLOCK_HAT, 0.5f, 4);
            }
            timeLeft--;
        }, 0L, 20L); // runs every second
    }

    public void forceStart(Player player) {
        Bukkit.getScheduler().cancelTask(countdownTaskId);
        broadcastMessage("&8[&cDungeon&8] &aDungeon has been forcibly started by " + player.getName() + "!");
        playSoundToAll(Sound.BLOCK_NOTE_BLOCK_BASS, 0.5f, 1);
    }

    private void broadcastMessage(String message) {
        Bukkit.broadcastMessage(message.replace("&", "§")); // Use § for color codes
    }

    private void playSoundToAll(Sound sound, float volume, float pitch) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), sound, volume, pitch);
        }
    }
}
