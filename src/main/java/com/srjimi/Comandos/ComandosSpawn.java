package com.srjimi.Comandos;

import com.srjimi.General.SpawnManager;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ComandosSpawn {

    public void ejecutar(CommandSender sender, String[] args) {
        if (!(sender instanceof Player jugador)) {
            sender.sendMessage(Component.text("Solo jugadores pueden usar este comando."));
            return;
        }

        if (args.length == 0) {
            if (!jugador.hasPermission("jimirpg.admin")) {
                jugador.sendMessage("§cNo tienes permiso para usar este comando.");
                return;
            }

            SpawnManager.establecerSpawn(jugador.getLocation());
            jugador.sendMessage("§eHas establecido el spawn correctamente.");
        }
    }
}
