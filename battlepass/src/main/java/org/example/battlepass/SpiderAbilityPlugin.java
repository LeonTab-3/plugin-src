package org.example.battlepass;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class SpiderAbilityPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("SpiderAbility Plugin Enabled");
        Bukkit.getPluginManager().registerEvents(new SpiderKillListener(this), this);
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("SpiderAbility Plugin Disabled");
    }
}
