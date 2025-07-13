package com.srjimi.Clases;

import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import com.srjimi.Main;
import org.bukkit.entity.Player;

public class MagoListener implements Listener {

    private final Mago mago;

    public MagoListener(Mago mago, Main plugin) {
        this.mago = mago;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        ItemStack dropped = event.getItemDrop().getItemStack();

        if (mago.esItemMago(dropped)) {
            event.setCancelled(true);
            player.sendMessage("§c¡No puedes tirar objetos mágicos!");
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();

        if (current != null && mago.esItemMago(current)) {
            event.setCancelled(true);
            player.sendMessage("§c¡No puedes mover objetos mágicos!");
        }
    }
}
