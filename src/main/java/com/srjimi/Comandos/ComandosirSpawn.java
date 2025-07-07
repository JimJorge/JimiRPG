package com.srjimi.Comandos;

import com.srjimi.General.SpawnManager;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ComandosirSpawn {

    public void ejecutar(CommandSender sender, String[] args) {
        if (!(sender instanceof Player jugador)) {
            sender.sendMessage(Component.text("Solo jugadores pueden usar este comando."));
            return;
        }

        if (args.length == 0) {
            Location spawn = SpawnManager.obtenerSpawn();
            if (spawn != null) {
                jugador.teleport(spawn);
                jugador.sendMessage("§a¡Has sido teletransportado al spawn!");
            } else {
                jugador.sendMessage("§cEl spawn aún no está configurado.");
            }
        }
    }
}