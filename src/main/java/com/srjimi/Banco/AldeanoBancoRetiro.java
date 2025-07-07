package com.srjimi.Banco;

import com.srjimi.Main;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.srjimi.Banco.BancoItemUtil;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class AldeanoBancoRetiro implements Listener {

    private final Main plugin;  // Si usas un plugin principal

    public AldeanoBancoRetiro(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    // Abrir GUI de retiro
    public void abrirGUI(Player jugador) {
        Inventory gui = Bukkit.createInventory(null, 27, ChatColor.GOLD + "Retirar monedas");

        // Botones para cantidades de oro usando BancoItemUtil
        gui.setItem(10, crearBoton(BancoItemUtil.getOroItem(1), ChatColor.GOLD + "Retirar 1 Oro", 1));
        gui.setItem(11, crearBoton(BancoItemUtil.getOroItem(5), ChatColor.GOLD + "Retirar 5 Oro", 5));
        gui.setItem(12, crearBoton(BancoItemUtil.getOroItem(10), ChatColor.GOLD + "Retirar 10 Oro", 10));
        gui.setItem(13, crearBoton(BancoItemUtil.getOroItem(20), ChatColor.GOLD + "Retirar 20 Oro", 20));
        gui.setItem(14, crearBoton(BancoItemUtil.getOroItem(50), ChatColor.GOLD + "Retirar 50 Oro", 50));

// Botones para cantidades de plata usando BancoItemUtil
        gui.setItem(19, crearBoton(BancoItemUtil.getPlataItem(1), ChatColor.GRAY + "Retirar 1 Plata", 1));
        gui.setItem(20, crearBoton(BancoItemUtil.getPlataItem(5), ChatColor.GRAY + "Retirar 5 Plata", 5));
        gui.setItem(21, crearBoton(BancoItemUtil.getPlataItem(10), ChatColor.GRAY + "Retirar 10 Plata", 10));
        gui.setItem(22, crearBoton(BancoItemUtil.getPlataItem(20), ChatColor.GRAY + "Retirar 20 Plata", 20));
        gui.setItem(23, crearBoton(BancoItemUtil.getPlataItem(50), ChatColor.GRAY + "Retirar 50 Plata", 50));


        jugador.openInventory(gui);
    }

    public void generarAldeano(Player jugador, Location location) {
        Villager aldeano = (Villager) jugador.getWorld().spawnEntity(location, EntityType.VILLAGER);
        aldeano.setCustomName(ChatColor.YELLOW + "🏧 Banco - Retiro");
        aldeano.setCustomNameVisible(true);
        aldeano.setAI(false);
        aldeano.setInvulnerable(true);
        aldeano.setProfession(Villager.Profession.NITWIT);
        aldeano.setSilent(true);
        aldeano.setCollidable(false);

        // Marcar al aldeano para identificación con namespaced key
        NamespacedKey key = new NamespacedKey(plugin, "aldeano_banco_retiro");
        aldeano.getPersistentDataContainer().set(key, PersistentDataType.STRING, "retiro");
        aldeano.getPersistentDataContainer().set(new NamespacedKey(plugin, "aldeano_proteccion"), PersistentDataType.STRING, "true");

        // Guardar en el YML
        File file = new File(plugin.getDataFolder(), "Aldeanos.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        UUID uuid = aldeano.getUniqueId();

        config.set("aldeanos." + uuid + ".tipo", "retiro");
        config.set("aldeanos." + uuid + ".world", location.getWorld().getName());
        config.set("aldeanos." + uuid + ".x", location.getX());
        config.set("aldeanos." + uuid + ".y", location.getY());
        config.set("aldeanos." + uuid + ".z", location.getZ());

        try {
            config.save(file);
            jugador.sendMessage(ChatColor.GREEN + "Aldeano de depósito creado y guardado.");
        } catch (IOException e) {
            jugador.sendMessage(ChatColor.RED + "Error al guardar el aldeano.");
            e.printStackTrace();
        }
    }

    // Crear un ítem botón con cantidad guardada en lore
    private ItemStack crearBoton(ItemStack baseItem, String nombre, int cantidad) {
        ItemStack item = baseItem.clone();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(nombre);
        meta.setLore(java.util.Collections.singletonList("Cantidad:" + cantidad));
        item.setItemMeta(meta);
        return item;
    }


    @EventHandler
    public void onInventarioClick(InventoryClickEvent event) {
        if (event.getView().getTitle() == null) return;
        if (!event.getView().getTitle().equals(ChatColor.GOLD + "Retirar monedas")) return;

        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player jugador)) return;

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || clicked.getType() == Material.AIR) return;

        // Cerrar GUI
        if (clicked.getType() == Material.RED_WOOL) {
            jugador.closeInventory();
            return;
        }

        ItemMeta meta = clicked.getItemMeta();
        if (meta == null || meta.getLore() == null || meta.getLore().isEmpty()) return;

        String lore = meta.getLore().get(0);
        if (!lore.startsWith("Cantidad:")) return;

        int cantidad;
        try {
            cantidad = Integer.parseInt(lore.replace("Cantidad:", ""));
        } catch (NumberFormatException e) {
            jugador.sendMessage(ChatColor.RED + "Error al leer la cantidad.");
            return;
        }

        // Validar si es oro o plata y si tiene saldo suficiente
        if (clicked.getType() == Material.GOLD_INGOT) {
            if (BancoManager.getOro(jugador) < cantidad) {
                jugador.sendMessage(ChatColor.RED + "No tienes suficiente oro.");
                return;
            }
            BancoManager.removeOro(jugador, cantidad);
            jugador.getInventory().addItem(BancoItemUtil.getOroItem(cantidad));
            jugador.sendMessage(ChatColor.GREEN + "Has retirado " + cantidad + " lingotes de oro.");
        } else if (clicked.getType() == Material.IRON_NUGGET) {
            if (BancoManager.getPlata(jugador) < cantidad) {
                jugador.sendMessage(ChatColor.RED + "No tienes suficiente plata.");
                return;
            }
            BancoManager.removePlata(jugador, cantidad);
            jugador.getInventory().addItem(BancoItemUtil.getPlataItem(cantidad));
            jugador.sendMessage(ChatColor.GREEN + "Has retirado " + cantidad + " pepitas de plata.");
        } else {
            jugador.sendMessage(ChatColor.RED + "Elemento inválido.");
        }
    }
}
