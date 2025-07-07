package com.srjimi.Mercado;

import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;

public class VillagerTradeHelper {

    private final Villager villager;

    public VillagerTradeHelper(Villager villager) {
        this.villager = villager;
    }
    public void limpiarTrades() {
        villager.setRecipes(new ArrayList<>()); // limpia todos los trades
    }

    public void agregarTrade(ItemStack itemCosto, ItemStack itemResultado) {
        MerchantRecipe recipe = new MerchantRecipe(itemResultado, 9999); // alto número de usos
        recipe.addIngredient(itemCosto);
        agregarRecipe(recipe);
    }

    public void agregarTrade(ItemStack itemCosto1, ItemStack itemCosto2, ItemStack itemResultado) {
        MerchantRecipe recipe = new MerchantRecipe(itemResultado, 9999);
        recipe.addIngredient(itemCosto1);
        recipe.addIngredient(itemCosto2);
        agregarRecipe(recipe);
    }

    public void agregarRecipe(MerchantRecipe recipe) {
        List<MerchantRecipe> recipes = new ArrayList<>(villager.getRecipes()); // ✅ clonar lista
        recipes.add(recipe);
        villager.setRecipes(recipes); // ✅ reemplazar
    }

    // Método opcional: genera trade a partir de 2 materiales
    public static MerchantRecipe crearTrade(Material mat1, Material mat2, int cantidad) {
        ItemStack ingrediente1 = new ItemStack(mat1, 1);
        ItemStack ingrediente2 = new ItemStack(mat2, cantidad);

        MerchantRecipe receta = new MerchantRecipe(new ItemStack(Material.AIR), 9999); // temporal
        receta.addIngredient(ingrediente1);
        receta.addIngredient(ingrediente2);
        return receta;
    }
}