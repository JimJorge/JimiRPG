package com.srjimi.Comandos;

import com.srjimi.General.SpawnManager;
import com.srjimi.Gremio.AldeanoGremio;
import com.srjimi.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ComandosGremio {

    private Main plugin;

    public ComandosGremio(Main plugin){
        this.plugin = plugin;
    }

    public void ejecutar(CommandSender sender, String[] args) {
        if (!(sender instanceof Player jugador)) {
            sender.sendMessage(Component.text("Solo jugadores pueden usar este comando."));
            return;
        }

        if (args.length == 0) {
            jugador.sendMessage(Component.text("Usa /gremio misionesdiarias | misionesextra >"));
            return;
        }
        if (!jugador.hasPermission("jimirpg.admin")) {
            jugador.sendMessage("§cNo tienes permiso para usar este comando.");
            return;
        }
        String clase = args[0].toLowerCase();
        Location loc = jugador.getLocation();
        switch (clase) {
            case "misionesdiarias":
                plugin.getAldeanoGremio().crearAldeanoMisiones(loc);
                jugador.sendMessage(ChatColor.GREEN + "Aldeano del banco creado y protegido.");
                break;

            case "misionesextra":

                jugador.sendMessage(ChatColor.GREEN + "Aldeano del banco creado y protegido.");
                break;
            default:
                jugador.sendMessage("Subcomando no reconocido. Usa /gremio misionesdiarias | misionesextra>");
        }
    }
}
