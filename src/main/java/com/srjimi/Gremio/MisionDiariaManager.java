package com.srjimi.Gremio;

import com.srjimi.Banco.BancoManager;
import com.srjimi.Main;
import com.srjimi.Nivel.NivelManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.*;

public class MisionDiariaManager {

        private final Map<String, Mision> misiones = new HashMap<>();
        private final Map<UUID, Mision> activas = new HashMap<>();
        private final Map<UUID, Integer> progreso = new HashMap<>();
        private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();

        private File misionFile;
        private FileConfiguration misionConfig;
        private Main plugin;
        private NivelManager nivelManager;

        public void inicializar(Main plugin,NivelManager nivelManager) {
            this.plugin = plugin;
            this.nivelManager = nivelManager;
            misionFile = new File(plugin.getDataFolder(), "misiones.yml");
            if (!misionFile.exists()) {
                plugin.saveResource("misiones.yml", false);
            }

            misionConfig = YamlConfiguration.loadConfiguration(misionFile);
            cargarMisionesDesdeArchivo();
        }

        public void cargarMisionesDesdeArchivo() {
            misiones.clear();
            var section = misionConfig.getConfigurationSection("misiones");
            if (section == null) {
                Bukkit.getLogger().warning("[RPG] No se encontró la sección 'misiones'");
                return;
            }

            for (String id : section.getKeys(false)) {
                String base = "misiones." + id + ".";
                String nombre = misionConfig.getString(base + "nombre");
                String descripcion = misionConfig.getString(base + "descripcion");
                String tipoEntidad = misionConfig.getString(base + "entidad");
                int cantidad = misionConfig.getInt(base + "objetivo");
                int xp = misionConfig.getInt(base + "recompensa_xp");
                int plata = misionConfig.getInt(base + "recompensa_plata");
                int cooldown = misionConfig.getInt(base + "cooldown", 86400); // por defecto 24 hrs

                if (tipoEntidad == null) continue;

                EntityType tipo = EntityType.valueOf(tipoEntidad.toUpperCase());
                Mision m = new Mision(id, nombre, descripcion, tipo, cantidad, xp, plata, cooldown);
                misiones.put(id, m);
            }

            Bukkit.getLogger().info("[RPG] Misiones cargadas: " + misiones.size());
        }

        public void aceptarMision(Player player, String id) {
            UUID uuid = player.getUniqueId();

            if (activas.containsKey(uuid)) {
                player.sendMessage("§cYa tienes una misión activa.");
                return;
            }

            Mision m = misiones.get(id);
            if (m == null) {
                player.sendMessage("§cMisión no encontrada.");
                return;
            }

            // Verificar cooldown
            long now = System.currentTimeMillis();
            Map<String, Long> jugadorCooldowns = cooldowns.getOrDefault(uuid, new HashMap<>());

            if (jugadorCooldowns.containsKey(id)) {
                long cooldownTime = jugadorCooldowns.get(id);
                if (now < cooldownTime) {
                    long restante = (cooldownTime - now) / 1000;
                    long horas = restante / 3600;
                    long minutos = (restante % 3600) / 60;
                    long segundos = restante % 60;
                    player.sendMessage("§cDebes esperar §e" + horas + "h " + minutos + "m " + segundos + "s §cpara volver a aceptar esta misión.");
                    return;
                }
            }

            activas.put(uuid, m);
            progreso.put(uuid, 0);

            player.sendMessage("§aMisión aceptada: §e" + m.getNombre());
            player.sendMessage("§7" + m.getDescripcion());
            player.sendMessage("§aRecompensas: §6" + m.getRecompensaPlata() + " plata, §b" + m.getRecompensaXP() + " XP");

            iniciarBarraProgreso(player);
        }

        public void cancelarMision(Player player) {
            UUID uuid = player.getUniqueId();
            if (!activas.containsKey(uuid)) {
                player.sendMessage("§cNo tienes ninguna misión activa para cancelar.");
                return;
            }

            Mision m = activas.remove(uuid);
            progreso.remove(uuid);

            player.sendMessage("§eHas cancelado la misión: §c" + m.getNombre());
        }

        public void aumentarProgreso(Player player) {
            UUID uuid = player.getUniqueId();
            if (!activas.containsKey(uuid)) return;

            Mision m = activas.get(uuid);
            int actual = progreso.getOrDefault(uuid, 0) + 1;

            if (actual >= m.getCantidadObjetivo()) {
                completarMision(player, m);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
            } else {
                progreso.put(uuid, actual);
            }
        }

        private void completarMision(Player player, Mision mision) {
            UUID uuid = player.getUniqueId();

            activas.remove(uuid);
            progreso.remove(uuid);

            cooldowns.computeIfAbsent(uuid, k -> new HashMap<>())
                    .put(mision.getId(), System.currentTimeMillis() + mision.getCooldown() * 1000L);

            player.sendMessage("§6§l¡Has completado la misión §e" + mision.getNombre() + "§6§l!");
            player.sendMessage("§a+ " + mision.getRecompensaXP() + " XP, + " + mision.getRecompensaPlata() + " plata");
            BancoManager.setPlata(player, mision.getRecompensaPlata());
            nivelManager.addXP(player, mision.getRecompensaXP());
            plugin.getScoreboardManager().CreaActualizaScoreboard(player);
        }

        public Mision getMisionActiva(Player player) {
            return activas.get(player.getUniqueId());
        }

        public int getProgreso(Player player) {
            return progreso.getOrDefault(player.getUniqueId(), 0);
        }

        public long getCooldownRestante(Player player, Mision m) {
            UUID uuid = player.getUniqueId();
            if (!cooldowns.containsKey(uuid)) return 0L;

            Map<String, Long> mapa = cooldowns.get(uuid);
            if (!mapa.containsKey(m.getId())) return 0L;

            long restante = mapa.get(m.getId()) - System.currentTimeMillis();
            return Math.max(restante, 0);
        }

        public String cooldownEstado(Player player, String id) {
            Mision m = misiones.get(id);
            if (m == null) return "§cMisión no encontrada.";
            long restante = getCooldownRestante(player, m);
            if (restante <= 0) return "§a¡Disponible!";

            long segundos = restante / 1000;
            long h = segundos / 3600;
            long m_ = (segundos % 3600) / 60;
            long s = segundos % 60;
            return "§cEn espera: §e" + h + "h " + m_ + "m " + s + "s";
        }

        public void iniciarBarraProgreso(Player player) {
            UUID uuid = player.getUniqueId();

            new BukkitRunnable() {
                @Override
                public void run() {
                    Mision m = activas.get(uuid);
                    if (m == null) {
                        cancel();
                        return;
                    }

                    int actual = progreso.getOrDefault(uuid, 0);
                    int total = m.getCantidadObjetivo();
                    int barLength = 10;
                    int filled = (int) ((double) actual / total * barLength);

                    String color;
                    double porcentaje = (double) actual / total;
                    if (porcentaje >= 0.8) color = "§e";
                    else if (porcentaje < 0.3) color = "§c";
                    else color = "§a";

                    StringBuilder bar = new StringBuilder(color);
                    for (int i = 0; i < barLength; i++) {
                        bar.append(i < filled ? "■" : "§7■");
                    }

                    String mensaje = "§d" + m.getNombre() + ": " + bar + " §e" + actual + "/" + total;
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(mensaje));
                }
            }.runTaskTimer(plugin, 0L, 20L);
        }

        public Collection<Mision> getMisiones() {
            return misiones.values();
        }

        public Mision getMisionPorID(String id) {
            return misiones.get(id);
        }
    }
