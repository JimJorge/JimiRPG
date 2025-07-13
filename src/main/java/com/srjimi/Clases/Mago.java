package com.srjimi.Clases;


import org.bukkit.*;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.*;
import org.bukkit.scheduler.BukkitRunnable;

import com.srjimi.Main;

import java.util.*;

public class Mago extends Clase {

    private final Map<UUID, Long> cooldownPolvo = new HashMap<>();
    private final Map<UUID, Long> cooldownLagrima = new HashMap<>();
    private final Map<UUID, Long> cooldownBaston = new HashMap<>();
    private final Map<UUID, Integer> bolasLanzadas = new HashMap<>();

    private final ItemStack polvoBlaze;
    private final ItemStack lagrimaGhast;
    private final ItemStack baston;
    private Player player;

    public Mago(Main plugin) {
        super(plugin,"Mago");
        polvoBlaze = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta metaPolvo = polvoBlaze.getItemMeta();
        metaPolvo.setDisplayName("§6§l☠Poder de Fuerza☠");
        polvoBlaze.setItemMeta(metaPolvo);

        lagrimaGhast = new ItemStack(Material.GHAST_TEAR);
        ItemMeta metaLagrima = lagrimaGhast.getItemMeta();
        metaLagrima.setDisplayName("§d§l❤Lágrima Curativa❤");
        lagrimaGhast.setItemMeta(metaLagrima);

        baston = new ItemStack(Material.BLAZE_ROD);
        ItemMeta metaBaston = baston.getItemMeta();
        metaBaston.setDisplayName("§c§l☢Bastón de Fuego☢");
        baston.setItemMeta(metaBaston);
    }

    @Override
    public void activarHabilidad(Player player) {
        // Dar los objetos
        player.getInventory().addItem(polvoBlaze);
        player.getInventory().addItem(lagrimaGhast);
        player.getInventory().addItem(baston);

        // Prevenir que los saque del inventario
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) cancel();
                for (ItemStack item : player.getInventory()) {
                    if (item != null && (item.equals(polvoBlaze) || item.equals(lagrimaGhast) || item.equals(baston))) {
                        item.setAmount(1);
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 40);
    }

    @Override
    public void quitarHabilidades(Player player) {
        if (player == null) return;
        player.getInventory().remove(polvoBlaze);
        player.getInventory().remove(lagrimaGhast);
        player.getInventory().remove(baston);
    }

    public void usarItem(Player player, ItemStack item) {
        UUID uuid = player.getUniqueId();

        if (item.isSimilar(polvoBlaze)) {
            long lastUse = cooldownPolvo.getOrDefault(uuid, 0L);
            long elapsed = System.currentTimeMillis() - lastUse;
            if (elapsed < 5 * 60 * 1000) {
                long restante = 5 * 60 * 1000 - elapsed;
                long minutos = (restante / 1000) / 60;
                long segundos = (restante / 1000) % 60;
                player.spigot().sendMessage(
                        net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                        new net.md_5.bungee.api.chat.TextComponent("§e§lHabilidad Cooldown: " + minutos + "m " + segundos + "s§r"));
                return;
            }
            cooldownPolvo.put(uuid, System.currentTimeMillis());
            for (Player nearby : player.getWorld().getPlayers()) {
                if (nearby.getLocation().distance(player.getLocation()) <= 30) {
                    nearby.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 20 * 180, 1));
                }
            }
            player.spigot().sendMessage(
                    net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                    new net.md_5.bungee.api.chat.TextComponent("§6¡Fuerza II activada durante 3 minutos!"));
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
        } else if (item.isSimilar(lagrimaGhast)) {
            long lastUse = cooldownLagrima.getOrDefault(uuid, 0L);
            long elapsed = System.currentTimeMillis() - lastUse;
            if (elapsed < 4 * 60 * 1000) {
                long restante = 4 * 60 * 1000 - elapsed;
                long minutos = (restante / 1000) / 60;
                long segundos = (restante / 1000) % 60;
                player.spigot().sendMessage(
                        net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                        new net.md_5.bungee.api.chat.TextComponent("§e§lHabilidad Cooldown: " + minutos + "m " + segundos + "s§r"));
                return;
            }
            cooldownLagrima.put(uuid, System.currentTimeMillis());
            for (Player nearby : player.getWorld().getPlayers()) {
                if (nearby.getLocation().distance(player.getLocation()) <= 30) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 120, 1));
                }
            }
            player.spigot().sendMessage(
                    net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                    new net.md_5.bungee.api.chat.TextComponent("§d¡Regeneración II activada durante 2 minutos!"));
            player.playSound(player.getLocation(), Sound.ITEM_TOTEM_USE	, 1.0f, 1.0f);
        } else if (item.isSimilar(baston)) {
            long lastUse = cooldownBaston.getOrDefault(uuid, 0L);
            long elapsed = System.currentTimeMillis() - lastUse;
            if (elapsed < 3 * 60 * 1000) {
                long restante = 3 * 60 * 1000 - elapsed;
                long minutos = (restante / 1000) / 60;
                long segundos = (restante / 1000) % 60;
                player.spigot().sendMessage(
                        net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                        new net.md_5.bungee.api.chat.TextComponent("§e§lHabilidad Cooldown: " + minutos + "m " + segundos + "s§r"));
                return;
            }

            int lanzadas = bolasLanzadas.getOrDefault(uuid, 0);
            if (lanzadas >= 3) {
                cooldownBaston.put(uuid, System.currentTimeMillis());
                bolasLanzadas.put(uuid, 0);
                player.spigot().sendMessage(
                        net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                        new net.md_5.bungee.api.chat.TextComponent("§cHas lanzado 3 bolas. Espera el cooldown."));
                return;
            }

            Fireball fireball = player.launchProjectile(Fireball.class);
            fireball.setIsIncendiary(false);
            fireball.setYield(2);
            bolasLanzadas.put(uuid, lanzadas + 1);
            player.spigot().sendMessage(
                    net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                    new net.md_5.bungee.api.chat.TextComponent("§c¡Bola de fuego lanzada! [" + (lanzadas + 1) + "/3]"));
            player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 1.0f, 1.0f);
        }
    }
    public boolean esItemMago(ItemStack item) {
        return item != null && (item.isSimilar(polvoBlaze) || item.isSimilar(lagrimaGhast) || item.isSimilar(baston));
    }

}