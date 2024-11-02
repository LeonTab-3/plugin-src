package org.example.gem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class GemListener implements Listener {
    private final GemPlugin plugin;  // Reference to the main plugin class
    private final HashMap<UUID, Gem> playerGems = new HashMap<>();  // Store player gems

    // Constructor to accept plugin instance
    public GemListener(GemPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Check if the player already has a gem (load it)
        if (!playerGems.containsKey(player.getUniqueId())) {
            player.sendMessage("Welcome! Please choose a gem by typing /gem.");
        } else {
            Gem gem = playerGems.get(player.getUniqueId());
            player.sendMessage("Welcome back! You have the " + gem.getName() + ".");
        }
    }

    // Assign a random gem and store it in the player's data
    public Gem assignRandomGem(Player player) {
        String[] gemTypes = {"Blade Gem", "Archer Gem", "Wind Gem", "Freeze Gem", "Lava Gem"};
        Random random = new Random();
        String gemName = gemTypes[random.nextInt(gemTypes.length)];

        // Create a new Gem with the plugin reference
        Gem gem = new Gem(gemName, 1, plugin);
        playerGems.put(player.getUniqueId(), gem);  // Store the player's gem
        return gem;
    }

    // Method to check if a player has chosen a gem
    public boolean hasChosenGem(Player player) {
        return playerGems.containsKey(player.getUniqueId());
    }

    // Method to get a player's gem
    public Gem getGem(Player player) {
        return playerGems.get(player.getUniqueId());
    }
}
