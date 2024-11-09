package org.example.dungeon.utils;

import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class RewardUtils {
    public static void giveReward(Player player, String item, int amount, int chance) {
        if (new Random().nextInt(100) < chance) {
            player.getInventory().addItem(new ItemStack(Material.getMaterial(item.toUpperCase()), amount));
        }
    }
}
