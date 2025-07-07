package com.srjimi.Aldeanos;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AldeanosGuardarYproteger implements Listener {

    private final JavaPlugin plugin;
    private File aldeanosFile;
    private FileConfiguration aldeanosConfig;

    // Set de UUIDs para acceso rápido en memoria
    private Set<UUID> aldeanosProtegidos;

    public AldeanosGuardarYproteger(JavaPlugin plugin) {
        this.plugin = plugin;

        // Cargar o crear archivo
        aldeanosFile = new File(plugin.getDataFolder(), "Aldeanos.yml");
        if (!aldeanosFile.exists()) {
            aldeanosFile.getParentFile().mkdirs();
            plugin.saveResource("Aldeanos.yml", false);
        }
        aldeanosConfig = YamlConfiguration.loadConfiguration(aldeanosFile);

        aldeanosProtegidos = new HashSet<>();
        cargarAldeanos();
    }

    // Cargar aldeanos protegidos de YML a memoria
    private void cargarAldeanos() {
        aldeanosProtegidos.clear();
        if (aldeanosConfig.isList("aldeanos")) {
            for (String uuidStr : aldeanosConfig.getStringList("aldeanos")) {
                try {
                    aldeanosProtegidos.add(UUID.fromString(uuidStr));
                } catch (IllegalArgumentException ignored) {}
            }
        }
    }

    // Guardar aldeanos protegidos de memoria a YML
    private void guardarAldeanos() {
        aldeanosConfig.set("aldeanos", aldeanosProtegidos.stream().map(UUID::toString).toList());
        try {
            aldeanosConfig.save(aldeanosFile);
        } catch (IOException e) {
            plugin.getLogger().severe("No se pudo guardar Aldeanos.yml");
            e.printStackTrace();
        }
    }

    // Añadir aldeano a la lista y guardar
    public void agregarAldeano(Entity aldeano) {
        aldeanosProtegidos.add(aldeano.getUniqueId());
        guardarAldeanos();
    }

    // Quitar aldeano de la lista y guardar
    public void eliminarAldeano(Entity aldeano) {
        aldeanosProtegidos.remove(aldeano.getUniqueId());
        guardarAldeanos();
    }

    // Verifica si un aldeano está protegido
    public boolean estaProtegido(Entity entidad) {
        return aldeanosProtegidos.contains(entidad.getUniqueId());
    }

    // -----------------------------------
    // Eventos para proteger aldeanos
    // -----------------------------------

    // Proteger contra daño directo o indirecto
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (estaProtegido(event.getEntity())) {
            event.setCancelled(true);
        }
    }

    // Proteger contra daño por jugador específicamente
    @EventHandler
    public void onDamageByPlayer(EntityDamageByEntityEvent event) {
        if (estaProtegido(event.getEntity())) {
            if (event.getDamager() instanceof Player) {
                event.setCancelled(true);
            }
        }
    }

    // Proteger contra interacción que pueda mover aldeano (ejemplo: clic derecho)
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (estaProtegido(event.getRightClicked())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("Este aldeano está protegido.");
        }
    }

    // Opcional: evitar que aldeano se mueva (por empujones, física, etc)
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        // Si quieres evitar movimiento de aldeanos, tendrías que hacerlo con otra lógica,
        // porque PlayerMoveEvent es para jugadores, no aldeanos.
        // Se podría usar entidades que no se mueven por sí mismas, o teletransportarlas constantemente.
    }

}
