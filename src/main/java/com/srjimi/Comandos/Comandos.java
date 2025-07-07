package com.srjimi.Comandos;

import com.srjimi.Manager.ClaseManager;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Comandos {

    private final ClaseManager claseManager;

    public Comandos(ClaseManager claseManager) {
        this.claseManager = claseManager;
    }

    public void ejecutar(CommandSender sender, String[] args) {
        if (!(sender instanceof Player jugador)) {
            sender.sendMessage(Component.text("Solo jugadores pueden usar este comando."));
            return;
        }

        if (args.length == 0) {
            jugador.sendMessage(Component.text("Usa /clase <guerrero|mago|arquero>"));
            return;
        }
        if (!jugador.hasPermission("jimirpg.admin")) {
            jugador.sendMessage("§cNo tienes permiso para usar este comando.");
            return;
        }
        String clase = args[0].toLowerCase();

        switch (clase) {
            case "guerrero", "mago", "arquero" -> {
                claseManager.setClase(jugador.getUniqueId(), clase);
                jugador.sendMessage(Component.text("Has elegido la clase: " + clase));
            }
            default -> jugador.sendMessage(Component.text("Clase no válida."));
        }
    }
}
