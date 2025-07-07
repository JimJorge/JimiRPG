package com.srjimi.Banco;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BancoItemUtil {

    public static ItemStack getOroItem(int cantidad) {
        ItemStack item = new ItemStack(Material.GOLD_INGOT, cantidad);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6§lOro RPG");

        List<String> lore = new ArrayList<>();
        lore.add("§fMoneda oficial del Banco RPG");
        lore.add("§fUtilízala para comprar objetos");
        lore.add("§fen tiendas o realizar intercambios.");
        lore.add("");
        lore.add("§eSolo puede obtenerse mediante el sistema bancario.");

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }


    public static ItemStack getPlataItem(int cantidad) {
        ItemStack item = new ItemStack(Material.IRON_NUGGET, cantidad);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§7§lPlata RPG");

        List<String> lore = new ArrayList<>();
        lore.add("§fMoneda oficial del Banco RPG");
        lore.add("§fÚsala para transacciones menores");
        lore.add("§fen servicios y con aldeanos especiales.");
        lore.add("");
        lore.add("§eNo se puede craftear ni dropear.");

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }


    public static boolean esOroValido(ItemStack item) {
        if (item == null || item.getType() != Material.GOLD_INGOT) return false;
        if (!item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        String nombre = ChatColor.stripColor(meta.getDisplayName());
        if (!"Oro RPG".equalsIgnoreCase(nombre)) return false;
        if (!meta.hasLore()) return false;
        List<String> lore = meta.getLore();
        // Aquí buscas un texto que seguro tienen las monedas, por ejemplo:
        return lore.stream().anyMatch(line -> ChatColor.stripColor(line).contains("Moneda oficial del Banco"));
    }

    public static boolean esPlataValida(ItemStack item) {
        if (item == null || item.getType() != Material.IRON_NUGGET) return false;
        if (!item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        String nombre = ChatColor.stripColor(meta.getDisplayName());
        if (!"Plata RPG".equalsIgnoreCase(nombre)) return false;
        if (!meta.hasLore()) return false;
        List<String> lore = meta.getLore();
        return lore.stream().anyMatch(line -> ChatColor.stripColor(line).contains("Moneda oficial del Banco"));
    }

    /**
     * Compara si dos ItemStack son iguales en tipo, cantidad y meta (nombre, lore, flags)
     */
    public static boolean sonItemsIguales(ItemStack a, ItemStack b) {
        if (a == null || b == null) return false;
        if (a.getType() != b.getType()) return false;

        ItemMeta metaA = a.getItemMeta();
        ItemMeta metaB = b.getItemMeta();

        if (metaA == null && metaB == null) {
            return true;
        }

        if (metaA == null || metaB == null) {
            return false;
        }

        if (!metaA.getDisplayName().equals(metaB.getDisplayName())) return false;
        if (!metaA.getLore().equals(metaB.getLore())) return false;

        // Se pueden comparar flags, encantos o cualquier otro atributo si quieres.

        return true;
    }
}
