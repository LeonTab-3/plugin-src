package org.example.gemplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GemCommand implements CommandExecutor {

    private final GemPlugin plugin;
    private final GemListener gemListener;

    public GemCommand(GemPlugin plugin, GemListener gemListener) {
        this.plugin = plugin;
        this.gemListener = gemListener;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        // Check if the player already chose a gem
        if (gemListener.hasChosenGem(player)) {
            player.sendMessage("You have already chosen a gem! You cannot change it.");
            return true;
        }

        // Show available gem options
        if (args.length == 0) {
            player.sendMessage("Choose a gem: Blade, Archer, Wind, Freeze, Lava");
            player.sendMessage("Use /gem <gemName> to choose your gem.");
            return true;
        }

        String gemName = args[0].toLowerCase();
        Gem gem = null;

        // Assign a gem based on player input
        switch (gemName) {
            case "blade":
                gem = new Gem("Blade Gem");
                break;
            case "archer":
                gem = new Gem("Archer Gem");
                break;
            case "wind":
                gem = new Gem("Wind Gem");
                break;
            case "freeze":
                gem = new Gem("Freeze Gem");
                break;
            case "lava":
                gem = new Gem("Lava Gem");
                break;
            default:
                player.sendMessage("Invalid gem name. Available options are: Blade, Archer, Wind, Freeze, Lava.");
                return true;
        }

        // Save the player's choice and assign the gem
        gemListener.savePlayerGem(player, gem);
        player.sendMessage("You have chosen the " + gem.getName() + "!");

        return true;
    }
}
