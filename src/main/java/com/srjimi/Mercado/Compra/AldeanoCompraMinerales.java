package com.srjimi.Mercado.Compra;

import com.srjimi.Banco.BancoItemUtil;
import com.srjimi.Main;
import org.bukkit.*;
import org.bukkit.entity.Villager;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

    public class AldeanoCompraMinerales implements Listener {

        private final Main plugin;

        public AldeanoCompraMinerales(Main plugin) {
            this.plugin = plugin;
            Bukkit.getPluginManager().registerEvents(this, plugin);
        }

        public void generarAldeano(Location location) {
            Villager aldeano = location.getWorld().spawn(location, Villager.class);
            aldeano.setProfession(Villager.Profession.ARMORER);
            aldeano.setCustomName("§e§lMercader de Minerales");
            aldeano.setCustomNameVisible(true);
            aldeano.setInvulnerable(true);
            aldeano.setAI(false);
            aldeano.setPersistent(true);
            aldeano.setSilent(true);

            NamespacedKey key = new NamespacedKey(plugin, "aldeano_mercado");
            aldeano.getPersistentDataContainer().set(key, PersistentDataType.STRING, "CompraMinerales"); // cambia a "armas", "armaduras", etc.

            List<MerchantRecipe> recetas = new ArrayList<>();

// Menas
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.COAL_ORE, 32)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.DEEPSLATE_COAL_ORE, 32)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.IRON_ORE, 24)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.DEEPSLATE_IRON_ORE, 24)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.COPPER_ORE, 24)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.DEEPSLATE_COPPER_ORE, 24)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.GOLD_ORE, 20)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.DEEPSLATE_GOLD_ORE, 20)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.REDSTONE_ORE, 42)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.DEEPSLATE_REDSTONE_ORE, 42)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.LAPIS_ORE, 20)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.DEEPSLATE_LAPIS_ORE, 20)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.DIAMOND_ORE, 8)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.DEEPSLATE_DIAMOND_ORE, 8)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.EMERALD_ORE, 8)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.DEEPSLATE_EMERALD_ORE, 8)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.NETHER_QUARTZ_ORE, 20)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.NETHER_GOLD_ORE, 20)));

// Minerales crudos
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.COAL, 39)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.RAW_COPPER, 39)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.LAPIS_LAZULI, 25)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.REDSTONE, 64)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.GUNPOWDER, 32)));

// Lingotes y pepitas
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.DIAMOND, 8)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.EMERALD, 8)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.IRON_INGOT, 24)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.GOLD_INGOT, 18)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.COPPER_INGOT, 30)));
            recetas.add(crearTrade(BancoItemUtil.getPlataItem(1), new ItemStack(Material.NETHERITE_INGOT, 2)));

// Establecer recetas al aldeano
            aldeano.setRecipes(recetas);

        }

        private MerchantRecipe crearTrade(ItemStack result, ItemStack costo) {
            MerchantRecipe receta = new MerchantRecipe(result, 9999);
            receta.addIngredient(costo);
            return receta;
        }
    }