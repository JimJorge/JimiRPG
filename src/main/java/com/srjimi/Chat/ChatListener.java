package com.srjimi.Chat;

import com.srjimi.Nivel.NivelManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private NivelManager nivelManager;

    public ChatListener(NivelManager nivelManager) {
        this.nivelManager = nivelManager;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player jugador = event.getPlayer();
        String mensaje = event.getMessage();

        // Personalización del chat
        int nivel = nivelManager.getLevel(jugador);
        String prefijo = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Nvl. "+ nivel + ChatColor.DARK_GRAY + "]";
        String nombre = ChatColor.YELLOW + jugador.getName();
        String flecha = ChatColor.GRAY + " » ";
        String texto = ChatColor.WHITE + mensaje;

        event.setFormat(prefijo + " " + nombre + flecha + texto);
    }
}
