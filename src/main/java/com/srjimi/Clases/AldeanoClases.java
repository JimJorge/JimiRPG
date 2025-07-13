package com.srjimi.Clases;

import com.srjimi.Main;
import com.srjimi.Clases.Clase;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class AldeanoClases implements Listener {

    private final Main plugin;
    private final String nombreInventario = "§8Selecciona tu clase";

    public AldeanoClases(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void alInteractuarConAldeano(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof Villager villager)) return;

        String nombre = villager.getCustomName();
        if (nombre == null || !ChatColor.stripColor(nombre).equalsIgnoreCase("Aldeano de Clases")) return;

        e.setCancelled(true);
        abrirGUI(e.getPlayer());
    }

    private void abrirGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, nombreInventario);

        // Guerrero
        ItemStack guerrero = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta metaGuerrero = guerrero.getItemMeta();
        metaGuerrero.setDisplayName("§6Guerrero");
        metaGuerrero.setLore(Arrays.asList(
                "§7Clase ofensiva cuerpo a cuerpo.",
                "§eHaz clic para elegir esta clase."
        ));
        metaGuerrero.getPersistentDataContainer().set(
                new NamespacedKey(plugin, "clase_id"),
                PersistentDataType.STRING,
                "guerrero"
        );
        guerrero.setItemMeta(metaGuerrero);
        gui.setItem(11, guerrero); // Posición izquierda

        // Tanque
        ItemStack tanque = new ItemStack(Material.SHIELD);
        ItemMeta metaTanque = tanque.getItemMeta();
        metaTanque.setDisplayName("§6Tanque");
        metaTanque.setLore(Arrays.asList(
                "§7Clase resistente, ideal para recibir daño.",
                "§eHaz clic para elegir esta clase."
        ));
        metaTanque.getPersistentDataContainer().set(
                new NamespacedKey(plugin, "clase_id"),
                PersistentDataType.STRING,
                "tanque"
        );
        tanque.setItemMeta(metaTanque);
        gui.setItem(13, tanque); // Posición central

        // Mago
        ItemStack mago = new ItemStack(Material.NETHER_STAR);
        ItemMeta metaMago = mago.getItemMeta();
        metaMago.setDisplayName("§6Mago");
        metaMago.setLore(Arrays.asList(
                "§7Clase mágica con hechizos a distancia.",
                "§eHaz clic para elegir esta clase."
        ));
        metaMago.getPersistentDataContainer().set(
                new NamespacedKey(plugin, "clase_id"),
                PersistentDataType.STRING,
                "mago"
        );
        mago.setItemMeta(metaMago);
        gui.setItem(15, mago); // Posición derecha

        player.openInventory(gui);
    }

    @EventHandler
    public void alHacerClickEnGUI(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(nombreInventario)) return;

        event.setCancelled(true);

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        ItemMeta meta = event.getCurrentItem().getItemMeta();
        if (meta == null) return;

        String claseId = meta.getPersistentDataContainer().get(
                new NamespacedKey(plugin, "clase_id"),
                PersistentDataType.STRING
        );

        if (claseId == null) return;

        Clase claseAnterior = plugin.getClasesMain().getClase(player);
        boolean exito = plugin.getClasesMain().asignarClase(player, claseId);

        if (exito) {
            // Quitar habilidades de la clase anterior si existía
            if (claseAnterior != null) claseAnterior.quitarHabilidades(player);

            player.sendMessage("§a¡Ahora eres un §6" + claseId + "§a!");

            // Activar habilidad si no es guerrero (como en tu lógica original)
            Clase claseNueva = plugin.getClasesMain().getClase(player);
            if (claseNueva != null && !claseNueva.getNombre().equalsIgnoreCase("guerrero")) {
                claseNueva.activarHabilidad(player);
            }

            // Actualizar scoreboard si aplica
            plugin.getScoreboardManager().CreaActualizaScoreboard(player);
        } else {
            player.sendMessage("§cClase desconocida.");
        }

        player.closeInventory();
    }


    public void crearAldeanoClases(Location loc) {
        for (Entity entidad : loc.getWorld().getNearbyEntities(loc, 1, 1, 1)) {
            if (entidad instanceof Villager) {
                Villager aldeano = (Villager) entidad;
                if (ChatColor.stripColor(aldeano.getCustomName()) != null &&
                        ChatColor.stripColor(aldeano.getCustomName()).equalsIgnoreCase("Aldeano de Clases")) {
                    plugin.getLogger().info("Ya existe el aldeano de clases en esta ubicación.");
                    return;
                }
            }
        }

        Villager aldeano = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
        aldeano.setCustomName("§6Aldeano de Clases");
        aldeano.setCustomNameVisible(true);
        aldeano.setProfession(Villager.Profession.LIBRARIAN);
        aldeano.setAI(false);
        aldeano.setInvulnerable(true);
        aldeano.setCollidable(false);
        aldeano.setPersistent(true);
        aldeano.setSilent(true);
        aldeano.setRemoveWhenFarAway(false);

        NamespacedKey key = new NamespacedKey(plugin, "aldeano_clases");
        aldeano.getPersistentDataContainer().set(key, PersistentDataType.STRING, "clases");

        plugin.getLogger().info("Aldeano de clases creado en: " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
    }
}
