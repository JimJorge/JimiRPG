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

public class AldeanoArmaduraLvl6 implements Listener {

    private final Main plugin;

    public AldeanoArmaduraLvl6(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void generarAldeano(Location location) {
        Villager aldeano = location.getWorld().spawn(location, Villager.class);
        aldeano.setProfession(Villager.Profession.ARMORER);
        aldeano.setCustomName("§e§lArmadura Nivel 6");
        aldeano.setCustomNameVisible(true);
        aldeano.setInvulnerable(true);
        aldeano.setAI(false);
        aldeano.setPersistent(true);
        aldeano.setSilent(true);

        NamespacedKey key = new NamespacedKey(plugin, "aldeano_mercado");
        aldeano.getPersistentDataContainer().set(key, PersistentDataType.STRING, "armaduralvl6"); // cambia a "armas", "armaduras", etc.

        VillagerTradeHelper helper = new VillagerTradeHelper(aldeano);
        helper.limpiarTrades();
        helper.agregarTrade(BancoItemUtil.getOroItem(11), ArmaduraNivelHelper.crearEspadaNivel(5, Material.DIAMOND_SWORD),ArmaduraNivelHelper.crearEspadaNivel(6, Material.NETHERITE_SWORD));
        helper.agregarTrade(BancoItemUtil.getOroItem(8), ArmaduraNivelHelper.crearHerramientaNivel(5, Material.DIAMOND_PICKAXE),ArmaduraNivelHelper.crearHerramientaNivel(6, Material.NETHERITE_PICKAXE));
        helper.agregarTrade(BancoItemUtil.getOroItem(7), ArmaduraNivelHelper.crearHerramientaNivel(5, Material.DIAMOND_AXE),ArmaduraNivelHelper.crearHerramientaNivel(6, Material.NETHERITE_AXE));
        helper.agregarTrade(BancoItemUtil.getOroItem(7), ArmaduraNivelHelper.crearHerramientaNivel(5, Material.DIAMOND_SHOVEL), ArmaduraNivelHelper.crearHerramientaNivel(6, Material.NETHERITE_SHOVEL));
        helper.agregarTrade(BancoItemUtil.getOroItem(8), ArmaduraNivelHelper.crearArmaduraNivel(5, Material.DIAMOND_HELMET), ArmaduraNivelHelper.crearArmaduraNivel(6, Material.NETHERITE_HELMET));
        helper.agregarTrade(BancoItemUtil.getOroItem(9), ArmaduraNivelHelper.crearArmaduraNivel(5, Material.DIAMOND_CHESTPLATE), ArmaduraNivelHelper.crearArmaduraNivel(6, Material.NETHERITE_CHESTPLATE));
        helper.agregarTrade(BancoItemUtil.getOroItem(9), ArmaduraNivelHelper.crearArmaduraNivel(5, Material.DIAMOND_LEGGINGS), ArmaduraNivelHelper.crearArmaduraNivel(6, Material.NETHERITE_LEGGINGS));
        helper.agregarTrade(BancoItemUtil.getOroItem(8), ArmaduraNivelHelper.crearArmaduraNivel(5, Material.DIAMOND_BOOTS), ArmaduraNivelHelper.crearArmaduraNivel(6, Material.NETHERITE_BOOTS));

    }

}
