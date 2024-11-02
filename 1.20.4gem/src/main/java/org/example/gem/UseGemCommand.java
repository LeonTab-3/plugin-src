package org.example.gem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UseGemCommand implements CommandExecutor {

    private final GemListener gemListener;

    public UseGemCommand(GemListener gemListener) {
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
            gem.useAbility(player);
        }

        return true;
    }
}
