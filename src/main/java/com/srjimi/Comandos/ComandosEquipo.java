package com.srjimi.Comandos;

import com.srjimi.Equipo.EquipoManager;
import com.srjimi.Main;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ComandosEquipo implements BasicCommand {

    private Main plugin;
    private EquipoManager equipoManager;

    public ComandosEquipo(Main plugin, EquipoManager equipoManager) {
        this.plugin = plugin;
        this.equipoManager = equipoManager;
    }

    @Override
    public void execute(CommandSourceStack stack, String[] args) {
        if (!(stack.getSender() instanceof Player player)) {
            stack.getSender().sendMessage(Component.text("Este comando solo puede ser usado por jugadores."));
            return;
        }

        if (args.length == 0) {
            player.sendMessage(Component.text("§eUsa /equipo <crear|eliminar|invitar|aceptar|salir>"));
            return;
        }

        String subcomando = args[0].toLowerCase();

        switch (subcomando) {
            case "crear" -> {
                if (args.length < 2) {
                    player.sendMessage(Component.text("§cEspecifica un nombre para el equipo."));
                    return;
                }
                if (equipoManager.estaEnEquipo(player.getName())) {
                    player.sendMessage(Component.text("§cYa estás en un equipo."));
                    return;
                }
                String nombre = args[1];
                if (equipoManager.crearEquipo(nombre, player)) {
                    plugin.getScoreboardManager().CreaActualizaScoreboard(player);
                    player.sendMessage(Component.text("§aEquipo creado: " + nombre));
                } else {
                    player.sendMessage(Component.text("§cEse nombre ya está en uso."));
                }
            }

            case "invitar" -> {
                if (args.length < 2) {
                    player.sendMessage(Component.text("§cEspecifica a quién invitar."));
                    return;
                }

                if (!equipoManager.estaEnEquipo(player.getName())) {
                    player.sendMessage(Component.text("§cNo estás en ningún equipo."));
                    return;
                }

                String equipo = equipoManager.obtenerEquipo(player.getName());
                String lider = equipoManager.getLider(equipo);

                if (!player.getName().equalsIgnoreCase(lider)) {
                    player.sendMessage(Component.text("§cSolo el líder puede invitar."));
                    return;
                }

                Player invitado = Bukkit.getPlayer(args[1]);
                if (invitado == null) {
                    player.sendMessage(Component.text("§cJugador no encontrado o no está conectado."));
                    return;
                }

                if (equipoManager.estaEnEquipo(invitado.getName())) {
                    player.sendMessage(Component.text("§cEse jugador ya está en un equipo."));
                    return;
                }

                equipoManager.invitarJugador(equipo, invitado.getName());
                player.sendMessage(Component.text("§aInvitación enviada a §e" + invitado.getName()));
                invitado.sendMessage(Component.text("§eHas sido invitado al equipo §b" + equipo + "§e. Usa §a/equipo aceptar §epara unirte."));
            }

            case "aceptar" -> {
                if (!equipoManager.tieneInvitacion(player.getName())) {
                    player.sendMessage(Component.text("§cNo tienes invitaciones."));
                    return;
                }

                if (equipoManager.estaEnEquipo(player.getName())) {
                    player.sendMessage(Component.text("§cYa estás en un equipo."));
                    return;
                }

                if (equipoManager.aceptarInvitacion(player.getName())) {
                    plugin.getScoreboardManager().CreaActualizaScoreboard(player);
                    player.sendMessage(Component.text("§aTe uniste al equipo correctamente."));
                }
            }

            case "salir" -> {
                if (!equipoManager.estaEnEquipo(player.getName())) {
                    player.sendMessage(Component.text("§cNo estás en ningún equipo."));
                    return;
                }

                String equipo = equipoManager.obtenerEquipoPorMiembro(player.getName());
                List<String> miembros = equipoManager.getMiembros(equipo);

                if (miembros.size() == 1) {
                    equipoManager.eliminarEquipo(equipo);
                    player.sendMessage(Component.text("§eSaliste del equipo y fue disuelto por estar vacío."));
                } else {
                    String lider = equipoManager.getLider(equipo);
                    if (player.getName().equalsIgnoreCase(lider)) {
                        player.sendMessage(Component.text("§cEres el líder. No puedes salir del equipo sin antes transferir el liderazgo o eliminarlo."));
                        return;
                    }

                    equipoManager.eliminarMiembro(equipo, player.getName());
                    player.sendMessage(Component.text("§aHas salido del equipo §b" + equipo));
                }

                plugin.getScoreboardManager().CreaActualizaScoreboard(player);
            }

            case "eliminar" -> {
                if (args.length < 2) {
                    player.sendMessage(Component.text("§cEspecifica a quién eliminar del equipo."));
                    return;
                }

                if (!equipoManager.estaEnEquipo(player.getName())) {
                    player.sendMessage(Component.text("§cNo estás en ningún equipo."));
                    return;
                }

                String equipo = equipoManager.obtenerEquipoPorMiembro(player.getName());
                String lider = equipoManager.getLider(equipo);

                if (!player.getName().equalsIgnoreCase(lider)) {
                    player.sendMessage(Component.text("§cSolo el líder puede eliminar miembros del equipo."));
                    return;
                }

                String objetivo = args[1];

                if (!equipoManager.getMiembros(equipo).contains(objetivo)) {
                    player.sendMessage(Component.text("§cEse jugador no está en tu equipo."));
                    return;
                }

                if (objetivo.equalsIgnoreCase(lider)) {
                    player.sendMessage(Component.text("§cNo puedes eliminarte a ti mismo como líder."));
                    return;
                }

                equipoManager.eliminarMiembro(equipo, objetivo);
                player.sendMessage(Component.text("§aHas eliminado a §c" + objetivo + " §adel equipo."));

                Player expulsado = Bukkit.getPlayerExact(objetivo);
                if (expulsado != null && expulsado.isOnline()) {
                    expulsado.sendMessage(Component.text("§cHas sido eliminado del equipo §b" + equipo));
                    plugin.getScoreboardManager().CreaActualizaScoreboard(expulsado);
                }
            }

            default -> player.sendMessage(Component.text("§7Usa: /equipo crear | invitar <jugador> | aceptar | salir | eliminar <jugador>"));
        }
    }

    @Override
    public @Nullable String permission() {
        return null; // Sin restricción de permisos, todos los jugadores pueden usarlo
    }
}
