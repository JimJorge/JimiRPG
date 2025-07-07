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

public class AldeanoArmaduraLvl5 implements Listener {

    private final Main plugin;

    public AldeanoArmaduraLvl5(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void generarAldeano(Location location) {
        Villager aldeano = location.getWorld().spawn(location, Villager.class);
        aldeano.setProfession(Villager.Profession.ARMORER);
        aldeano.setCustomName("§e§lArmadura Nivel 5");
        aldeano.setCustomNameVisible(true);
        aldeano.setInvulnerable(true);
        aldeano.setAI(false);
        aldeano.setPersistent(true);
        aldeano.setSilent(true);

        NamespacedKey key = new NamespacedKey(plugin, "aldeano_mercado");
        aldeano.getPersistentDataContainer().set(key, PersistentDataType.STRING, "armaduralvl5"); // cambia a "armas", "armaduras", etc.

        VillagerTradeHelper helper = new VillagerTradeHelper(aldeano);
        helper.limpiarTrades();
        helper.agregarTrade(BancoItemUtil.getOroItem(9), ArmaduraNivelHelper.crearEspadaNivel(4, Material.IRON_SWORD),ArmaduraNivelHelper.crearEspadaNivel(5, Material.DIAMOND_SWORD));
        helper.agregarTrade(BancoItemUtil.getOroItem(7), ArmaduraNivelHelper.crearHerramientaNivel(4, Material.IRON_PICKAXE),ArmaduraNivelHelper.crearHerramientaNivel(5, Material.DIAMOND_PICKAXE));
        helper.agregarTrade(BancoItemUtil.getOroItem(6), ArmaduraNivelHelper.crearHerramientaNivel(4, Material.IRON_AXE),ArmaduraNivelHelper.crearHerramientaNivel(5, Material.DIAMOND_AXE));
        helper.agregarTrade(BancoItemUtil.getOroItem(6), ArmaduraNivelHelper.crearHerramientaNivel(4, Material.IRON_SHOVEL), ArmaduraNivelHelper.crearHerramientaNivel(5, Material.DIAMOND_SHOVEL));
        helper.agregarTrade(BancoItemUtil.getOroItem(7), ArmaduraNivelHelper.crearArmaduraNivel(4, Material.IRON_HELMET), ArmaduraNivelHelper.crearArmaduraNivel(5, Material.DIAMOND_HELMET));
        helper.agregarTrade(BancoItemUtil.getOroItem(8), ArmaduraNivelHelper.crearArmaduraNivel(4, Material.IRON_CHESTPLATE), ArmaduraNivelHelper.crearArmaduraNivel(5, Material.DIAMOND_CHESTPLATE));
        helper.agregarTrade(BancoItemUtil.getOroItem(8), ArmaduraNivelHelper.crearArmaduraNivel(4, Material.IRON_LEGGINGS), ArmaduraNivelHelper.crearArmaduraNivel(5, Material.DIAMOND_LEGGINGS));
        helper.agregarTrade(BancoItemUtil.getOroItem(7), ArmaduraNivelHelper.crearArmaduraNivel(4, Material.IRON_BOOTS), ArmaduraNivelHelper.crearArmaduraNivel(5, Material.DIAMOND_BOOTS));

    }
}
