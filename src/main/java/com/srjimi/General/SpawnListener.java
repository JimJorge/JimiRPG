package com.srjimi.General;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SpawnListener implements Listener {

    private final SpawnManager spawnManager;

    public SpawnListener(SpawnManager spawnManager) {
        this.spawnManager = spawnManager;
    }

    @EventHandler
    public void alEntrar(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) {
            var loc = spawnManager.obtenerSpawn();
            if (loc != null) {
                event.getPlayer().teleport(loc);
            }
        }
    }
}
