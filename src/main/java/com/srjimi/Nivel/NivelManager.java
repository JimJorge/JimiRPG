package com.srjimi.Nivel;

import com.srjimi.Banco.BancoItemUtil;
import com.srjimi.Banco.BancoManager;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import com.srjimi.Main;

public class NivelManager {

    private final HashMap<UUID, Integer> playerXP = new HashMap<>();
    private final HashMap<UUID, Integer> playerLevel = new HashMap<>();
    private final HashMap<UUID, BossBar> bossBars = new HashMap<>();
    private final Main plugin;
    private File nivelFile;
    private FileConfiguration nivelConfig;

    private final int[] xpTable = new int[100];

    public NivelManager(Main plugin) {
        this.plugin = plugin;

        nivelFile = new File(plugin.getDataFolder(), "niveles.yml");

        if (!nivelFile.exists()) {
            try {
                plugin.saveResource("niveles.yml", false);
            } catch (IllegalArgumentException e) {
                // Si no existe, lo creamos vacío
                try {
                    nivelFile.getParentFile().mkdirs();
                    nivelFile.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        nivelConfig = YamlConfiguration.loadConfiguration(nivelFile);
        cargarNiveles(); // ← Carga al iniciar


        // Niveles escalacion
        int maxLevel = 100;
        int maxXP = 1_000_000; // 1 millón de XP para nivel 100

        // Precalcular XP necesarios para subir de nivel de forma cuadrática
        for (int i = 0; i < maxLevel; i++) {
            // XP acumulado para nivel i+1 (porque i desde 0)
            double xpAcumuladoProximo = maxXP * Math.pow(i + 1, 2) / (maxLevel * maxLevel);
            double xpAcumuladoActual = (i == 0) ? 0 : maxXP * Math.pow(i, 2) / (maxLevel * maxLevel);

            xpTable[i] = (int) Math.round(xpAcumuladoProximo - xpAcumuladoActual);
        }
    }


    public int getXP(Player player) {
        return playerXP.getOrDefault(player.getUniqueId(), 0);
    }

    public int getLevel(Player player) {
        return playerLevel.getOrDefault(player.getUniqueId(), 1);
    }

    public int getXPNeeded(int currentLevel) {
        if (currentLevel <= 0 || currentLevel >= 100) {
            return Integer.MAX_VALUE; // Nivel máximo alcanzado
        }
        return xpTable[currentLevel - 1];
    }

    public void addXP(Player player, int amount) {
        UUID uuid = player.getUniqueId();
        int xp = getXP(player) + amount;
        int level = getLevel(player);
        boolean leveledUp = false;

        while (level < 100 && xp >= getXPNeeded(level)) {
            xp -= getXPNeeded(level);
            level++;
            leveledUp = true;
        }

        playerXP.put(uuid, xp);
        playerLevel.put(uuid, level);

        if (leveledUp) {
            player.sendTitle("§a¡Nivel " + level + "!", "Has subido de nivel", 10, 70, 20);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            nivelesStatus(player,level);
        }
        guardarNivel(player);
        plugin.getScoreboardManager().CreaActualizaScoreboard(player);
    }

    public void nivelesStatus(Player player, int lvl){
        if(lvl == 5){
            BancoManager.addOro(player,2);
            player.sendMessage("§aFelicidades por llegar al nivel 5 se le regalo 5 de oro");
            plugin.getScoreboardManager().CreaActualizaScoreboard(player);
        }else if(lvl == 10 || lvl == 20 || lvl == 30 || lvl == 40 || lvl == 50 || lvl == 60 || lvl == 70 || lvl == 80 || lvl == 90 || lvl == 100){
            BancoManager.addOro(player,10);
            player.sendMessage("§aFelicidades por llegar al nivel "+lvl+" se le regalo 10 de oro");
            plugin.getScoreboardManager().CreaActualizaScoreboard(player);
        }else{
            BancoManager.addPlata(player,5);
            player.sendMessage("§aFelicidades por llegar al nivel "+lvl+" se le regalo 5 de plata");
            plugin.getScoreboardManager().CreaActualizaScoreboard(player);
        }
    }

    public void guardarNivel(Player player) {
        UUID uuid = player.getUniqueId();
        int xp = getXP(player);
        int level = getLevel(player);

        nivelConfig.set("jugadores." + uuid + ".xp", xp);
        nivelConfig.set("jugadores." + uuid + ".nivel", level);

        try {
            nivelConfig.save(nivelFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void guardarTodos() {
        for (UUID uuid : playerXP.keySet()) {
            nivelConfig.set("jugadores." + uuid + ".xp", playerXP.get(uuid));
            nivelConfig.set("jugadores." + uuid + ".nivel", playerLevel.get(uuid));
        }

        try {
            nivelConfig.save(nivelFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void cargarNiveles() {
        if (!nivelConfig.contains("jugadores")) return;

        for (String key : nivelConfig.getConfigurationSection("jugadores").getKeys(false)) {
            UUID uuid = UUID.fromString(key);
            int xp = nivelConfig.getInt("jugadores." + key + ".xp");
            int level = nivelConfig.getInt("jugadores." + key + ".nivel");

            playerXP.put(uuid, xp);
            playerLevel.put(uuid, level);
        }
    }
    public void removerBossBar(Player player) {
        UUID uuid = player.getUniqueId();
        BossBar bar = bossBars.remove(uuid);
        if (bar != null) {
            bar.removePlayer(player);
        }
        guardarNivel(player);
    }

}