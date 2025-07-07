package com.srjimi.Mercado.Venta;

import com.srjimi.Banco.BancoItemUtil;
import com.srjimi.Main;
import com.srjimi.Mercado.VillagerTradeHelper;
import org.bukkit.*;
import org.bukkit.entity.Villager;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class AldeanoBloques implements Listener {

    private final Main plugin;

    public AldeanoBloques(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void generarAldeano(Location location) {
        Villager aldeano = location.getWorld().spawn(location, Villager.class);
        aldeano.setProfession(Villager.Profession.ARMORER);
        aldeano.setCustomName("§e§lMercader de bloques");
        aldeano.setCustomNameVisible(true);
        aldeano.setInvulnerable(true);
        aldeano.setAI(false);
        aldeano.setPersistent(true);
        aldeano.setSilent(true);

        NamespacedKey key = new NamespacedKey(plugin, "aldeano_mercado");
        aldeano.getPersistentDataContainer().set(key, PersistentDataType.STRING, "bloques"); // cambia a "armas", "armaduras", etc.

        List<MerchantRecipe> recetas = new ArrayList<>();
        // Maderas
        recetas.add(crearTrade(new ItemStack(Material.SPRUCE_PLANKS, 16), BancoItemUtil.getPlataItem(4)));
        recetas.add(crearTrade(new ItemStack(Material.BIRCH_PLANKS, 16), BancoItemUtil.getPlataItem(4)));
        recetas.add(crearTrade(new ItemStack(Material.JUNGLE_PLANKS, 16), BancoItemUtil.getPlataItem(4)));
        recetas.add(crearTrade(new ItemStack(Material.ACACIA_PLANKS, 16), BancoItemUtil.getPlataItem(4)));
        recetas.add(crearTrade(new ItemStack(Material.DARK_OAK_PLANKS, 16), BancoItemUtil.getPlataItem(4)));
        recetas.add(crearTrade(new ItemStack(Material.MANGROVE_PLANKS, 16), BancoItemUtil.getPlataItem(4)));
        recetas.add(crearTrade(new ItemStack(Material.CHERRY_PLANKS, 16), BancoItemUtil.getPlataItem(4)));
        recetas.add(crearTrade(new ItemStack(Material.CRIMSON_PLANKS, 16), BancoItemUtil.getPlataItem(5)));
        recetas.add(crearTrade(new ItemStack(Material.WARPED_PLANKS, 16), BancoItemUtil.getPlataItem(5)));

        // Lanas de colores
        for (DyeColor color : DyeColor.values()) {
            Material wool = Material.getMaterial(color.name() + "_WOOL");
            if (wool != null) {
                recetas.add(crearTrade(new ItemStack(wool, 8), BancoItemUtil.getPlataItem(3)));
            }
        }

        // Cristales de colores
        for (DyeColor color : DyeColor.values()) {
            Material glass = Material.getMaterial(color.name() + "_STAINED_GLASS");
            if (glass != null) {
                recetas.add(crearTrade(new ItemStack(glass, 8), BancoItemUtil.getPlataItem(4)));
            }
        }

        // Establecer recetas al aldeano
        aldeano.setRecipes(recetas);
    }

    private MerchantRecipe crearTrade(ItemStack result, ItemStack costo) {
        MerchantRecipe receta = new MerchantRecipe(result, 9999);
        receta.addIngredient(costo);
        return receta;
    }
}