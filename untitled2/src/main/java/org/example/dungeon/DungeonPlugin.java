package org.example.dungeon;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.dungeon.commands.DungeonCommand;
import org.example.dungeon.listeners.PlayerDeathListener;

public class DungeonPlugin extends JavaPlugin {
    private static DungeonPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        // Register commands
        getCommand("dungeon").setExecutor(new DungeonCommand(this));

        // Register listeners
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(this.getConfig()), this);

        // Load configurations
        saveDefaultConfig();
        getLogger().info("Dungeon Plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Dungeon Plugin disabled.");
    }

    public static DungeonPlugin getInstance() {
        return instance;
    }
}
