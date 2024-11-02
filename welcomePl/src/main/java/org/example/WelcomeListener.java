package org.example;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class WelcomeListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        String welcomeMessage = ChatColor.GRAY + "Welcome " + ChatColor.GREEN + playerName + ChatColor.GRAY + " to this server!";
        event.getPlayer().sendMessage(welcomeMessage);
    }
}
