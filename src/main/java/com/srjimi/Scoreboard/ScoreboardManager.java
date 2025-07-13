package com.srjimi.Scoreboard;

import com.srjimi.Banco.BancoManager;
import com.srjimi.Clases.Clase;
import com.srjimi.Equipo.EquipoManager;
import com.srjimi.Main;
import com.srjimi.Nivel.NivelManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class ScoreboardManager {

    private final Main plugin;
    private final NivelManager nivelManager;
    private final EquipoManager equipoManager;
    private final HashMap<UUID, org.bukkit.scoreboard.Scoreboard> playerBoards = new HashMap<>();

    public ScoreboardManager(Main plugin,NivelManager nivelManager,EquipoManager equipoManager) {
        this.plugin = plugin;
        this.nivelManager = nivelManager;
        this.equipoManager = equipoManager;
    }

    public void CreaActualizaScoreboard(Player jugador) {
        org.bukkit.scoreboard.ScoreboardManager mainManager = Bukkit.getScoreboardManager();
        org.bukkit.scoreboard.Scoreboard board = mainManager.getNewScoreboard();

        Objective objective = board.registerNewObjective("rpg", "dummy", ChatColor.GOLD+" "+ChatColor.MAGIC+"ppp"+ChatColor.GOLD + ChatColor.BOLD + "✦ JimWord RPG ✦"+ChatColor.MAGIC+"ppp");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        int score = 0;
        int nivel = nivelManager.getLevel(jugador);
        int xp = nivelManager.getXP(jugador);
        int xpNeeded = nivelManager.getXPNeeded(nivel);

        String equipoNombre = "Sin equipo";
        if (plugin.getEquipoManager().estaEnEquipo(jugador.getName())) {
            String eq = plugin.getEquipoManager().obtenerEquipoPorMiembro(jugador.getName());
            equipoNombre = eq;
        }

        Clase claseObj = plugin.getClasesMain().getClase(jugador);
        String claseNombre = (claseObj != null) ? claseObj.getNombre() : "Sin clase";

        String colorNivel = getColorPorNivel(nivel);
        String colorClase = getColorClase(claseNombre);

        objective.getScore(" ").setScore(score++);

        objective.getScore(ChatColor.GOLD + "   ➤"+ChatColor.WHITE+" Plata: "+ChatColor.GRAY+BancoManager.getPlata(jugador)).setScore(score++);
        objective.getScore(ChatColor.GOLD + "   ➤"+ChatColor.WHITE+" Oro:"+ChatColor.GOLD+ BancoManager.getOro(jugador)).setScore(score++);
        objective.getScore(ChatColor.GREEN + "Monedas").setScore(score++);

        objective.getScore(ChatColor.GOLD+" ").setScore(score++);
        objective.getScore(ChatColor.GOLD + "   ➤ "+ChatColor.WHITE+xp+" / "+xpNeeded).setScore(score++);
        objective.getScore(ChatColor.GREEN + "Nivel: " + colorNivel + nivel).setScore(score++);
        objective.getScore(ChatColor.GOLD + "   ➤ "+ChatColor.WHITE+equipoNombre).setScore(score++);
        objective.getScore(ChatColor.GREEN + "Equipo").setScore(score++);
        objective.getScore(ChatColor.GOLD + "   ➤"+colorClase+claseNombre).setScore(score++);
        objective.getScore(ChatColor.GREEN + "Clase").setScore(score++);

        objective.getScore(ChatColor.RED+" ").setScore(score++);
        objective.getScore(ChatColor.GOLD + "   ➤ " + ChatColor.WHITE + jugador.getName()).setScore(score++);
        objective.getScore(ChatColor.GREEN + "Nombre").setScore(score++);


        objective.getScore(ChatColor.DARK_GRAY + " ").setScore(score++);


        jugador.setScoreboard(board);
        playerBoards.put(jugador.getUniqueId(), board);
    }
    private String getColorClase(String clase) {
        return switch (clase) {
            case "Guerrero" -> "§b";
            case "Mago" -> "§5";
            case "Tanque" -> "§c";
            default -> "§f";
        };
    }

    private String getColorPorNivel(int nivel) {
        if (nivel >= 100) return  "§6"; // Dorado
        if (nivel >= 90) return "§4";   // rojo
        if (nivel >= 80) return "§e";   // amarillo
        if (nivel >= 70) return "§d";   // rosa
        if (nivel >= 60) return "§5";   // morado
        if (nivel >= 50) return "§0";   // negro
        if (nivel >= 30) return "§9";   // Azul
        if (nivel >= 20) return "§8";   // Gris Oscuro
        if (nivel >= 10) return "§a";   // Verde
        if (nivel >= 5)  return "§b";   // Cian claro
        return "§f";                    // Gris claro
    }

    public void removerScoreboard(Player jugador) {
        playerBoards.remove(jugador.getUniqueId());
        jugador.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }
}
