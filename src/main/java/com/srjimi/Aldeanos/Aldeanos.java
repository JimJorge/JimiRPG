package com.srjimi.Aldeanos;

import com.srjimi.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;

public class Aldeanos implements Listener {
    private Main plugin;

    public Aldeanos(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void alIntentarDañarAldeano(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Villager villager)) return;

        // Verifica que sea tu aldeano específico usando PersistentDataContainer
        NamespacedKey key = new NamespacedKey(plugin, "aldeano_mercado");
        if (villager.getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void alIntentarAtacarAldeano(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Villager villager)) return;

        NamespacedKey key = new NamespacedKey(plugin, "aldeano_mercado");
        if (villager.getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
            e.setCancelled(true);
        }
    }

}
