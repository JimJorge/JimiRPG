package com.srjimi.Clases;

import com.srjimi.Main;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class Tanque extends Clase {

    private final ItemStack escudoEspecial;
    private final Map<UUID, Long> cooldowns = new HashMap<>();

    public Tanque(Main plugin) {
        super(plugin, "Tanque");

        escudoEspecial = new ItemStack(Material.SHIELD);
        ItemMeta meta = escudoEspecial.getItemMeta();
        meta.setDisplayName("§b§lEscudo del Guardián");
        escudoEspecial.setItemMeta(meta);
    }

    @Override
    public void activarHabilidad(Player player) {
        // Dar el escudo en la mano secundaria
        player.getInventory().setItemInOffHand(escudoEspecial);
        player.updateInventory();
    }

    @Override
    public void quitarHabilidades(Player player) {
        if (player == null) return;
        ItemStack[] inventario = player.getInventory().getContents();

        for (int i = 0; i < inventario.length; i++) {
            ItemStack item = inventario[i];
            if (item != null && item.isSimilar(escudoEspecial)) {
                player.getInventory().setItem(i, null);
            }
        }
        if (player.getInventory().getItemInOffHand() != null &&
                player.getInventory().getItemInOffHand().isSimilar(escudoEspecial)) {
            player.getInventory().setItemInOffHand(null);
        }

        player.updateInventory();
    }


    public boolean esEscudoEspecial(ItemStack item) {
        return item != null && item.isSimilar(escudoEspecial);
    }

    public void usarHabilidad(Player player) {
        UUID uuid = player.getUniqueId();
        long ahora = System.currentTimeMillis();
        long cooldown = 5 * 60 * 1000; // 5 minutos

        if (cooldowns.containsKey(uuid)) {
            long restante = cooldown - (ahora - cooldowns.get(uuid));
            if (restante > 0) {
                long minutos = (restante / 1000) / 60;
                long segundos = (restante / 1000) % 60;
                player.spigot().sendMessage(
                        net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                        new net.md_5.bungee.api.chat.TextComponent("§e§lCooldown: " + minutos + "m " + segundos + "s§r"));
                return;
            }
        }

        cooldowns.put(uuid, ahora);

        for (Player nearby : player.getWorld().getPlayers()) {
            if (nearby.getLocation().distance(player.getLocation()) <= 30) {
                nearby.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 20 * 120, 4)); // Resistencia V
                nearby.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 120, 3)); // Absorción IV
            }
        }

        player.spigot().sendMessage(
                net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                new net.md_5.bungee.api.chat.TextComponent("§b¡Habilidad de Tanque activada! Resistencia y Absorción aplicadas."));
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 1.0f);
    }

    public ItemStack getEscudo() {
        return escudoEspecial;  // corregido: devuelve el escudo definido
    }
}
