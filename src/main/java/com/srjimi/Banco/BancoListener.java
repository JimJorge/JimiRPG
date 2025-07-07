package com.srjimi.Banco;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.entity.Player;

import com.srjimi.Main;

public class BancoListener implements Listener {

    private final Main plugin;

    public BancoListener(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void alEntrar(PlayerJoinEvent event) {
        Player jugador = event.getPlayer();
        String uuid = jugador.getUniqueId().toString();
        FileConfiguration banco = plugin.getBancoConfig();

        if (!banco.contains("banco." + uuid)) {
            banco.set("banco." + uuid + ".oro", 0);
            banco.set("banco." + uuid + ".plata", 0);
            plugin.guardarBancoConfig();
        }

        int oro = banco.getInt("banco." + uuid + ".oro");
        int plata = banco.getInt("banco." + uuid + ".plata");

        BancoManager.setOro(jugador, oro);
        BancoManager.setPlata(jugador, plata);
    }

    @EventHandler
    public void alSalir(PlayerQuitEvent event) {
        Player jugador = event.getPlayer();
        String uuid = jugador.getUniqueId().toString();
        FileConfiguration banco = plugin.getBancoConfig();

        int oro = BancoManager.getOro(jugador);
        int plata = BancoManager.getPlata(jugador);

        banco.set("banco." + uuid + ".oro", oro);
        banco.set("banco." + uuid + ".plata", plata);
        plugin.guardarBancoConfig();
    }
}
