package com.srjimi.Mercado.Venta;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArmaduraNivelHelper {

    public static ItemStack crearArmaduraNivel(int nivel, Material tipo) {
        ItemStack item = new ItemStack(tipo);
        ItemMeta meta = item.getItemMeta();

        if (meta == null) return item; // Protege si no hay meta

        int proteccion = 0;
        int irrompibilidad = 0;

        switch (nivel) {
            case 1 -> {
                proteccion = 3;
                irrompibilidad = 10;
            }
            case 2 -> {
                proteccion = 5;
                irrompibilidad = 11;
            }
            case 3 -> {
                proteccion = 7;
                irrompibilidad = 12;
            }
            case 4 -> {
                proteccion = 9;
                irrompibilidad = 13;
            }
            case 5 -> {
                proteccion = 11;
                irrompibilidad = 15;
            }
            case 6 -> {
                proteccion = 13;
                irrompibilidad = 17;
            }
        }

        meta.setDisplayName("§" + getColor(nivel) + "§lArmadura Nivel " + nivel);
        meta.addEnchant(Enchantment.PROTECTION, proteccion, true);
        meta.addEnchant(Enchantment.UNBREAKING, irrompibilidad, true);
        meta.addEnchant(Enchantment.MENDING, 1, true);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack crearEspadaNivel(int nivel, Material tipo) {
        ItemStack item = new ItemStack(tipo);
        ItemMeta meta = item.getItemMeta();

        if (meta == null) return item;

        int sharpness = 0;
        int irrompibilidad = 0;

        switch (nivel) {
            case 1 -> {
                sharpness = 3;
                irrompibilidad = 10;
            }
            case 2 -> {
                sharpness = 5;
                irrompibilidad = 11;
            }
            case 3 -> {
                sharpness = 7;
                irrompibilidad = 12;
            }
            case 4 -> {
                sharpness = 9;
                irrompibilidad = 13;
            }
            case 5 -> {
                sharpness = 11;
                irrompibilidad = 15;
            }
            case 6 -> {
                sharpness = 13;
                irrompibilidad = 17;
            }
        }

        meta.setDisplayName("§" + getColor(nivel) + "§lEspada Nivel " + nivel);
        meta.addEnchant(Enchantment.SHARPNESS, sharpness, true);
        meta.addEnchant(Enchantment.UNBREAKING, irrompibilidad, true);
        meta.addEnchant(Enchantment.MENDING, 1, true);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack crearHerramientaNivel(int nivel, Material tipo) {
        ItemStack item = new ItemStack(tipo);
        ItemMeta meta = item.getItemMeta();

        if (meta == null) return item;

        int eficiencia = 0;
        int irrompibilidad = 0;

        switch (nivel) {
            case 1 -> {
                eficiencia = 3;
                irrompibilidad = 10;
            }
            case 2 -> {
                eficiencia = 5;
                irrompibilidad = 11;
            }
            case 3 -> {
                eficiencia = 7;
                irrompibilidad = 12;
            }
            case 4 -> {
                eficiencia = 9;
                irrompibilidad = 13;
            }
            case 5 -> {
                eficiencia = 11;
                irrompibilidad = 15;
            }
            case 6 -> {
                eficiencia = 13;
                irrompibilidad = 17;
            }
        }

        meta.setDisplayName("§" + getColor(nivel) + "§lHerramienta Nivel " + nivel);
        meta.addEnchant(Enchantment.EFFICIENCY, eficiencia, true); // eficiencia
        meta.addEnchant(Enchantment.UNBREAKING, irrompibilidad, true);
        meta.addEnchant(Enchantment.MENDING, 1, true);

        item.setItemMeta(meta);
        return item;
    }

    private static String getColor(int nivel) {
        return switch (nivel) {
            case 1 -> "7";  // Gris claro
            case 2 -> "e";  // Amarillo
            case 3 -> "b";  // Azul claro
            case 4 -> "f";  // Blanco
            case 5 -> "3";  // Aqua oscuro
            case 6 -> "6";  // Naranja dorado
            default -> "7";
        };
    }
}

