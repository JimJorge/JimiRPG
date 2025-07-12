package com.srjimi.Equipo;

import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.block.Block;
import org.bukkit.Material;

public class EquipoListener implements Listener {

    private final EquipoManager equipoManager;

    public EquipoListener(EquipoManager equipoManager) {
        this.equipoManager = equipoManager;
    }

    /**
     * 1. Evitar daño entre miembros del mismo equipo
     */
    @EventHandler
    public void alDañarJugador(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player victima)) return;
        if (!(event.getDamager() instanceof Player atacante)) return;

        String equipoVictima = equipoManager.obtenerEquipo(victima.getName());
        String equipoAtacante = equipoManager.obtenerEquipo(atacante.getName());

        if (equipoVictima != null && equipoVictima.equals(equipoAtacante)) {
            event.setCancelled(true);
            atacante.sendMessage("§cNo puedes dañar a un compañero de tu equipo.");
        }
    }

    @EventHandler
    public void alIntentarAbrirCofre(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        Block bloque = event.getClickedBlock();

        if (bloque.getType() != Material.CHEST) return;

        Player player = event.getPlayer();
        String equipoJugador = equipoManager.obtenerEquipo(player.getName());

        // Revisar si hay un cartel al frente del cofre
        for (BlockFace face : BlockFace.values()) {
            Block bloqueFrente = bloque.getRelative(face);
            if (!bloqueFrente.getType().name().contains("SIGN")) continue;

            Sign cartel = (Sign) bloqueFrente.getState();
            String linea1 = cartel.getLine(0);
            String linea2 = cartel.getLine(1);

            if (linea1.equalsIgnoreCase("[Equipo]")) {
                String equipoCofre = linea2;

                if (equipoJugador == null || !equipoJugador.equalsIgnoreCase(equipoCofre)) {
                    player.sendMessage("§cEste cofre pertenece al equipo §e" + equipoCofre);
                    event.setCancelled(true);
                }
                return; // si hay un cartel válido, no sigue buscando
            }
        }
    }
}
