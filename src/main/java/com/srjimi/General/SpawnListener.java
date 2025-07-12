package com.srjimi.General;

import com.srjimi.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SpawnListener implements Listener {

    private final SpawnManager spawnManager;
    private final Main plugin;

    public SpawnListener(Main plugin,SpawnManager spawnManager) {
        this.spawnManager = spawnManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void alEntrar(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) {
            var loc = spawnManager.obtenerSpawn();
            if (loc != null) {
                event.getPlayer().teleport(loc);
                plugin.getMisionDiariaManager().aumentarProgreso(event.getPlayer());
            }
        }
    }
}
