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

public class AldeanoComida implements Listener {

    private final Main plugin;

    public AldeanoComida(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void generarAldeano(Location location) {
        Villager aldeano = location.getWorld().spawn(location, Villager.class);
        aldeano.setProfession(Villager.Profession.ARMORER);
        aldeano.setCustomName("§e§lMercader de Comida");
        aldeano.setCustomNameVisible(true);
        aldeano.setInvulnerable(true);
        aldeano.setAI(false);
        aldeano.setPersistent(true);
        aldeano.setSilent(true);

        NamespacedKey key = new NamespacedKey(plugin, "aldeano_mercado");
        aldeano.getPersistentDataContainer().set(key, PersistentDataType.STRING, "comida"); // cambia a "armas", "armaduras", etc.


        List<MerchantRecipe> recetas = new ArrayList<>();

        recetas.add(crearTrade(new ItemStack(Material.APPLE, 14), BancoItemUtil.getPlataItem(3)));
        recetas.add(crearTrade(new ItemStack(Material.GOLDEN_APPLE, 18), BancoItemUtil.getOroItem(2)));

        recetas.add(crearTrade(new ItemStack(Material.CARROT, 20), BancoItemUtil.getPlataItem(3)));
        recetas.add(crearTrade(new ItemStack(Material.GOLDEN_CARROT, 16), BancoItemUtil.getOroItem(1)));

        recetas.add(crearTrade(new ItemStack(Material.BAKED_POTATO, 21), BancoItemUtil.getPlataItem(4)));
        recetas.add(crearTrade(new ItemStack(Material.MELON_SLICE, 64), BancoItemUtil.getPlataItem(5)));

        recetas.add(crearTrade(new ItemStack(Material.COOKED_BEEF, 32), BancoItemUtil.getPlataItem(7)));     // Vaca
        recetas.add(crearTrade(new ItemStack(Material.COOKED_CHICKEN, 32), BancoItemUtil.getPlataItem(7)));  // Pollo
        recetas.add(crearTrade(new ItemStack(Material.COOKED_MUTTON, 32), BancoItemUtil.getPlataItem(7)));   // Oveja

        recetas.add(crearTrade(new ItemStack(Material.COOKED_COD, 16), BancoItemUtil.getPlataItem(4)));
        recetas.add(crearTrade(new ItemStack(Material.COOKED_SALMON, 16), BancoItemUtil.getPlataItem(4)));

        recetas.add(crearTrade(new ItemStack(Material.BREAD, 32), BancoItemUtil.getPlataItem(5)));
        recetas.add(crearTrade(new ItemStack(Material.COOKIE, 42), BancoItemUtil.getPlataItem(6)));
        recetas.add(crearTrade(new ItemStack(Material.PUMPKIN_PIE, 13), BancoItemUtil.getPlataItem(6)));


        // Establecer recetas al aldeano
        aldeano.setRecipes(recetas);
    }

    private MerchantRecipe crearTrade(ItemStack result, ItemStack costo) {
        MerchantRecipe receta = new MerchantRecipe(result, 9999);
        receta.addIngredient(costo);
        return receta;
    }
}