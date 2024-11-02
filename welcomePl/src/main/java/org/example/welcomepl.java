package org.example;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class welcomepl extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("My Minecraft Plugin has been enabled!");
        // Register the event listener
        getServer().getPluginManager().registerEvents(new WelcomeListener(), this);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("");

    }
}
