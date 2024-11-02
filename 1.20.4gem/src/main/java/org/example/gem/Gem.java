package org.example.gem;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Gem {
    private final String name;
    private int level;
    private final GemPlugin plugin;

    public Gem(String name, int level, GemPlugin plugin) {
        this.name = name;
        this.level = level;
        this.plugin = plugin;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void upgrade() {
        if (level < 3) level++;
    }

    public void useAbility(Player player) {
        switch (name) {
            case "Blade Gem":
                activateBladeAbility(player);
                break;
            case "Archer Gem":
                activateArcherAbility(player);
                break;
            case "Wind Gem":
                activateWindAbility(player);
                break;
            case "Freeze Gem":
                activateFreezeAbility(player);
                break;
            case "Lava Gem":
                activateLavaAbility(player);
                break;
        }
    }

    private void activateBladeAbility(Player player) {
        switch (level) {
            case 1:
                player.sendMessage("Blade Gem Level 1: Sword summoned to hit players within 8 blocks!");
                break;
            case 2:
                player.sendMessage("Blade Gem Level 2: You are now immune to damage for 10 seconds!");
                break;
            case 3:
                player.sendMessage("Blade Gem Level 3: Shockwave activated!");
                break;
        }
    }

    private void activateArcherAbility(Player player) {
        switch (level) {
            case 1:
                player.sendMessage("Archer Gem Level 1: Summon a lightning-infused bow!");
                break;
            case 2:
                player.sendMessage("Archer Gem Level 2: Arrows rain from the sky!");
                break;
            case 3:
                player.sendMessage("Archer Gem Level 3: Two demons summoned to aid you!");
                summonDemons(player);
                break;
        }
    }

    private void activateWindAbility(Player player) {
        switch (level) {
            case 1:
                player.sendMessage("Wind Gem Level 1: You can now fly for 15 seconds!");
                player.setAllowFlight(true);
                Bukkit.getScheduler().runTaskLater(plugin, () -> player.setAllowFlight(false), 300L); // 15 seconds
                break;
            case 2:
                player.sendMessage("Wind Gem Level 2: You are invisible for 20 seconds!");
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 400, 1));
                break;
            case 3:
                player.sendMessage("Wind Gem Level 3: Entering spectator mode.");
                player.setGameMode(GameMode.SPECTATOR);
                Bukkit.getScheduler().runTaskLater(plugin, () -> player.setGameMode(GameMode.SURVIVAL), 400L); // 20 seconds
                break;
        }
    }

    private void activateFreezeAbility(Player player) {
        switch (level) {
            case 1:
                player.sendMessage("Freeze Gem Level 1: Nearby players slowed.");
                break;
            case 2:
                player.sendMessage("Freeze Gem Level 2: Shooting icy arrows!");
                break;
            case 3:
                player.sendMessage("Freeze Gem Level 3: Opponent's sword disappears for 10 seconds!");
                break;
        }
    }

    private void activateLavaAbility(Player player) {
        switch (level) {
            case 1:
                player.sendMessage("Lava Gem Level 1: Launching a fireball!");
                player.launchProjectile(Fireball.class);
                break;
            case 2:
                player.sendMessage("Lava Gem Level 2: Lava flows around you!");
                break;
            case 3:
                player.sendMessage("Lava Gem Level 3: Summoning wither skeletons!");
                for (int i = 0; i < 12; i++) {
                    WitherSkeleton skeleton = (WitherSkeleton) player.getWorld().spawnEntity(player.getLocation(), EntityType.WITHER_SKELETON);
                    skeleton.setTarget((LivingEntity) player.getTargetEntity(10));
                }
                break;
        }
    }

    private void summonDemons(Player player) {
        for (int i = 0; i < 2; i++) {
            Skeleton demon = (Skeleton) player.getWorld().spawnEntity(player.getLocation(), EntityType.SKELETON);
            demon.getEquipment().setItemInMainHand(new ItemStack(Material.BOW));
            demon.setTarget((LivingEntity) player.getTargetEntity(10));
        }
    }
}
