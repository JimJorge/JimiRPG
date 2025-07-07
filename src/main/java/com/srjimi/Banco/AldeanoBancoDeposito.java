package com.srjimi.Banco;

import com.srjimi.Main;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class AldeanoBancoDeposito implements Listener {

    private Main plugin;

    public AldeanoBancoDeposito(Main plugin) {
        this.plugin = plugin;
    }

    private final String INVENTORY_TITLE = ChatColor.DARK_PURPLE + "🏦 Banco - Depositar Monedas";

    public void abrirDeposito(Player jugador) {
        Inventory inv = Bukkit.createInventory(null, 27, INVENTORY_TITLE);

        ItemStack panel = crearItem(Material.PURPLE_STAINED_GLASS_PANE, ChatColor.BLACK+""+ChatColor.MAGIC+"XXXXXXXXXXXX");
        ItemStack cerrar = crearItem(Material.RED_WOOL, ChatColor.RED + "✘ Cerrar menú");
        ItemStack confirmar = crearItem(Material.LIME_WOOL, ChatColor.GREEN + "✔ Confirmar depósito");

        // Fila 1 (superior) — decorativa
        for (int i = 0; i <= 8; i++) inv.setItem(i, panel);

// Fila 2 — área de depósito (slots 8–12 quedan libres)
        inv.setItem(9, panel);   // borde izquierdo
        inv.setItem(17, panel);  // borde derecho

// Fila 3 — botones y decoración
        inv.setItem(18, panel);
        inv.setItem(19, panel);
        inv.setItem(20, confirmar);
        inv.setItem(21, panel);
        inv.setItem(22, panel);
        inv.setItem(23, panel);
        inv.setItem(24, cerrar);
        inv.setItem(25, panel);
        inv.setItem(26, panel);


        jugador.openInventory(inv);
    }

    private ItemStack crearItem(Material material, String nombre) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(nombre);
        item.setItemMeta(meta);
        return item;
    }

    public void generarAldeano(Player jugador, Location location) {
        Villager aldeano = (Villager) jugador.getWorld().spawnEntity(location, EntityType.VILLAGER);
        aldeano.setCustomName(ChatColor.GREEN + "🪙 Banco - Depósito");
        aldeano.setCustomNameVisible(true);
        aldeano.setAI(false);
        aldeano.setInvulnerable(true);
        aldeano.setProfession(Villager.Profession.NITWIT);
        aldeano.setSilent(true);
        aldeano.setCollidable(false);

        // Marcar al aldeano para identificación con namespaced key
        NamespacedKey key = new NamespacedKey(plugin, "aldeano_banco_deposito");
        aldeano.getPersistentDataContainer().set(key, PersistentDataType.STRING, "deposito");
        aldeano.getPersistentDataContainer().set(new NamespacedKey(plugin, "aldeano_proteccion"), PersistentDataType.STRING, "true");

        // Guardar en el YML
        File file = new File(plugin.getDataFolder(), "Aldeanos.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        UUID uuid = aldeano.getUniqueId();

        config.set("aldeanos." + uuid + ".tipo", "deposito");
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

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!ChatColor.stripColor(event.getView().getTitle()).contains("Banco - Depositar Monedas")) return;

        Player jugador = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        int slot = event.getRawSlot();

        if (clicked == null || clicked.getType() == Material.AIR) return;

        // Bloquear mover paneles decorativos y botones (lana roja y verde)
        Material tipo = clicked.getType();
        if (tipo == Material.PURPLE_STAINED_GLASS_PANE
                || tipo == Material.RED_WOOL
                || tipo == Material.LIME_WOOL) {
            event.setCancelled(true);
        }

        // Zona libre para mover monedas u otros ítems: slots 8-12 (ajusta según tu GUI)
        if (slot >= 10 && slot <= 16) {
            event.setCancelled(false); // permitir mover libremente monedas y demás ítems en esta zona
            return;
        }

        // Si clickea el botón "Cerrar" (lana roja)
        if (tipo == Material.RED_WOOL && clicked.getItemMeta() != null
                && ChatColor.stripColor(clicked.getItemMeta().getDisplayName()).contains("Cerrar")) {
            event.setCancelled(true);
            jugador.closeInventory();
            return;
        }

        // Si clickea el botón "Confirmar" (lana verde)
        if (tipo == Material.LIME_WOOL && clicked.getItemMeta() != null
                && ChatColor.stripColor(clicked.getItemMeta().getDisplayName()).contains("Confirmar")) {
            event.setCancelled(true);

            Inventory inv = event.getInventory();
            int oro = 0, plata = 0;

            for (int i = 10; i <= 16; i++) {
                ItemStack item = inv.getItem(i);
                if (item == null || item.getType() == Material.AIR) continue;

                if (BancoItemUtil.esOroValido(item)) oro += item.getAmount();
                else if (BancoItemUtil.esPlataValida(item)) plata += item.getAmount();
                else {
                    jugador.sendMessage(ChatColor.RED + "Solo puedes depositar monedas oficiales.");
                    return;
                }
            }

            if (oro == 0 && plata == 0) {
                jugador.sendMessage(ChatColor.RED + "Debes colocar monedas para depositar.");
                return;
            }

            // Limpiar la zona de depósito
            for (int i = 10; i <= 16; i++) {
                inv.setItem(i, null);
            }

            BancoManager.addOro(jugador, oro);
            BancoManager.addPlata(jugador, plata);

            jugador.sendMessage(ChatColor.GREEN + "Depositaste " + oro + " oro y " + plata + " plata.");
            jugador.closeInventory();
        }
    }


    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!ChatColor.stripColor(event.getView().getTitle()).contains("Banco - Depositar Monedas")) return;

        for (int slot : event.getRawSlots()) {
            if (slot >= 10 && slot <= 16) {
                ItemStack item = event.getOldCursor();
                if (!BancoItemUtil.esOroValido(item) && !BancoItemUtil.esPlataValida(item)) {
                    ((Player) event.getWhoClicked()).sendMessage(ChatColor.RED + "Solo monedas oficiales.");
                    event.setCancelled(true);
                    return;
                }
            } else {
                event.setCancelled(true); // Bloquear todos los demás slots
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!event.getView().getTitle().equals(INVENTORY_TITLE)) return;

        Player jugador = (Player) event.getPlayer();
        Inventory inv = event.getInventory();

        // Devolver monedas si no confirmó
        for (int slot = 10; slot <= 16; slot++) {
            ItemStack item = inv.getItem(slot);
            if (item != null && item.getType() != Material.AIR) {
                jugador.getInventory().addItem(item);
            }
        }
    }
    private void procesarDeposito(Player jugador, Inventory inv) {
        int oro = 0, plata = 0;

        for (int i = 8; i <= 12; i++) {
            ItemStack item = inv.getItem(i);
            if (item == null) continue;

            if (BancoItemUtil.esOroValido(item)) oro += item.getAmount();
            else if (BancoItemUtil.esPlataValida(item)) plata += item.getAmount();
        }

        // Limpiar y guardar
        for (int i = 10; i <= 16; i++) inv.setItem(i, null);

        BancoManager.addOro(jugador, oro);
        BancoManager.addPlata(jugador, plata);

        jugador.sendMessage(ChatColor.GREEN + "Depositaste " + oro + " oro y " + plata + " plata.");
        jugador.closeInventory();
    }

}
