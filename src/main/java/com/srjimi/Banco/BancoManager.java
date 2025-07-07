package com.srjimi.Banco;

import com.srjimi.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;



public class BancoManager {

    private static final HashMap<UUID, Integer> oro = new HashMap<>();
    private static final HashMap<UUID, Integer> plata = new HashMap<>();
    private static Main plugin;

    public static void setPlugin(Main main) {
        plugin = main;
    }

    public static void setOro(Player jugador, int cantidad) {
        if (plugin == null) return;
        FileConfiguration banco = plugin.getBancoConfig();
        banco.set("banco." + jugador.getUniqueId() + ".oro", cantidad);
        plugin.guardarBancoConfig();
    }

    public static void setPlata(Player jugador, int cantidad) {
        if (plugin == null) return;
        FileConfiguration banco = plugin.getBancoConfig();
        banco.set("banco." + jugador.getUniqueId() + ".plata", cantidad);
        plugin.guardarBancoConfig();
    }


    public static int getOro(Player jugador) {
        if (plugin == null) return 0;
        FileConfiguration banco = plugin.getBancoConfig();
        return banco.getInt("banco." + jugador.getUniqueId() + ".oro", 0);
    }

    public static int getPlata(Player jugador) {
        if (plugin == null) return 0;
        FileConfiguration banco = plugin.getBancoConfig();
        return banco.getInt("banco." + jugador.getUniqueId() + ".plata", 0);
    }

    public static void addPlata(Player jugador, int cantidad) {
        setPlata(jugador, getPlata(jugador) + cantidad);
        plugin.getScoreboardManager().CreaActualizaScoreboard(jugador);
    }

    public static void removePlata(Player jugador, int cantidad) {
        setPlata(jugador, getPlata(jugador) - cantidad);
        plugin.getScoreboardManager().CreaActualizaScoreboard(jugador);
    }

    public static void addOro(Player jugador, int cantidad) {
        setOro(jugador, getOro(jugador) + cantidad);
        plugin.getScoreboardManager().CreaActualizaScoreboard(jugador);
    }

    public static void removeOro(Player jugador, int cantidad) {
        setOro(jugador, Math.max(0, getOro(jugador) - cantidad));
        plugin.getScoreboardManager().CreaActualizaScoreboard(jugador);
    }


    /**
     * Deposita monedas si el item es válido
     */
    public static boolean depositar(Player jugador, ItemStack item) {
        if (item == null) return false;

        ItemStack oroReferencia = BancoItemUtil.getOroItem(1);
        ItemStack plataReferencia = BancoItemUtil.getPlataItem(1);

        if (BancoItemUtil.sonItemsIguales(item, oroReferencia)) {
            addOro(jugador, item.getAmount());
            return true;
        } else if (BancoItemUtil.sonItemsIguales(item, plataReferencia)) {
            addPlata(jugador, item.getAmount());
            return true;
        }
        return false;
    }

    /**
     * Retira monedas si el item es válido y el jugador tiene saldo suficiente
     */
    public static boolean retirar(Player jugador, ItemStack item) {
        if (item == null) return false;

        ItemStack oroReferencia = BancoItemUtil.getOroItem(1);
        ItemStack plataReferencia = BancoItemUtil.getPlataItem(1);

        if (BancoItemUtil.sonItemsIguales(item, oroReferencia)) {
            int cantidad = item.getAmount();
            if (getOro(jugador) >= cantidad) {
                removeOro(jugador, cantidad);
                return true;
            }
        } else if (BancoItemUtil.sonItemsIguales(item, plataReferencia)) {
            int cantidad = item.getAmount();
            if (getPlata(jugador) >= cantidad) {
                removePlata(jugador, cantidad);
                return true;
            }
        }
        return false;
    }

}
