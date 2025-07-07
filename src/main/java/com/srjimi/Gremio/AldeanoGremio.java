package com.srjimi.Gremio;

import com.srjimi.Main;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class AldeanoGremio implements Listener {

    private final Main plugin;
    private final String nombreInventario = "§8Misiones disponibles";

    public AldeanoGremio(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void alInteractuarConAldeano(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof Villager villager)) return;

        String nombre = villager.getCustomName();
        if (nombre == null || !ChatColor.stripColor(nombre).equalsIgnoreCase("Aldeano de Misiones Diarias")) return;

        e.setCancelled(true); // evita el menú de intercambio
        abrirGUI(e.getPlayer());
    }

    private void abrirGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 54, nombreInventario);

        for (Mision m : plugin.getMisionDiariaManager().getMisiones()) {
            boolean enCooldown = plugin.getMisionDiariaManager().getCooldownRestante(player, m) > 0;

            ItemStack item = new ItemStack(enCooldown ? Material.RED_WOOL : Material.LIME_WOOL);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName("§e" + m.getNombre());

            List<String> lore = new ArrayList<>();
            lore.add("§7" + m.getDescripcion());
            lore.add("§7Entidad: §f" + m.getMobObjetivo().name());
            lore.add("§7Cantidad: §f" + m.getCantidadObjetivo());
            lore.add("§7Recompensas: §b" + m.getRecompensaXP() + " XP §7y §f" + m.getRecompensaPlata() + " plata");

            long tiempoRestante = plugin.getMisionDiariaManager().getCooldownRestante(player,m);
            if (tiempoRestante > 0) {
                long seg = tiempoRestante / 1000;
                long min = (seg % 3600) / 60;
                long hrs = seg / 3600;
                long s = seg % 60;
                lore.add("§cCooldown: " + hrs + "h " + min + "m " + s + "s");
            } else {
                lore.add("§aHaz clic para aceptar esta misión.");
            }

            meta.setLore(lore);

            // Guardamos el ID de la misión en la "localización" del ítem
            meta.getPersistentDataContainer().set(
                    new NamespacedKey(plugin, "mision_id"),
                    org.bukkit.persistence.PersistentDataType.STRING,
                    m.getId()
            );

            item.setItemMeta(meta);
            gui.addItem(item);
        }

        player.openInventory(gui);
    }

    @EventHandler
    public void alHacerClickEnGUI(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(nombreInventario)) {
            event.setCancelled(true);

            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;

            Player player = (Player) event.getWhoClicked();
            ItemMeta meta = event.getCurrentItem().getItemMeta();
            if (meta == null) return;

            // Recuperar el ID de la misión del ítem
            String id = meta.getPersistentDataContainer().get(
                    new NamespacedKey(plugin, "mision_id"),
                    org.bukkit.persistence.PersistentDataType.STRING
            );

            if (id != null) {
                plugin.getMisionDiariaManager().aceptarMision(player, id);
                player.closeInventory();
            }
        }
    }

    public void crearAldeanoMisiones(Location loc) {
        // Verifica si ya existe un aldeano con ese nombre en la misma ubicación
        for (Entity entidad : loc.getWorld().getNearbyEntities(loc, 1, 1, 1)) {
            if (entidad instanceof Villager) {
                Villager aldeano = (Villager) entidad;
                if (ChatColor.stripColor(aldeano.getCustomName()) != null &&
                        ChatColor.stripColor(aldeano.getCustomName()).equalsIgnoreCase("Aldeano de Misiones Diarias")) {
                    plugin.getLogger().info("Ya existe el aldeano de misiones en esta ubicación.");
                    return;
                }
            }
        }

        Villager aldeano = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
        aldeano.setCustomName("§6Aldeano de Misiones Diarias");
        aldeano.setCustomNameVisible(true);
        aldeano.setProfession(Villager.Profession.LIBRARIAN);
        aldeano.setAI(false);
        aldeano.setInvulnerable(true);
        aldeano.setCollidable(false);
        aldeano.setPersistent(true);
        aldeano.setSilent(true);
        aldeano.setRemoveWhenFarAway(false);

        // Marcar al aldeano para identificación con namespaced key
        NamespacedKey key = new NamespacedKey(plugin, "aldeano_misiones_diarias");
        aldeano.getPersistentDataContainer().set(key, PersistentDataType.STRING, "misionesD");
        aldeano.getPersistentDataContainer().set(new NamespacedKey(plugin, "aldeano_proteccion"), PersistentDataType.STRING, "true");

        plugin.getLogger().info("Aldeano de misiones creado en: " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
    }
}
