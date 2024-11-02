package org.example.gem;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class GemPlugin extends JavaPlugin {

    private GemListener gemListener;

    @Override
    public void onEnable() {
        // Register event listeners
        gemListener = new GemListener(this);
        Bukkit.getPluginManager().registerEvents(gemListener, this);

        // Register the "/gems" and "/gem" commands
        this.getCommand("gems").setExecutor(new GemsCommand(gemListener));
        this.getCommand("usegem").setExecutor(new UseGemCommand(gemListener));

        getLogger().info("GemPlugin has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("GemPlugin has been disabled.");
    }
}
