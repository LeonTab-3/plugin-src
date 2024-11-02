package org.example.rtpgui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class RTPGUIPlugin extends JavaPlugin implements Listener {

    private Map<UUID, Long> teleportCooldowns = new HashMap<>();
    private final long COOLDOWN_TIME = 150 * 1000; // 2.5 minutes in milliseconds

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("rtp").setExecutor(new RTPCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void openRTPGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, "Random Teleport");

        ItemStack rtpItem = createRTPItem();
        gui.setItem(13, rtpItem); // Center item in the middle row

        for (int i = 0; i < gui.getSize(); i++) {
            if (i != 13) {
                gui.setItem(i, createEmptyGlassPane());
            }
        }

        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Random Teleport")) {
            event.setCancelled(true);

            if (event.getRawSlot() == 13) {
                Player player = (Player) event.getWhoClicked();

                if (teleportCooldowns.containsKey(player.getUniqueId())) {
                    long cooldownEnd = teleportCooldowns.get(player.getUniqueId());
                    long currentTime = System.currentTimeMillis();
                    if (currentTime < cooldownEnd) {
                        long remainingTimeSeconds = (cooldownEnd - currentTime) / 1000;
                        player.sendMessage(ChatColor.RED + "You need to wait " + remainingTimeSeconds + " seconds before teleporting again.");
                        return;
                    }
                }

                teleportPlayer(player);
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getView().getTitle().equals("Random Teleport")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.hasMetadata("teleporting")) {
            player.sendMessage(ChatColor.DARK_RED + "");
            player.removeMetadata("teleporting", this);
        }
    }

    private void teleportPlayer(Player player) {
        player.sendMessage("§7[RTP] §eTeleporting in 3 seconds...");

        // Schedule the teleportation task
        new BukkitRunnable() {
            int countdown = 3;
            boolean cancelled = false;

            @Override
            public void run() {
                if (cancelled) {
                    cancel();
                    return;
                }

                if (countdown > 0) {
                    if (!player.isOnline() || !player.isValid() || !player.hasMetadata("teleporting")) {
                        // Player went offline or moved, cancel the teleportation
                        player.sendMessage("§4§l[RTP] Teleportation canceled.");
                        cancel();
                        return;
                    }

                    player.sendMessage("§7[RTP] §e" + countdown + "");
                } else {
                    // Teleport the player to a random location
                    Location randomLocation = getRandomLocation(player.getWorld(), 5780);
                    player.teleport(randomLocation);
                    player.sendMessage("§7[RTP] §eYou have been teleported to a random location!");

                    // Set cooldown
                    long cooldownEnd = System.currentTimeMillis() + COOLDOWN_TIME;
                    teleportCooldowns.put(player.getUniqueId(), cooldownEnd);

                    player.closeInventory();
                    cancel(); // Cancel the task after teleporting
                }
                countdown--;
            }

            @Override
            public synchronized void cancel() throws IllegalStateException {
                super.cancel();
                cancelled = true;
            }
        }.runTaskTimer(this, 0, 20); // Run every second (20 ticks)

        // Set metadata to indicate the player is teleporting
        player.setMetadata("teleporting", new FixedMetadataValue(this, true));
    }

    private Location getRandomLocation(World world, int radius) {
        Random random = new Random();
        int x = random.nextInt(radius * 2) - radius;
        int z = random.nextInt(radius * 2) - radius;
        int y = world.getHighestBlockYAt(x, z); // Get the highest block at (x, z) to avoid teleporting into the void

        return new Location(world, x, y, z);
    }

    private ItemStack createRTPItem() {
        ItemStack rtpItem = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta rtpMeta = rtpItem.getItemMeta();
        rtpMeta.setDisplayName("§c§lOverworld rtp");
        rtpItem.setItemMeta(rtpMeta);
        return rtpItem;
    }

    private ItemStack createEmptyGlassPane() {
        ItemStack pane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = pane.getItemMeta();
        meta.setDisplayName(" ");
        pane.setItemMeta(meta);
        return pane;
    }
}
