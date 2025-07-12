package com.srjimi.General;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class SpawnManager {
    private final JavaPlugin plugin;
    private File spawnFile;
    private FileConfiguration spawnConfig;

    public SpawnManager(JavaPlugin plugin) {
        this.plugin = plugin;
        crearArchivo();
    }

    private void crearArchivo() {
        spawnFile = new File(plugin.getDataFolder(), "spawn.yml");
        if (!spawnFile.exists()) {
            try {
                spawnFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        spawnConfig = YamlConfiguration.loadConfiguration(spawnFile);
    }

    public void guardarSpawn(Location loc) {
        spawnConfig.set("spawn.world", loc.getWorld().getName());
        spawnConfig.set("spawn.x", loc.getX());
        spawnConfig.set("spawn.y", loc.getY());
        spawnConfig.set("spawn.z", loc.getZ());
        spawnConfig.set("spawn.yaw", loc.getYaw());
        spawnConfig.set("spawn.pitch", loc.getPitch());
        try {
            spawnConfig.save(spawnFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Location obtenerSpawn() {
        if (!spawnConfig.contains("spawn.world")) return null;
        World world = Bukkit.getWorld(spawnConfig.getString("spawn.world"));
        double x = spawnConfig.getDouble("spawn.x");
        double y = spawnConfig.getDouble("spawn.y");
        double z = spawnConfig.getDouble("spawn.z");
        float yaw = (float) spawnConfig.getDouble("spawn.yaw");
        float pitch = (float) spawnConfig.getDouble("spawn.pitch");
        return new Location(world, x, y, z, yaw, pitch);
    }
}