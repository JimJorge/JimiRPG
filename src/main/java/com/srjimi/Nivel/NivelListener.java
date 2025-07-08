package com.srjimi.Nivel;

import com.srjimi.Gremio.Mision;
import com.srjimi.Main;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.entity.EntityType;

public class NivelListener implements Listener {

    private Main plugin;
    private NivelManager nivelManager;

    public NivelListener(Main plugin,NivelManager nivelManager) {
        this.plugin = plugin;
        this.nivelManager = nivelManager;
    }

    @EventHandler
    public void alMatarMobHostil(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Monster)) return;

        Monster mob = (Monster) event.getEntity();
        Player asesino = mob.getKiller(); // <- aquí ya no da error

        if (asesino == null) return;

        int xp = obtenerXP(mob.getType());

        if (xp > 0) {
            // Progreso de misión
            Mision activa = plugin.getMisionDiariaManager().getMisionActiva(asesino);
            if (activa != null && mob.getType() == activa.getMobObjetivo())
                plugin.getMisionDiariaManager().aumentarProgreso(asesino);

            nivelManager.addXP(asesino, xp);
            asesino.sendMessage("§e+" + xp + " XP por matar un §6" + mob.getType().name().toLowerCase().replace("_", " ") + "§e.");
            plugin.getScoreboardManager().CreaActualizaScoreboard(asesino);
        }
    }


    // Mapeo de XP por tipo de mob hostil
    public int obtenerXP(EntityType tipo) {
        return switch (tipo) {
            case ZOMBIE, SKELETON, SPIDER -> 10;
            case CREEPER, STRAY, HUSK, DROWNED -> 12;
            case SLIME -> 12;
            case ENDERMAN -> 15;
            case WITCH -> 20;
            case PIGLIN_BRUTE -> 25;
            case VINDICATOR, ILLUSIONER -> 20;
            case EVOKER -> 30;
            case RAVAGER -> 40;
            case BLAZE -> 20;
            case WITHER_SKELETON -> 25;
            case GHAST -> 30;
            case MAGMA_CUBE -> 20;
            case WARDEN -> 150;
            case WITHER -> 250;
            case ENDER_DRAGON -> 500;
            default -> 0;
        };
    }
}
