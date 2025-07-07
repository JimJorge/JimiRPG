package com.srjimi.Listeners;

import com.srjimi.Manager.ClaseManager;
import com.srjimi.Clases.Arquero;
import com.srjimi.Clases.Clase;
import com.srjimi.Clases.Guerrero;
import com.srjimi.Clases.Mago;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {

    private final ClaseManager claseManager;

    public DamageListener(ClaseManager claseManager) {
        this.claseManager = claseManager;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player jugador)) return;

        String claseStr = claseManager.getClase(jugador.getUniqueId());
        Clase clase = Clase.desdeNombre(claseStr);

        if (clase == null) return;

        switch (clase) {
            case GUERRERO -> {
                e.setDamage(e.getDamage() + 3.0);
                Guerrero.aplicarBonus(jugador);
            }
            case MAGO -> Mago.aplicarBonus(jugador);
            case ARQUERO -> Arquero.aplicarBonus(jugador);
        }
    }
}
