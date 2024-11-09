package org.example.dungeon.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.example.dungeon.DungeonPlugin;

public class DropCoinCommand implements CommandExecutor {
    private final DungeonPlugin plugin;

    public DropCoinCommand(DungeonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage("Configuration reloaded.");
            return true;
        }
        return false;
    }
}
