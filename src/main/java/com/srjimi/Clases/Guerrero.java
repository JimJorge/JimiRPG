package com.srjimi.Clases;


import org.bukkit.entity.Player;

public class Guerrero {

    public static void aplicarBonus(Player jugador) {
        jugador.sendMessage("¡Poder del Guerrero activado!");
        // Por ejemplo, le damos más daño en el listener
    }
}