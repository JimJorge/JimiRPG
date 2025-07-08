package com.srjimi.Equipo;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.srjimi.Main;

public class EquipoManager {

    private final Main plugin;
    private final File archivo;
    private final FileConfiguration config;

    public EquipoManager(Main plugin) {
        this.plugin = plugin;
        this.archivo = new File(plugin.getDataFolder(), "Equipos.yml");

        if (!archivo.exists()) {
            try {
                archivo.getParentFile().mkdirs();
                archivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.config = YamlConfiguration.loadConfiguration(archivo);
    }

    public boolean crearEquipo(String nombre, Player lider) {
        if (config.contains("equipos." + nombre)) return false;

        UUID uuid = lider.getUniqueId();

        config.set("equipos." + nombre + ".miembros." + uuid + ".nombre", lider.getName());
        config.set("equipos." + nombre + ".miembros." + uuid + ".rol", EquipoRol.LIDER.name());

        guardar();
        return true;
    }

    public boolean eliminarEquipo(String nombre) {
        if (!config.contains("equipos." + nombre)) return false;
        config.set("equipos." + nombre, null);
        guardar();
        return true;
    }

    public boolean agregarMiembro(String equipo, Player jugador, EquipoRol rol) {
        if (!config.contains("equipos." + equipo)) return false;

        UUID uuid = jugador.getUniqueId();

        config.set("equipos." + equipo + ".miembros." + uuid + ".nombre", jugador.getName());
        config.set("equipos." + equipo + ".miembros." + uuid + ".rol", rol.name());

        guardar();
        return true;
    }

    public boolean removerMiembro(String equipo, UUID uuid) {
        if (!config.contains("equipos." + equipo + ".miembros." + uuid)) return false;

        config.set("equipos." + equipo + ".miembros." + uuid, null);
        guardar();
        return true;
    }

    public EquipoRol getRol(String equipo, UUID uuid) {
        String path = "equipos." + equipo + ".miembros." + uuid + ".rol";
        if (!config.contains(path)) return null;
        return EquipoRol.valueOf(config.getString(path));
    }

    public String getEquipoDeJugador(UUID uuid) {
        for (String equipo : config.getConfigurationSection("equipos").getKeys(false)) {
            if (config.contains("equipos." + equipo + ".miembros." + uuid)) {
                return equipo;
            }
        }
        return null;
    }

    private void guardar() {
        try {
            config.save(archivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
