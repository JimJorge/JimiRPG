package com.srjimi.Scoreboard;

import com.srjimi.Banco.BancoManager;
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
    private final HashMap<UUID, org.bukkit.scoreboard.Scoreboard> playerBoards = new HashMap<>();

    public ScoreboardManager(Main plugin) {
        this.plugin = plugin;
    }

    public void CreaActualizaScoreboard(Player jugador) {
        org.bukkit.scoreboard.ScoreboardManager mainManager = Bukkit.getScoreboardManager();
        org.bukkit.scoreboard.Scoreboard board = mainManager.getNewScoreboard();

        Objective objective = board.registerNewObjective("rpg", "dummy", ChatColor.GOLD+" "+ChatColor.MAGIC+"ppp"+ChatColor.GOLD + ChatColor.BOLD + "✦ JimWord RPG ✦"+ChatColor.MAGIC+"ppp");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        int xpTotal = NivelManager.getXP(jugador);
        int nivel = NivelManager.getNivel(jugador);

        // XP cuando comenzó este nivel
        int xpInicioNivel = NivelManager.getXPAcumuladoParaNivel(nivel);

        // Cuánto XP necesitas para subir al siguiente nivel
        int xpParaSubir = NivelManager.getXPRequerido(nivel);

        // Cuánto XP lleva dentro del nivel actual
        int xpDentroDelNivel = xpTotal - xpInicioNivel;

        // Generar barra con 40 bloques de longitud (puedes cambiar el número)
        String barra = generarBarraProgreso(xpDentroDelNivel, xpParaSubir, 25);




// Se agregan líneas del 0 (abajo) al 14 (arriba)

        int score = 0;

// Línea inferior decorativa (espacio)
        objective.getScore(" ").setScore(score++);

// Monedas
        objective.getScore(ChatColor.GOLD + "   ➤"+ChatColor.WHITE+" Plata: "+ChatColor.GRAY+BancoManager.getPlata(jugador)).setScore(score++);
        objective.getScore(ChatColor.GOLD + "   ➤"+ChatColor.WHITE+" Oro:"+ChatColor.GOLD+ BancoManager.getOro(jugador)).setScore(score++);
        objective.getScore(ChatColor.GREEN + "Monedas").setScore(score++);

// Nivel
        objective.getScore("   " + barra).setScore(score++);
        objective.getScore(ChatColor.GREEN + "Nivel: " + ChatColor.YELLOW + nivel).setScore(score++);

// Equipo
        objective.getScore(ChatColor.GOLD + "   ➤"+ChatColor.WHITE+" Ninguno").setScore(score++);
        objective.getScore(ChatColor.GREEN + "Equipo").setScore(score++);

// Clase
        objective.getScore(ChatColor.GOLD + "   ➤"+ChatColor.WHITE+" Ninguna").setScore(score++);
        objective.getScore(ChatColor.GREEN + "Clase").setScore(score++);

// Separador central
        objective.getScore(ChatColor.DARK_GRAY + "──────────────").setScore(score++);

// Nombre del jugador
        objective.getScore(ChatColor.GOLD + "   ➤ " + ChatColor.WHITE + jugador.getName()).setScore(score++);
        objective.getScore(ChatColor.GREEN + "Nombre").setScore(score++);

// Línea superior decorativa
        objective.getScore(ChatColor.DARK_GRAY + " ").setScore(score++);


        jugador.setScoreboard(board);
        playerBoards.put(jugador.getUniqueId(), board);
    }

    public String generarBarraProgreso(int xpActual, int xpRequerido, int bloques) {
        if (xpRequerido <= 0) xpRequerido = 1;  // evitar división por cero o negativo
        if (xpActual < 0) xpActual = 0;

        int porcentaje = (int) (((double) xpActual / xpRequerido) * 100);
        porcentaje = Math.min(100, Math.max(0, porcentaje)); // limitar entre 0 y 100

        int llenos = (porcentaje * bloques) / 100;
        int vacios = bloques - llenos;

        // Asegurarse que no haya números negativos
        llenos = Math.max(0, llenos);
        vacios = Math.max(0, vacios);

        ChatColor color;
        if (porcentaje < 30) {
            color = ChatColor.RED;
        } else if (porcentaje < 70) {
            color = ChatColor.GOLD;
        } else {
            color = ChatColor.GREEN;
        }

        return " " + color + "▮".repeat(llenos) + ChatColor.GRAY + "▯".repeat(vacios) + " ";
    }



    public void removerScoreboard(Player jugador) {
        playerBoards.remove(jugador.getUniqueId());
        jugador.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

}
