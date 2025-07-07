package com.srjimi.Banco;

import com.srjimi.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class AldeanoBancoConversiones implements Listener {

    private final Main plugin;

    public AldeanoBancoConversiones(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void crearAldeano(Location loc) {
        Villager aldeano = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);

        aldeano.setCustomName(ChatColor.GOLD + "Aldeano de Conversiones");
        aldeano.setCustomNameVisible(true);
        aldeano.setInvulnerable(true);
        aldeano.setAI(false);
        aldeano.setPersistent(true);
        aldeano.setProfession(Villager.Profession.CLERIC); // O cualquier otra profesión
        aldeano.setVillagerLevel(2); // Estético

        NamespacedKey key = new NamespacedKey(plugin, "aldeano_mercado");
        aldeano.getPersistentDataContainer().set(key, PersistentDataType.STRING, "converciones"); // cambia a "armas", "armaduras", etc.

        // Crear recetas de intercambio
        List<MerchantRecipe> recetas = new ArrayList<>();

        // Trade 1: 10 plata -> 1 oro
        ItemStack plata = BancoItemUtil.getPlataItem(10);
        plata.setAmount(10);

        ItemStack oro = BancoItemUtil.getOroItem(1);

        MerchantRecipe recetaOro = new MerchantRecipe(oro, 9999); // Max usos
        recetaOro.addIngredient(plata);

        recetas.add(recetaOro);

        // Trade 2: 1 oro -> 10 plata
        ItemStack oro2 = BancoItemUtil.getOroItem(1);

        ItemStack plata10 = BancoItemUtil.getPlataItem(10);
        plata10.setAmount(10);

        MerchantRecipe recetaPlata = new MerchantRecipe(plata10, 9999);
        recetaPlata.addIngredient(oro2);

        recetas.add(recetaPlata);

        // Asignar las recetas al aldeano
        aldeano.setRecipes(recetas);
    }
}
