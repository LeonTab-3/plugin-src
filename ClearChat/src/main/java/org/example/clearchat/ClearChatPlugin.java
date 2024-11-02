package org.example.clearchat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ClearChatPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("ClearChatPlugin has been enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("ClearChatPlugin has been disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("clearchat")) {
            if (sender instanceof Player || sender instanceof ConsoleCommandSender) {
                for (int i = 0; i < 100; i++) {
                    Bukkit.broadcastMessage("");
                }
                Bukkit.broadcastMessage("[ClearChat] The chat was cleared by " + sender.getName());
                return true;
            } else {
                sender.sendMessage("This command can only be executed by a player or the console.");
                return false;
            }
        }
        return false;
    }
}
