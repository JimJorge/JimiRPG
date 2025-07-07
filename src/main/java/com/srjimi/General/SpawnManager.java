package com.srjimi.General;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class SpawnManager {

    private static File archivo;
    private static YamlConfiguration config;

    public static void init() {
        archivo = new File(Bukkit.getPluginManager().getPlugin("JimiRPG").getDataFolder(), "Spawn.yml");

        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(archivo);
    }

    public static void establecerSpawn(Location loc) {
        config.set("spawn.world", loc.getWorld().getName());
        config.set("spawn.x", loc.getX());
        config.set("spawn.y", loc.getY());
        config.set("spawn.z", loc.getZ());
        config.set("spawn.yaw", loc.getYaw());
        config.set("spawn.pitch", loc.getPitch());

        try {
            config.save(archivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Location obtenerSpawn() {
        if (!config.contains("spawn.world")) return null;

        String world = config.getString("spawn.world");
        double x = config.getDouble("spawn.x");
        double y = config.getDouble("spawn.y");
        double z = config.getDouble("spawn.z");
        float yaw = (float) config.getDouble("spawn.yaw");
        float pitch = (float) config.getDouble("spawn.pitch");

        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    public static void teletransportarAlSpawn(Player jugador) {
        Location spawn = obtenerSpawn();
        if (spawn != null) {
            jugador.teleport(spawn);
            jugador.sendMessage("§a¡Has sido teletransportado al spawn!");
        } else {
            jugador.sendMessage("§cEl spawn no está configurado.");
        }
    }
}
