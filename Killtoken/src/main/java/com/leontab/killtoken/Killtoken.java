package com.leontab.killtoken;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class Killtoken extends JavaPlugin implements Listener {

    private static final String TOKEN_NAME = "§4§lKill Token"; // Red name
    private static final NamespacedKey TOKEN_KEY = new NamespacedKey("killtoken", "kill_token");

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("KillToken Plugin Enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("KillToken Plugin Disabled!");
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        if (killer != null) {
            ItemStack token = new ItemStack(Material.RED_DYE);
            ItemMeta meta = token.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(TOKEN_NAME);
                meta.getPersistentDataContainer().set(TOKEN_KEY, PersistentDataType.BOOLEAN, true);
                token.setItemMeta(meta);
            }
            killer.getInventory().addItem(token);
            killer.sendMessage(ChatColor.GOLD + "You received a " + TOKEN_NAME + ChatColor.BLUE + " for killing a Player!");
        }
    }
}
