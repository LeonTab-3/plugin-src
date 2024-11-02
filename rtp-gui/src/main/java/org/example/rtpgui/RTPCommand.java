package org.example.rtpgui;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RTPCommand implements CommandExecutor {

    private final RTPGUIPlugin plugin;

    public RTPCommand(RTPGUIPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            plugin.openRTPGUI(player);
            return true;
        }
        sender.sendMessage("This command can only be used by players.");
        return false;
    }
}
