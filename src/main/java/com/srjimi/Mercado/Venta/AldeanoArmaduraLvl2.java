package com.srjimi.Mercado.Venta;

import com.srjimi.Banco.BancoItemUtil;
import com.srjimi.Main;
import com.srjimi.Mercado.VillagerTradeHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Villager;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;

public class AldeanoArmaduraLvl2 implements Listener {

    private final Main plugin;

    public AldeanoArmaduraLvl2(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void generarAldeano(Location location) {
        Villager aldeano = location.getWorld().spawn(location, Villager.class);
        aldeano.setProfession(Villager.Profession.ARMORER);
        aldeano.setCustomName("§e§lArmadura Nivel 2");
        aldeano.setCustomNameVisible(true);
        aldeano.setInvulnerable(true);
        aldeano.setAI(false);
        aldeano.setPersistent(true);
        aldeano.setSilent(true);

        // Marcar al aldeano para identificación con namespaced key
        NamespacedKey key = new NamespacedKey(plugin, "aldeano_mercado");
        aldeano.getPersistentDataContainer().set(key, PersistentDataType.STRING, "armaduralvl2"); // cambia a "armas", "armaduras", etc.

        VillagerTradeHelper helper = new VillagerTradeHelper(aldeano);
        helper.limpiarTrades();
        helper.agregarTrade(BancoItemUtil.getOroItem(5), ArmaduraNivelHelper.crearEspadaNivel(1, Material.WOODEN_SWORD),ArmaduraNivelHelper.crearEspadaNivel(2, Material.GOLDEN_SWORD));
        helper.agregarTrade(BancoItemUtil.getOroItem(4), ArmaduraNivelHelper.crearHerramientaNivel(1, Material.WOODEN_PICKAXE),ArmaduraNivelHelper.crearHerramientaNivel(2, Material.GOLDEN_PICKAXE));
        helper.agregarTrade(BancoItemUtil.getOroItem(4), ArmaduraNivelHelper.crearHerramientaNivel(1, Material.WOODEN_AXE),ArmaduraNivelHelper.crearHerramientaNivel(2, Material.GOLDEN_AXE));
        helper.agregarTrade(BancoItemUtil.getOroItem(4), ArmaduraNivelHelper.crearHerramientaNivel(1, Material.WOODEN_SHOVEL), ArmaduraNivelHelper.crearHerramientaNivel(2, Material.GOLDEN_SHOVEL));
        helper.agregarTrade(BancoItemUtil.getOroItem(4), ArmaduraNivelHelper.crearArmaduraNivel(1, Material.LEATHER_HELMET), ArmaduraNivelHelper.crearArmaduraNivel(2, Material.GOLDEN_HELMET));
        helper.agregarTrade(BancoItemUtil.getOroItem(5), ArmaduraNivelHelper.crearArmaduraNivel(1, Material.LEATHER_CHESTPLATE), ArmaduraNivelHelper.crearArmaduraNivel(2, Material.GOLDEN_CHESTPLATE));
        helper.agregarTrade(BancoItemUtil.getOroItem(5), ArmaduraNivelHelper.crearArmaduraNivel(1, Material.LEATHER_LEGGINGS), ArmaduraNivelHelper.crearArmaduraNivel(2, Material.GOLDEN_LEGGINGS));
        helper.agregarTrade(BancoItemUtil.getOroItem(4), ArmaduraNivelHelper.crearArmaduraNivel(1, Material.LEATHER_BOOTS), ArmaduraNivelHelper.crearArmaduraNivel(2, Material.GOLDEN_BOOTS));

    }
}
