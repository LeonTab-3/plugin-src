package com.example.killmessageplugin;

import com.destroystokyo.paper.inventory.meta.ArmorStandMeta;
import de.myzelyam.api.vanish.VanishAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class KillMessagePlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Register the event listener
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("KillMessagePlugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("KillMessagePlugin disabled!");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        // Check if the victim was killed by a player
        if (killer != null) {
            ArmorStandMeta VanishAPI;
            if (VanishAPI.isInvisible(killer)) {
                // If the killer is in vanish, display unknown reason message
                event.setDeathMessage(ChatColor.RED + victim.getName() + " died to an unknown reason");
            } else {
                // Get the weapon used by the killer
                ItemStack weapon = killer.getInventory().getItemInMainHand();
                String weaponName;

                // Check if the weapon is an empty hand or an item
                if (weapon == null || weapon.getType() == Material.AIR) {
                    weaponName = "hand";
                } else {
                    weaponName = weapon.getType().name().toLowerCase().replace("_", " ");
                }

                // Set the custom death message
                event.setDeathMessage(ChatColor.YELLOW + victim.getName() + " has been killed by " +
                        ChatColor.RED + killer.getName() + ChatColor.YELLOW + " using " + ChatColor.AQUA + weaponName);
            }
        }
    }
}
