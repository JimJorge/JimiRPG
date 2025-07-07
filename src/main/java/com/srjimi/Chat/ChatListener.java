package com.srjimi.Chat;

import com.srjimi.Nivel.NivelManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player jugador = event.getPlayer();
        String mensaje = event.getMessage();

        // Personalización del chat
        int nivel = NivelManager.getNivel(jugador);
        String prefijo = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Nvl. "+ nivel + ChatColor.DARK_GRAY + "]";
        String nombre = ChatColor.YELLOW + jugador.getName();
        String flecha = ChatColor.GRAY + " » ";
        String texto = ChatColor.WHITE + mensaje;

        event.setFormat(prefijo + " " + nombre + flecha + texto);
    }
}
