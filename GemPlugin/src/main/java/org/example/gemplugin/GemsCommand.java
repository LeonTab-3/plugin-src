package org.example.gemplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GemsCommand implements CommandExecutor {

    private final GemListener gemListener;

    public GemsCommand(GemListener gemListener) {
        this.gemListener = gemListener;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        Gem gem = gemListener.getGem(player);

        if (gem == null) {
            player.sendMessage("You do not have a gem assigned.");
        } else {
            player.sendMessage("You currently have the " + gem.getName() + " at level " + gem.getLevel() + ".");
        }

        return true;
    }
}
