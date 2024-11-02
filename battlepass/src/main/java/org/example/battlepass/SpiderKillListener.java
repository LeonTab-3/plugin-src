package org.example.battlepass;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class SpiderKillListener implements Listener {
    private final SpiderAbilityPlugin plugin;
    private final HashMap<UUID, Integer> spiderKills = new HashMap<>();

    public SpiderKillListener(SpiderAbilityPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getType() == EntityType.SPIDER) {
            Player player = event.getEntity().getKiller();
            if (player != null) {
                UUID playerId = player.getUniqueId();
                int kills = spiderKills.getOrDefault(playerId, 0) + 1;
                spiderKills.put(playerId, kills);
                player.sendMessage("You have killed " + kills + " spiders.");

                if (kills >= 2000 && !player.hasPermission("spiderability.spiderhack")) {
                    player.sendMessage("Congratulations! You have unlocked the Spiderhack ability.");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set spiderability.spiderhack true");
                }
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("spiderability.spiderhack")) {
            if (player.getInventory().contains(Material.STRING)) {
                // Beispielhafte Implementation eines Spiderhacks
                player.setAllowFlight(true);
                player.setFlying(true);
            } else {
                player.setAllowFlight(false);
                player.setFlying(false);
            }
        }
    }
}
