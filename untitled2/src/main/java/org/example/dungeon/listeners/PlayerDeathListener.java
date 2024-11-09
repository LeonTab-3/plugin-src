package org.example.dungeon.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.example.dungeon.utils.ConfigUtils;

import java.util.Optional;

public class PlayerDeathListener implements Listener {

    private final FileConfiguration config;

    public PlayerDeathListener(FileConfiguration config) {
        this.config = config;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String worldName = player.getWorld().getName();

        // Check if money drop is enabled globally and in this world
        if (!config.getBoolean("dungeon.moneydrop.enabled") || config.getStringList("dungeon.moneydrop.disabled_worlds").contains(worldName)) {
            return;
        }

        // Find the lowest permission percentage using Optional<Integer>
        Optional<Integer> dropPercentage = Optional.empty();
        for (int percentage = 100; percentage >= 0; percentage--) {
            if (player.hasPermission("moneydrop." + percentage)) {
                dropPercentage = Optional.of(percentage);
                break;
            }
        }

        // If no valid drop percentage is found or it's 0, don't drop money
        if (!dropPercentage.isPresent() || dropPercentage.get() == 0) {
            String noDropMessage = config.getString("dungeon.moneydrop.no_drop_message", "&cYou don't have permission to drop money.");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', noDropMessage));
            return;
        }

        int percentage = dropPercentage.get();
        double balance = getBalance(player); // Replace with economy API call
        double amountToDrop = balance * (percentage / 100.0);

        // Deduct the money and drop the calculated amount (use your economy API)
        setBalance(player, balance - amountToDrop);
        dropMoney(player, amountToDrop);

        // Send drop message with placeholder
        String dropMessage = config.getString("dungeon.moneydrop.drop_message", "&aYou dropped %moneydrop_percentage%% of your balance.");
        dropMessage = dropMessage.replace("%moneydrop_percentage%", String.valueOf(percentage));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', dropMessage));
    }

    private double getBalance(Player player) {
        // Implement this with your economy plugin to get player balance
        return 1000.0; // Placeholder for demo purposes
    }

    private void setBalance(Player player, double newBalance) {
        // Implement this with your economy plugin to set player balance
    }

    private void dropMoney(Player player, double amount) {
        // Implement this to handle money drop
        Bukkit.broadcastMessage(player.getName() + " has dropped " + amount + " currency!");
    }
}
