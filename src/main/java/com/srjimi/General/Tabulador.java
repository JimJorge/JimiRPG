package com.srjimi.General;

import com.srjimi.Main;
import com.srjimi.Clases.Clase;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Tabulador {

    private final Main plugin;
    private final MiniMessage mm = MiniMessage.miniMessage();

    public Tabulador(Main plugin) {
        this.plugin = plugin;
    }

    public void aplicarTab(Player jugador) {
        Clase clase = plugin.getClasesMain().getClase(jugador);
        String nombreClase = clase != null ? clase.getNombre() : "Sin clase";
        int nivel = plugin.getLevelManager().getLevel(jugador);

        Component header = mm.deserialize("""
            <rainbow>⛧ RPG SERVER ⛧</rainbow>
            <gray>──────────────</gray>
            <white>Jugador:</white> <green>""" + jugador.getName() + "</green>\n"
                + "<white>Clase:</white> <gold>" + nombreClase + "</gold>\n"
                + "<white>Nivel:</white> <aqua>" + nivel + "</aqua>");

        Component footer = mm.deserialize("""
            <gray>──────────────</gray>
            <gray>Jugadores conectados:</gray> <green>""" + Bukkit.getOnlinePlayers().size() + "</green>\n"
                + "<aqua>¡Disfruta tu aventura!</aqua>");

        jugador.sendPlayerListHeaderAndFooter(header, footer);
    }

    public void actualizarNombresEnTab() {
        for (Player jugador : Bukkit.getOnlinePlayers()) {
            int nivel = plugin.getLevelManager().getLevel(jugador);
            Clase clase = plugin.getClasesMain().getClase(jugador);
            String color;

            if (clase == null) {
                color = "§7"; // sin clase
            } else {
                switch (clase.getNombre().toLowerCase()) {
                    case "guerrero" -> color = "§c";
                    case "mago" -> color = "§9";
                    case "arquero" -> color = "§a";
                    default -> color = "§f";
                }
            }

            jugador.setPlayerListName(" §7[Lv. " + nivel + "]"+color + jugador.getName() );
        }
    }

    public void aplicarATodos() {
        for (Player jugador : Bukkit.getOnlinePlayers()) {
            aplicarTab(jugador);
        }
        actualizarNombresEnTab();
    }
}