package com.srjimi.Clases;

import com.srjimi.Main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class Guerrero extends Clase {

    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private static final long COOLDOWN_MILLIS = 8 * 60 * 1000; // 8 minutos

    public Guerrero(Main plugin) {
        super(plugin, "Guerrero");
    }

    @Override
    public void quitarHabilidades(Player player) {
        player.removePotionEffect(PotionEffectType.STRENGTH);
        player.removePotionEffect(PotionEffectType.ABSORPTION);
    }

    public void activarHabilidad(Player player) {
        UUID uuid = player.getUniqueId();
        long ahora = System.currentTimeMillis();

        if (cooldowns.containsKey(uuid)) {
            long ultimaVez = cooldowns.get(uuid);
            long restante = COOLDOWN_MILLIS - (ahora - ultimaVez);

            if (restante > 0) {
                long minutos = (restante / 1000) / 60;
                long segundos = (restante / 1000) % 60;

                player.spigot().sendMessage(
                        net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                        new net.md_5.bungee.api.chat.TextComponent("§e§lHabilidad Cooldown: " + minutos + "m " + segundos + "s§r"));
                return;
            }
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 20 * 60, 3)); // Fuerza IV
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 180, 3));     // Absorción IV

        player.sendMessage("§6¡Activaste tu habilidad de Guerrero! Fuerza IV por 1 minuto y Absorción IV por 3 minutos.");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        cooldowns.put(uuid, ahora);
    }
}
