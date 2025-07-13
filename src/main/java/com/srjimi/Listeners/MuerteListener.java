package com.srjimi.Listeners;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class MuerteListener implements Listener {

    private final JavaPlugin plugin;

    public MuerteListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void alMorir(PlayerDeathEvent event) {
        Player jugador = event.getEntity();
        Location loc = jugador.getLocation();
        World mundo = loc.getWorld();

        // Limpiar drops predeterminados
        event.getDrops().clear();

        // Mostrar coordenadas al jugador
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        jugador.sendMessage("§eHas muerto en: §bX: " + x + " Y: " + y + " Z: " + z);
        jugador.sendMessage("§aTus objetos han sido guardados en un cofre.");

        // Crear cofre doble
        Location loc1 = loc.getBlock().getLocation();
        Location loc2 = loc1.clone().add(1, 0, 0);

        loc1.getBlock().setType(Material.CHEST);
        loc2.getBlock().setType(Material.CHEST);

        // Configurar como cofre doble
        BlockData data1 = loc1.getBlock().getBlockData();
        BlockData data2 = loc2.getBlock().getBlockData();

        if (data1 instanceof Chest chestData1 && data2 instanceof Chest chestData2) {
            chestData1.setType(Chest.Type.LEFT);
            chestData2.setType(Chest.Type.RIGHT);
            loc1.getBlock().setBlockData(chestData1);
            loc2.getBlock().setBlockData(chestData2);
        }

        // Obtener inventario del cofre doble
        Inventory cofre = ((org.bukkit.block.Chest) loc1.getBlock().getState()).getBlockInventory();

        // Guardar inventario principal
        for (ItemStack item : jugador.getInventory().getContents()) {
            if (item != null && item.getType() != Material.AIR) {
                cofre.addItem(item);
            }
        }

        // Guardar armadura (casco, pechera, pantalones, botas)
        for (ItemStack armor : jugador.getInventory().getArmorContents()) {
            if (armor != null && armor.getType() != Material.AIR) {
                cofre.addItem(armor);
            }
        }

        // Guardar ítem de la mano secundaria (si tiene algo)
        ItemStack offHand = jugador.getInventory().getItemInOffHand();
        if (offHand != null && offHand.getType() != Material.AIR) {
            cofre.addItem(offHand);
        }

        // Vaciar el inventario completo del jugador
        jugador.getInventory().clear();
    }
}
