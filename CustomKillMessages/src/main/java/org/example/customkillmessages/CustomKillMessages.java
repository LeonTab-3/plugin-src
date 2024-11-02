package org.example.customkillmessages;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.MetadataValue;

public class CustomKillMessages extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Register event listeners
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("CustomKillMessages plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("CustomKillMessages plugin has been disabled.");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player deadPlayer = event.getEntity();
        Entity killer = deadPlayer.getKiller();

        String deathMessage = null;

        if (killer instanceof Player) {
            Player killerPlayer = (Player) killer;

            if (isVanished(killerPlayer)) {
                deathMessage = ChatColor.RED + deadPlayer.getName() + " was killed by an unknown entity!";
            } else {
                ItemStack weapon = killerPlayer.getInventory().getItemInMainHand();

                if (weapon == null || weapon.getType() == Material.AIR) {
                    deathMessage = ChatColor.RED + deadPlayer.getName() + " was killed by " + killerPlayer.getName() + " using their bare hands!";
                } else {
                    deathMessage = ChatColor.RED + deadPlayer.getName() + " was killed by " + killerPlayer.getName() + " using " + weapon.getType().toString().toLowerCase().replace("_", " ") + "!";
                }
            }
        } else {

            deathMessage = ChatColor.RED + deadPlayer.getName() + " died to an unknown reason.";
        }

        event.setDeathMessage(deathMessage);
    }

    private boolean isVanished(Player player) {

        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) {
                return true;
            }
        }
        return false;
    }
}
