package com.srjimi.Listeners;

import com.srjimi.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class IngresoSalidaListener implements Listener {

    private final Main plugin;

    public IngresoSalidaListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void alEntrar(PlayerJoinEvent event) {
        Player jugador = event.getPlayer();

        event.setJoinMessage(null);

        String mensaje = ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "✔" + ChatColor.DARK_GRAY + "] "
                + ChatColor.GREEN + jugador.getName() + ChatColor.GRAY + " ha entrado al servidor";

        for (Player online : Bukkit.getOnlinePlayers()) {
            online.sendMessage(mensaje);
        }

        jugador.sendTitle(
                ChatColor.GREEN + "¡Bienvenido!",
                ChatColor.GRAY + "Disfruta tu estancia, " + ChatColor.WHITE + jugador.getName(),
                10, 60, 20 // fadeIn, stay, fadeOut (en ticks)
        );

        jugador.playSound(jugador.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        plugin.getScoreboardManager().CreaActualizaScoreboard(jugador);
    }


    @EventHandler
    public void alSalir(PlayerQuitEvent event) {
        event.setQuitMessage(null); // Elimina el mensaje por defecto

        String mensaje = ChatColor.DARK_GRAY+"["+ChatColor.RED+"✘"+ChatColor.DARK_GRAY+"]"+ChatColor.RED + event.getPlayer().getName();

        for (Player online : Bukkit.getOnlinePlayers()) {
            online.sendMessage(mensaje);
        }

        plugin.getScoreboardManager().removerScoreboard(event.getPlayer());
    }

}