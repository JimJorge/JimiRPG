package com.srjimi.Aldeanos;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

    public class ProteccionAldeanos implements Listener {

            private final JavaPlugin plugin;
            private final NamespacedKey key;

            public ProteccionAldeanos(JavaPlugin plugin) {
                this.plugin = plugin;
                this.key = new NamespacedKey(plugin, "aldeano_proteccion"); // Marca de identificación
                Bukkit.getPluginManager().registerEvents(this, plugin);
            }

            private boolean esAldeanoProtegido(Entity entity) {
                if (!(entity instanceof Villager)) return false;
                PersistentDataContainer data = entity.getPersistentDataContainer();
                return data.has(key, PersistentDataType.STRING);
            }

            @EventHandler
            public void onEntityDamage(EntityDamageEvent event) {
                if (esAldeanoProtegido(event.getEntity())) {
                    event.setCancelled(true);
                }
            }

            @EventHandler
            public void onEntityDamageByPlayer(EntityDamageByEntityEvent event) {
                if (esAldeanoProtegido(event.getEntity())) {
                    event.setCancelled(true);
                }
            }

            @EventHandler
            public void onEntityCombust(EntityCombustEvent event) {
                if (esAldeanoProtegido(event.getEntity())) {
                    event.setCancelled(true);
                }
            }

            @EventHandler
            public void onEntityTarget(EntityTargetEvent event) {
                if (esAldeanoProtegido(event.getEntity())) {
                    event.setCancelled(true);
                }
            }

            @EventHandler
            public void onInteract(PlayerInteractEntityEvent event) {
                if (esAldeanoProtegido(event.getRightClicked())) {
                    event.setCancelled(true); // Bloqueamos interacción normal
                }
            }
        }

