package com.srjimi.Clases;

import com.srjimi.Main;
import com.srjimi.Clases.Tanque;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;

public class ClasesListener implements Listener {

    private final Main plugin;

    public ClasesListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack itemOff = player.getInventory().getItemInOffHand();

        // Solo procesamos clic derecho
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || item.getType() == Material.AIR) return;

        Clase clase = plugin.getClasesMain().getClase(player);

        // ⚔️ Guerrero (Click derecho con espada)
        if (clase instanceof Guerrero) {
            if (item.getType().toString().toLowerCase().contains("sword")) {
                Guerrero guerrero = (Guerrero) clase;
                guerrero.activarHabilidad(player);
            }
            return;
        }

        // 🔮 Mago (usa cualquier objeto personalizado)
        if (clase instanceof Mago) {
            Mago mago = (Mago) clase;
            mago.usarItem(player, item);
            return;
        }

        // Tanque - activar al hacer click derecho con escudo especial en mano secundaria
        if (clase instanceof Tanque) {
            Tanque tanque = (Tanque) clase;
            if (tanque.esEscudoEspecial(itemOff)) {
                tanque.usarHabilidad(player);
            }
        }
    }
}
