package com.leontab.blacklist;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ChatFilterPlugin extends JavaPlugin implements Listener {
    private List<String> blacklistedWords;
    private String discordWebhook;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfigValues();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    private void loadConfigValues() {
        FileConfiguration config = getConfig();
        blacklistedWords = config.getStringList("blacklisted-words");
        discordWebhook = config.getString("discord-webhook", "");
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        for (String word : blacklistedWords) {
            if (message.toLowerCase().contains(word.toLowerCase())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "§lThe word you were trying to say is a Blacklisted word so it got censored");
                sendToDiscord(event.getPlayer().getName(), message);
                return;
            }
        }
    }

    private void sendToDiscord(String playerName, String message) {
        if (discordWebhook.isEmpty()) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(discordWebhook);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);

                    String jsonPayload = "{" +
                            "\"content\": \"Player **" + playerName + "** tried to say: " + message + "\"}";
                    try (OutputStream os = connection.getOutputStream()) {
                        byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                        os.write(input, 0, input.length);
                    }

                    connection.getResponseCode();
                    connection.disconnect();
                } catch (Exception e) {
                    getLogger().warning("Failed to send message to Discord webhook.");
                }
            }
        }.runTaskAsynchronously(this);
    }
}
