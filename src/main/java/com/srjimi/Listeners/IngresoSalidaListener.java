package com.srjimi.Listeners;

import com.srjimi.Main;
import org.bukkit.ChatColor;
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
        event.setJoinMessage(null); // Elimina el mensaje por defecto
        event.getPlayer().sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GREEN+"✔"+ChatColor.DARK_GRAY+"]"+ChatColor.GREEN + event.getPlayer().getName());
        plugin.getScoreboardManager().CreaActualizaScoreboard(event.getPlayer());
    }

    @EventHandler
    public void alSalir(PlayerQuitEvent event) {
        event.setQuitMessage(null); // Elimina el mensaje por defecto
        event.getPlayer().sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.RED+"✔"+ChatColor.DARK_GRAY+"]"+ChatColor.RED + event.getPlayer().getName());
        plugin.getScoreboardManager().removerScoreboard(event.getPlayer());
    }

}