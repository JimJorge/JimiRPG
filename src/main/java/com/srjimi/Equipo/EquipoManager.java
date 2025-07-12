package com.srjimi.Equipo;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EquipoManager {

    private final JavaPlugin plugin;
    private final File file;
    final FileConfiguration config;

    private final Map<String, String> invitaciones = new HashMap<>(); // jugador -> nombre del equipo

    public EquipoManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "equipos.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public boolean crearEquipo(String nombre, Player lider) {
        if (config.contains(nombre)) return false;

        config.set(nombre + ".lider", lider.getName());
        config.set(nombre + ".miembros", List.of(lider.getName()));
        guardar();
        return true;
    }

    public boolean estaEnEquipo(String jugador) {
        for (String equipo : config.getKeys(false)) {
            List<String> miembros = config.getStringList(equipo + ".miembros");
            if (miembros.contains(jugador)) return true;
        }
        return false;
    }

    public void invitarJugador(String equipo, String jugador) {
        invitaciones.put(jugador, equipo);
    }

    public boolean tieneInvitacion(String jugador) {
        return invitaciones.containsKey(jugador);
    }

    public boolean aceptarInvitacion(String jugador) {
        if (!invitaciones.containsKey(jugador)) return false;

        String equipo = invitaciones.remove(jugador);
        List<String> miembros = config.getStringList(equipo + ".miembros");
        if (!miembros.contains(jugador)) {
            miembros.add(jugador);
            config.set(equipo + ".miembros", miembros);
            guardar();
        }
        return true;
    }

    private void guardar() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String obtenerEquipo(String jugador) {
        for (String equipo : config.getKeys(false)) {
            List<String> miembros = config.getStringList(equipo + ".miembros");
            if (miembros.contains(jugador)) {
                return equipo;
            }
        }
        return null;
    }

    public String obtenerEquipoPorMiembro(String jugador) {
        for (String equipo : config.getKeys(false)) {
            List<String> miembros = config.getStringList(equipo + ".miembros");
            if (miembros.contains(jugador)) {
                return equipo;
            }
        }
        return null;
    }

    public List<String> getMiembros(String equipo) {
        return config.getStringList(equipo + ".miembros");
    }

    public String getLider(String equipo) {
        return config.getString(equipo + ".lider");
    }

    public void eliminarMiembro(String equipo, String jugador) {
        List<String> miembros = getMiembros(equipo);
        miembros.remove(jugador);
        config.set(equipo + ".miembros", miembros);
        guardar();
    }

    public void eliminarEquipo(String equipo) {
        config.set(equipo, null);
        guardar();
    }

}
