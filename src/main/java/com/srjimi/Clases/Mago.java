package com.srjimi.Clases;

import org.bukkit.entity.Player;

public class Mago {

    public static void aplicarBonus(Player jugador) {
        jugador.sendMessage("¡Magia restauradora del Mago activada!");
        jugador.setHealth(Math.min(jugador.getMaxHealth(), jugador.getHealth() + 1));
    }
}