package com.srjimi.Nivel;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class NivelGuardar {

    private static File archivo;
    private static FileConfiguration config;

    public static void cargarArchivo() {
        archivo = new File("plugins/JimiRPG", "Nivel.yml");
        if (!archivo.exists()) {
            archivo.getParentFile().mkdirs();
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(archivo);
    }

    public static void guardarArchivo() {
        try {
            config.save(archivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void establecer(Player jugador, int nivel, int xp) {
        UUID uuid = jugador.getUniqueId();
        config.set(uuid + ".nivel", nivel);
        config.set(uuid + ".xp", xp);
        guardarArchivo();
    }

    public static int obtenerNivel(Player jugador) {
        UUID uuid = jugador.getUniqueId();
        return config.getInt(uuid + ".nivel", 1); // Default: nivel 1
    }

    public static int obtenerXP(Player jugador) {
        UUID uuid = jugador.getUniqueId();
        return config.getInt(uuid + ".xp", 0); // Default: 0 xp
    }

    public static void establecerXP(Player jugador, int xp) {
        UUID uuid = jugador.getUniqueId();
        config.set(uuid + ".xp", xp);
        guardarArchivo();
    }

    public static void establecerNivel(Player jugador, int nivel) {
        UUID uuid = jugador.getUniqueId();
        config.set(uuid + ".nivel", nivel);
        guardarArchivo();
    }
}
