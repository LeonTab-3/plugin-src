package org.example.gemplugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GemListener implements Listener {

    private final GemPlugin plugin;
    private final Map<Player, Gem> playerGems = new HashMap<>();

    public GemListener(GemPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean hasChosenGem(Player player) {
        FileConfiguration config = plugin.getConfig();
        return config.contains("gems." + player.getUniqueId());
    }

    public void savePlayerGem(Player player, Gem gem) {
        FileConfiguration config = plugin.getConfig();
        config.set("gems." + player.getUniqueId() + ".name", gem.getName());
        config.set("gems." + player.getUniqueId() + ".level", gem.getLevel());
        plugin.saveConfig();
        playerGems.put(player, gem);
    }

    public Gem loadPlayerGem(Player player) {
        FileConfiguration config = plugin.getConfig();
        if (config.contains("gems." + player.getUniqueId() + ".name")) {
            String gemName = config.getString("gems." + player.getUniqueId() + ".name");
            int level = config.getInt("gems." + player.getUniqueId() + ".level", 1);
            return new Gem(gemName, level);
        }
        return null;
    }

    public Gem getGem(Player player) {
        if (playerGems.containsKey(player)) {
            return playerGems.get(player);
        } else {
            Gem gem = loadPlayerGem(player);
            if (gem != null) {
                playerGems.put(player, gem);
                return gem;
            }
        }
        return null; // Player has not chosen a gem yet
    }

    private Gem assignRandomGem() {
        String[] gemTypes = {"Blade Gem", "Archer Gem", "Wind Gem", "Freeze Gem", "Lava Gem"};
        Random random = new Random();
        String gemName = gemTypes[random.nextInt(gemTypes.length)];
        return new Gem(gemName);
    }

    // Handle player joining the server
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Check if the player already has a gem (load it)
        if (!hasChosenGem(player)) {
            player.sendMessage("Welcome! Please choose a gem by typing /gem.");
        } else {
            Gem gem = getGem(player);
            player.sendMessage("Welcome back! You have the " + gem.getName() + ".");
        }
    }

    // Handle player death (and potentially gem upgrade)
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player deadPlayer = event.getEntity();
        Player killer = deadPlayer.getKiller();

        if (killer != null && playerGems.containsKey(killer)) {
            // Drop a Gem Upgrader and upgrade the killer's gem
            killer.getInventory().addItem(new ItemStack(Material.DIAMOND, 1));
            Gem gem = playerGems.get(killer);
            gem.upgrade();
            killer.sendMessage("Your " + gem.getName() + " has been upgraded to Level " + gem.getLevel() + "!");
        }
    }

    // Handle player quitting the server (save their gem state)
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (playerGems.containsKey(player)) {
            savePlayerGem(player, playerGems.get(player));
            playerGems.remove(player); // Remove from memory to free up space
        }
    }
}
