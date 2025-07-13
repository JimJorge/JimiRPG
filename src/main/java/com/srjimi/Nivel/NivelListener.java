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


    public int obtenerXP(EntityType tipo) {
        return switch (tipo) {
            case ZOMBIE, ZOMBIE_VILLAGER, SKELETON, SPIDER -> 10;
            case CREEPER, STRAY, HUSK, DROWNED, ZOMBIFIED_PIGLIN -> 12;
            case SLIME, SILVERFISH, ENDERMITE -> 12;
            case ENDERMAN, PHANTOM, SHULKER, PIGLIN, GUARDIAN, HOGLIN -> 15;
            case WITCH, CAVE_SPIDER, PILLAGER, BOGGED -> 20;
            case PIGLIN_BRUTE, VINDICATOR, ILLUSIONER, MAGMA_CUBE -> 25;
            case WITHER_SKELETON, ZOGLIN -> 25;
            case EVOKER, BLAZE, VEX, BREEZE -> 30;
            case GHAST -> 30;
            case RAVAGER -> 40;
            case ELDER_GUARDIAN -> 60;
            case WARDEN -> 150;
            case WITHER -> 250;
            case ENDER_DRAGON -> 500;
            default -> 0;
        };
    }

}
