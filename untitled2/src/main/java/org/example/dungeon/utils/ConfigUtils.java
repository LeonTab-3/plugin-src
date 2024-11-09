package org.example.dungeon.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.example.dungeon.DungeonPlugin;

public class ConfigUtils {
    private final DungeonPlugin plugin;

    public ConfigUtils(DungeonPlugin plugin) {
        this.plugin = plugin;
    }

    public void reload() {
        plugin.reloadConfig();
    }
}
