package org.example.invisarrow;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class InvisibleArrowsPlugin extends JavaPlugin implements Listener {

    private ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        getLogger().info("InvisibleArrowsPlugin has been enabled!");
        Bukkit.getPluginManager().registerEvents(this, this);

        protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new PacketAdapter(this, PacketType.Play.Server.SPAWN_ENTITY) {
            @Override
            public void onPacketSending(PacketEvent event) {
                if (event.getPacket().getEntityModifier(event).read(0) instanceof Arrow) {
                    event.setCancelled(true);
                }
            }
        });
    }

    @Override
    public void onDisable() {
        getLogger().info("InvisibleArrowsPlugin has been disabled!");
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getEntity();

            // Hide the critical (trail) effect
            arrow.setCritical(false);

            // Hide fire effect if any
            arrow.setVisualFire(false);

            // Spawn an invisible armor stand and attach it to the arrow
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!arrow.isDead()) {
                        Location arrowLoc = arrow.getLocation();
                        ArmorStand armorStand = arrowLoc.getWorld().spawn(arrowLoc, ArmorStand.class);
                        armorStand.setVisible(false); // Make the armor stand invisible
                        armorStand.setMarker(true); // Make it a marker (no hitbox)
                        armorStand.setSmall(true); // Make it small to minimize visual impact
                        armorStand.setGravity(false); // Prevent gravity from affecting the armor stand

                        // Attach the armor stand to the arrow as a passenger
                        arrow.addPassenger(armorStand);

                        // Schedule removal of the armor stand and arrow after a delay (optional)
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                armorStand.remove();
                                arrow.remove();
                            }
                        }.runTaskLater(InvisibleArrowsPlugin.this, 200); // Remove after 10 seconds (200 ticks)
                    }
                }
            }.runTaskLater(this, 1); // Run the task 1 tick later to ensure arrow has spawned
        }
    }
}
