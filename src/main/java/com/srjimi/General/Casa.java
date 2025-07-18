package com.srjimi.General;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Casa {

    private final File archivo;
    private final FileConfiguration config;

    public Casa(File dataFolder) {
        archivo = new File(dataFolder, "casas.yml");
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(archivo);
    }

    public void guardarCasa(Player jugador) {
        Location loc = jugador.getLocation();
        UUID uuid = jugador.getUniqueId();
        String base = "casas." + uuid;

        config.set(base + ".mundo", loc.getWorld().getName());
        config.set(base + ".x", loc.getX());
        config.set(base + ".y", loc.getY());
        config.set(base + ".z", loc.getZ());
        config.set(base + ".yaw", loc.getYaw());
        config.set(base + ".pitch", loc.getPitch());

        guardarArchivo();
    }

    public Location obtenerCasa(Player jugador) {
        UUID uuid = jugador.getUniqueId();
        String base = "casas." + uuid;

        if (!config.contains(base + ".mundo")) {
            return null;
        }

        String mundo = config.getString(base + ".mundo");
        double x = config.getDouble(base + ".x");
        double y = config.getDouble(base + ".y");
        double z = config.getDouble(base + ".z");
        float yaw = (float) config.getDouble(base + ".yaw");
        float pitch = (float) config.getDouble(base + ".pitch");

        return new Location(Bukkit.getWorld(mundo), x, y, z, yaw, pitch);
    }

    private void guardarArchivo() {
        try {
            config.save(archivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
