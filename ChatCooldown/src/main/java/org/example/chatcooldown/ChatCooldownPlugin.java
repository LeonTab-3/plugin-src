package org.example.chatcooldown;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class ChatCooldownPlugin extends JavaPlugin implements Listener {

    // Store the time when players last chatted
    private HashMap<UUID, Long> lastChatTime;

    // Cooldown time in milliseconds (5000ms = 5 seconds)
    private static final long COOLDOWN_TIME = 5000;

    @Override
    public void onEnable() {
        // Initialize the hashmap
        lastChatTime = new HashMap<>();

        // Register the event listener
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("ChatCooldown plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("ChatCooldown plugin has been disabled.");
    }

    // Listen to the chat event
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        long currentTime = System.currentTimeMillis();

        // Check if the player has chatted before
        if (lastChatTime.containsKey(playerId)) {
            long lastTime = lastChatTime.get(playerId);

            // If the current time minus the last chat time is less than the cooldown, cancel the event
            if (currentTime - lastTime < COOLDOWN_TIME) {
                long timeLeft = (COOLDOWN_TIME - (currentTime - lastTime)) / 1000;
                player.sendMessage(ChatColor.RED + "You must wait " + timeLeft + " more seconds before chatting again.");
                event.setCancelled(true);
                return;
            }
        }

        // Update the player's last chat time
        lastChatTime.put(playerId, currentTime);
    }
}
