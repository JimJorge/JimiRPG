package com.srjimi.Comandos;

import com.srjimi.Equipo.EquipoManager;
import com.srjimi.Equipo.EquipoRol;
import com.srjimi.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ComandosEquipo {

    private final Main plugin;
    private final EquipoManager equipoManager;

    public ComandosEquipo(Main plugin,EquipoManager equiposManager) {
        this.plugin = plugin;
        this.equipoManager = equiposManager; // Asegúrate de tener un getter en Main
    }

    public void ejecutar(CommandSender sender, String[] args) {
        if (!(sender instanceof Player jugador)) {
            sender.sendMessage(Component.text("Este comando solo puede ser usado por jugadores."));
            return;
        }

        if (args.length == 0) {
            jugador.sendMessage(Component.text("§eUsa /equipo <crear|eliminar|unirse|salir|info>"));
            return;
        }

        String subcomando = args[0].toLowerCase();

        switch (subcomando) {

            case "crear":
                if (args.length < 2) {
                    jugador.sendMessage("§cUso: /equipo crear <nombre>");
                    return;
                }
                String nombreCrear = args[1];
                if (equipoManager.getEquipoDeJugador(jugador.getUniqueId()) != null) {
                    jugador.sendMessage("§cYa perteneces a un equipo. Usa /equipo salir primero.");
                    return;
                }
                boolean creado = equipoManager.crearEquipo(nombreCrear, jugador);
                jugador.sendMessage(creado ? "§aEquipo creado exitosamente." : "§cEse equipo ya existe.");
                break;

            case "eliminar":
                if (!jugador.hasPermission("jimirpg.admin")) {
                    jugador.sendMessage("§cNo tienes permiso para eliminar equipos.");
                    return;
                }
                if (args.length < 2) {
                    jugador.sendMessage("§cUso: /equipo eliminar <nombre>");
                    return;
                }
                String nombreEliminar = args[1];
                boolean eliminado = equipoManager.eliminarEquipo(nombreEliminar);
                jugador.sendMessage(eliminado ? "§aEquipo eliminado." : "§cEse equipo no existe.");
                break;

            case "unirse":
                if (args.length < 2) {
                    jugador.sendMessage("§cUso: /equipo unirse <nombre>");
                    return;
                }
                if (equipoManager.getEquipoDeJugador(jugador.getUniqueId()) != null) {
                    jugador.sendMessage("§cYa estás en un equipo. Usa /equipo salir primero.");
                    return;
                }
                String nombreUnirse = args[1];
                boolean unido = equipoManager.agregarMiembro(nombreUnirse, jugador, EquipoRol.MIEMBRO);
                jugador.sendMessage(unido ? "§aTe has unido al equipo " + nombreUnirse : "§cEse equipo no existe.");
                break;

            case "salir":
                String equipoActual = equipoManager.getEquipoDeJugador(jugador.getUniqueId());
                if (equipoActual == null) {
                    jugador.sendMessage("§cNo estás en ningún equipo.");
                    return;
                }
                equipoManager.removerMiembro(equipoActual, jugador.getUniqueId());
                jugador.sendMessage("§eHas salido del equipo §f" + equipoActual + "§e.");
                break;

            case "info":
                String equipo = equipoManager.getEquipoDeJugador(jugador.getUniqueId());
                if (equipo == null) {
                    jugador.sendMessage("§cNo perteneces a ningún equipo.");
                    return;
                }
                jugador.sendMessage("§6Equipo: §f" + equipo);
                jugador.sendMessage("§6Rol: §f" + equipoManager.getRol(equipo, jugador.getUniqueId()));
                break;

            default:
                jugador.sendMessage("§cSubcomando desconocido. Usa /equipo <crear|eliminar|unirse|salir|info>");
                break;
        }
    }
}
