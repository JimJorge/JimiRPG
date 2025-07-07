package com.srjimi.Clases;

import org.bukkit.entity.Player;

public class Arquero {

    public static void aplicarBonus(Player jugador) {
        jugador.sendMessage("¡Velocidad del Arquero activada!");
        jugador.setWalkSpeed(0.3f); // Valor aumentado por bonus de velocidad
    }
}