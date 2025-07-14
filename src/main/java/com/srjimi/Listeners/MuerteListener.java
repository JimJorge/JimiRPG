package com.srjimi.Listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class MuerteListener implements Listener {

    private final Map<Location, UUID> cofresProtegidos = new HashMap<>();

    @EventHandler
    public void alMorir(PlayerDeathEvent event) {
        Player jugador = event.getEntity();
        Location loc = jugador.getLocation();

        event.getDrops().clear();

        jugador.sendMessage("§aTus objetos han sido guardados en cofres protegidos.");

        // Crear cofres separados
        Location loc1 = loc.getBlock().getLocation();
        Location loc2 = loc1.clone().add(1, 0, 0);

        loc1.getBlock().setType(Material.CHEST);
        loc2.getBlock().setType(Material.CHEST);

        cofresProtegidos.put(loc1, jugador.getUniqueId());
        cofresProtegidos.put(loc2, jugador.getUniqueId());

        Chest chest1 = (Chest) loc1.getBlock().getState();
        Chest chest2 = (Chest) loc2.getBlock().getState();

        Inventory inv1 = chest1.getInventory();
        Inventory inv2 = chest2.getInventory();

        List<ItemStack> itemsPorGuardar = new ArrayList<>();

        // Inventario completo + offhand + armadura
        Collections.addAll(itemsPorGuardar, jugador.getInventory().getContents());
        itemsPorGuardar.add(jugador.getInventory().getItemInOffHand());
        itemsPorGuardar.add(jugador.getInventory().getHelmet());
        itemsPorGuardar.add(jugador.getInventory().getChestplate());
        itemsPorGuardar.add(jugador.getInventory().getLeggings());
        itemsPorGuardar.add(jugador.getInventory().getBoots());

        for (ItemStack item : itemsPorGuardar) {
            if (item == null || item.getType() == Material.AIR) continue;

            HashMap<Integer, ItemStack> sobrante = inv1.addItem(item);
            if (!sobrante.isEmpty()) {
                for (ItemStack restante : sobrante.values()) {
                    HashMap<Integer, ItemStack> sobrante2 = inv2.addItem(restante);
                    if (!sobrante2.isEmpty()) {
                        jugador.sendMessage("§c¡Los cofres se llenaron! Algunos objetos se han perdido.");
                    }
                }
            }
        }

        jugador.getInventory().clear();

        // 📍 Mensaje clickeable con coordenadas para teletransportarse
        int x = loc1.getBlockX();
        int y = loc1.getBlockY();
        int z = loc1.getBlockZ();

        TextComponent mensaje = Component.text("§eHaz clic aquí para teletransportarte a tu cofre: ")
                .append(Component.text("[TP]", NamedTextColor.AQUA)
                        .clickEvent(ClickEvent.runCommand("/tp " + jugador.getName() + " " + x + " " + y + " " + z))
                        .hoverEvent(Component.text("§7Click para teletransportarte")));

        jugador.sendMessage(Component.text("§eHas muerto en: §bX: " + x + " Y: " + y + " Z: " + z));
        jugador.sendMessage(mensaje);
    }

    // 🔒 Prevenir saqueo
    @EventHandler
    public void protegerCofre(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (block == null || block.getType() != Material.CHEST) return;

        Location loc = block.getLocation();
        UUID dueño = cofresProtegidos.get(loc);
        if (dueño != null && !dueño.equals(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§cEste cofre pertenece a otro jugador.");
        }
    }

    // 🔒 Prevenir destrucción
    @EventHandler
    public void prevenirRompimiento(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() != Material.CHEST) return;

        Location loc = block.getLocation();
        UUID dueño = cofresProtegidos.get(loc);
        if (dueño != null && !dueño.equals(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§cNo puedes romper el cofre de otro jugador.");
        } else if (dueño != null) {
            cofresProtegidos.remove(loc); // El dueño puede romperlo y se elimina la protección
        }
    }
}
