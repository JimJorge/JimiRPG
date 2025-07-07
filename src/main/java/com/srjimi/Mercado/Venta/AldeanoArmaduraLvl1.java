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

public class AldeanoArmaduraLvl1 implements Listener {

    private final Main plugin;

    public AldeanoArmaduraLvl1(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void generarAldeano(Location location) {
        Villager aldeano = location.getWorld().spawn(location, Villager.class);
        aldeano.setProfession(Villager.Profession.ARMORER);
        aldeano.setCustomName("§e§lArmadura Nivel 1");
        aldeano.setCustomNameVisible(true);
        aldeano.setInvulnerable(true);
        aldeano.setAI(false);
        aldeano.setPersistent(true);
        aldeano.setSilent(true);

        NamespacedKey key = new NamespacedKey(plugin, "aldeano_mercado");
        aldeano.getPersistentDataContainer().set(key, PersistentDataType.STRING, "armaduralvl1"); // cambia a "armas", "armaduras", etc.


        VillagerTradeHelper helper = new VillagerTradeHelper(aldeano);
        helper.limpiarTrades();
        helper.agregarTrade(BancoItemUtil.getOroItem(2), ArmaduraNivelHelper.crearEspadaNivel(1, Material.WOODEN_SWORD));
        helper.agregarTrade(BancoItemUtil.getOroItem(3), ArmaduraNivelHelper.crearHerramientaNivel(1, Material.WOODEN_PICKAXE));
        helper.agregarTrade(BancoItemUtil.getOroItem(2), ArmaduraNivelHelper.crearHerramientaNivel(1, Material.WOODEN_AXE));
        helper.agregarTrade(BancoItemUtil.getOroItem(2), ArmaduraNivelHelper.crearHerramientaNivel(1, Material.WOODEN_SHOVEL));
        helper.agregarTrade(BancoItemUtil.getOroItem(2), ArmaduraNivelHelper.crearArmaduraNivel(1, Material.LEATHER_HELMET));
        helper.agregarTrade(BancoItemUtil.getOroItem(2), ArmaduraNivelHelper.crearArmaduraNivel(1, Material.LEATHER_CHESTPLATE));
        helper.agregarTrade(BancoItemUtil.getOroItem(2), ArmaduraNivelHelper.crearArmaduraNivel(1, Material.LEATHER_LEGGINGS));
        helper.agregarTrade(BancoItemUtil.getOroItem(2), ArmaduraNivelHelper.crearArmaduraNivel(1, Material.LEATHER_BOOTS));
    }
}
