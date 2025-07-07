package com.srjimi.Nivel;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import com.srjimi.Nivel.NivelGuardar;

public class NivelManager {

    public static final int NIVEL_MAXIMO = 100;

    // XP base para subir del nivel 1 al 2
    private static final int BASE_XP = 100;
    // Incremento extra de XP requerido para cada nivel sucesivo
    private static final int INCREMENTO_XP = 50;

    // Devuelve el XP requerido para subir del nivel actual al siguiente
    public static int getXPParaSubirNivel(int nivel) {
        if (nivel < 1) nivel = 1;
        return BASE_XP + (INCREMENTO_XP * (nivel - 1));
    }

    // Devuelve el XP total acumulado necesario para llegar a cierto nivel
    public static int getXPAcumuladoParaNivel(int nivel) {
        int totalXP = 0;
        for (int i = 1; i < nivel; i++) {
            totalXP += getXPParaSubirNivel(i);
        }
        return totalXP;
    }

    // Calcula el nivel del jugador basado en su XP actual
    public static int calcularNivelPorXP(int xpActual) {
        int nivel = 1;
        while (nivel < NIVEL_MAXIMO && xpActual >= getXPAcumuladoParaNivel(nivel + 1)) {
            nivel++;
        }
        return nivel;
    }

    // Obtener nivel actual desde NivelGuardar (tu clase que guarda datos)
    public static int getNivel(Player jugador) {
        return NivelGuardar.obtenerNivel(jugador);
    }

    // Obtener XP actual desde NivelGuardar
    public static int getXP(Player jugador) {
        return NivelGuardar.obtenerXP(jugador);
    }

    public static int getXPRequerido(int nivelActual) {
        return getXPAcumuladoParaNivel(nivelActual + 1) - getXPAcumuladoParaNivel(nivelActual);
    }

    // Agregar XP al jugador y verificar subida de nivel
    public static void agregarXP(Player jugador, int cantidad) {
        int xpActual = getXP(jugador);
        int nuevoXP = xpActual + cantidad;

        NivelGuardar.establecerXP(jugador, nuevoXP);

        verificarSubidaNivel(jugador);
    }

    // Quitar XP al jugador y verificar bajada de nivel
    public static void quitarXP(Player jugador, int cantidad) {
        int xpActual = getXP(jugador);
        int nuevoXP = Math.max(0, xpActual - cantidad);

        NivelGuardar.establecerXP(jugador, nuevoXP);

        verificarBajadaNivel(jugador);
    }

    // Verificar si el jugador subió de nivel según XP
    public static void verificarSubidaNivel(Player jugador) {
        int xp = getXP(jugador);
        int nuevoNivel = calcularNivelPorXP(xp);
        int nivelActual = getNivel(jugador);

        if (nuevoNivel > nivelActual) {
            NivelGuardar.establecerNivel(jugador, nuevoNivel);

            jugador.sendMessage("§a¡Has subido al nivel §e" + nuevoNivel + "§a!");
            jugador.sendActionBar(
                    Component.text("¡Has subido al nivel " + nuevoNivel + "!", NamedTextColor.GREEN)
                            .append(Component.text(" ✦", NamedTextColor.GOLD))
            );
            int xpTotal = getXP(jugador);
            NivelGuardar.establecerXP(jugador,xpTotal-xpTotal);
            jugador.playSound(jugador.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        }
    }

    // Verificar si el jugador bajó de nivel según XP
    public static void verificarBajadaNivel(Player jugador) {
        int xp = getXP(jugador);
        int nuevoNivel = calcularNivelPorXP(xp);
        int nivelActual = getNivel(jugador);

        if (nuevoNivel < nivelActual) {
            NivelGuardar.establecerNivel(jugador, nuevoNivel);
            jugador.sendMessage("§cHas bajado al nivel §e" + nuevoNivel + "§c.");
        }
    }

    // Subir nivel manualmente (limita al máximo)
    public static void subirNivel(Player jugador) {
        int nivel = getNivel(jugador);
        if (nivel < NIVEL_MAXIMO) {
            NivelGuardar.establecerNivel(jugador, nivel + 1);
            int xpTotal = getXP(jugador);
            NivelGuardar.establecerXP(jugador,xpTotal-xpTotal);
            jugador.sendMessage("§a¡Has subido recompensado con §e" + (nivel + 1) + " niveles§a!");
            jugador.sendActionBar(
                    Component.text("¡Has subido al nivel " + (nivel + 1) + "!", NamedTextColor.GREEN)
                            .append(Component.text(" ✦", NamedTextColor.GOLD))
            );
            jugador.playSound(jugador.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        }
    }

    // Bajar nivel manualmente (limita al mínimo 1)
    public static void bajarNivel(Player jugador) {
        int nivel = getNivel(jugador);
        if (nivel > 1) {
            NivelGuardar.establecerNivel(jugador, nivel - 1);
            jugador.sendMessage("§cHas bajado manualmente al nivel §e" + (nivel - 1) + "§c.");
        }
    }
}
