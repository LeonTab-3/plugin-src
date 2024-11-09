// File: src/main/java/org/example/dungeon/commands/DungeonCommand.java
package org.example.dungeon.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.example.dungeon.DungeonPlugin;

public class DungeonCommand implements CommandExecutor {
    private final DungeonPlugin plugin;

    public DungeonCommand(DungeonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage("/dungeon join - Join the dungeon");
            sender.sendMessage("/dungeon rewards - View your rewards");
            return true;
        }

        if (args[0].equalsIgnoreCase("join")) {
            // Handle dungeon join
            sender.sendMessage("Joining the dungeon...");
        } else if (args[0].equalsIgnoreCase("rewards")) {
            // Handle dungeon rewards display
            sender.sendMessage("Displaying rewards...");
        } else {
            sender.sendMessage("Invalid command. Type /dungeon help for options.");
        }

        return true;
    }
}
