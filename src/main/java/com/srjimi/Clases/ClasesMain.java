package com.srjimi.Clases;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.srjimi.Main;

public class ClasesMain {
    private final Main plugin;
    // Guarda para cada jugador (UUID) la instancia de su clase actual
    private final Map<UUID, Clase> playerClases = new HashMap<>();

    // Opcional: mapa con las clases disponibles para facilitar la consulta por nombre
    private final Map<String, Clase> clasesDisponibles = new HashMap<>();

    public ClasesMain(Main plugin) {
        this.plugin = plugin;

        // Usa el plugin si lo necesitas más adelante

        clasesDisponibles.put("guerrero", new Guerrero(plugin));
        clasesDisponibles.put("mago", new Mago(plugin));
        clasesDisponibles.put("tanque", new Tanque(plugin));
    }

    // Asignar una clase por nombre, retorna true si se asignó, false si no existe
    public boolean asignarClase(Player player, String nombreClase) {
        Clase clase = clasesDisponibles.get(nombreClase.toLowerCase());
        if (clase != null) {
            playerClases.put(player.getUniqueId(), clase);
            return true;
        }
        return false;
    }

    // Asignar directamente una instancia de Clase
    public void setClase(Player player, Clase clase) {
        playerClases.put(player.getUniqueId(), clase);
    }

    // Obtener la clase actual del jugador (puede ser null si no tiene)
    public Clase getClase(Player player) {
        return playerClases.get(player.getUniqueId());
    }
}
