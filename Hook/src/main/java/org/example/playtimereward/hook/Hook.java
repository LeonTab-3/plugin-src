package org.example.playtimereward.hook;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Hook extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Register events
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    // Listen for the player reeling in the fishing rod
    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Check if the player is holding a fishing rod
        if (item != null && item.getType() == Material.FISHING_ROD) {
            // Check if the player is pulling the hook back (PlayerFishEvent.State.REEL_IN)
            if (event.getState() == PlayerFishEvent.State.REEL_IN) {
                FishHook hook = event.getHook();

                // Calculate the direction and distance for the grappling pull
                Vector playerLocation = player.getLocation().toVector();
                Vector hookLocation = hook.getLocation().toVector();
                Vector pullDirection = hookLocation.subtract(playerLocation).normalize(); // Normalize to just get the direction

                // Multiply by a fixed distance, e.g., 10 blocks
                pullDirection.multiply(10);

                // Set the player's velocity to pull them towards the hook
                player.setVelocity(pullDirection);

                player.sendMessage("You have been grappled!");
            }
        }
    }
}
