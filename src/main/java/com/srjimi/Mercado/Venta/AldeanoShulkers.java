package com.srjimi.Mercado.Venta;

import com.srjimi.Banco.BancoItemUtil;
import com.srjimi.Banco.BancoManager;
import com.srjimi.Main;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class AldeanoShulkers {

    private final Main plugin;

    public AldeanoShulkers(Main plugin) {
        this.plugin = plugin;
    }

    public void generarAldeanoShulker(Location loc) {
        Villager villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
        villager.setCustomName("§d§lMercader de Shulkers");
        villager.setCustomNameVisible(true);
        villager.setAI(false);
        villager.setInvulnerable(true);
        villager.setPersistent(true);
        villager.setProfession(Villager.Profession.TOOLSMITH);

        // Identificador
        NamespacedKey key = new NamespacedKey(plugin, "aldeano_shulkers");
        villager.getPersistentDataContainer().set(key, PersistentDataType.STRING, "shulkers");

        List<MerchantRecipe> recetas = new ArrayList<>();

        for (DyeColor color : DyeColor.values()) {
            Material shulkerMaterial = Material.valueOf(color.name() + "_SHULKER_BOX");
            ItemStack shulker = new ItemStack(shulkerMaterial, 1);
            ItemStack pago = BancoItemUtil.getPlataItem(3);
            recetas.add(crearTrade(shulker, pago));
        }

        villager.setRecipes(recetas);
    }

    private MerchantRecipe crearTrade(ItemStack result, ItemStack costo) {
        MerchantRecipe receta = new MerchantRecipe(result, 9999);
        receta.addIngredient(costo);
        return receta;
    }
}
