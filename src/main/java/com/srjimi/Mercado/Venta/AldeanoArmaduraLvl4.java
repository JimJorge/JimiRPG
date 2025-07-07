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

public class AldeanoArmaduraLvl4 implements Listener {

    private final Main plugin;

    public AldeanoArmaduraLvl4(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void generarAldeano(Location location) {
        Villager aldeano = location.getWorld().spawn(location, Villager.class);
        aldeano.setProfession(Villager.Profession.ARMORER);
        aldeano.setCustomName("§e§lArmadura Nivel 4");
        aldeano.setCustomNameVisible(true);
        aldeano.setInvulnerable(true);
        aldeano.setAI(false);
        aldeano.setPersistent(true);
        aldeano.setSilent(true);

        NamespacedKey key = new NamespacedKey(plugin, "aldeano_mercado");
        aldeano.getPersistentDataContainer().set(key, PersistentDataType.STRING, "armaduralvl4"); // cambia a "armas", "armaduras", etc.

        VillagerTradeHelper helper = new VillagerTradeHelper(aldeano);
        helper.limpiarTrades();
        helper.agregarTrade(BancoItemUtil.getOroItem(7), ArmaduraNivelHelper.crearEspadaNivel(3, Material.STONE_SWORD),ArmaduraNivelHelper.crearEspadaNivel(4, Material.IRON_SWORD));
        helper.agregarTrade(BancoItemUtil.getOroItem(6), ArmaduraNivelHelper.crearHerramientaNivel(3, Material.STONE_PICKAXE),ArmaduraNivelHelper.crearHerramientaNivel(4, Material.IRON_PICKAXE));
        helper.agregarTrade(BancoItemUtil.getOroItem(5), ArmaduraNivelHelper.crearHerramientaNivel(3, Material.STONE_AXE),ArmaduraNivelHelper.crearHerramientaNivel(4, Material.IRON_AXE));
        helper.agregarTrade(BancoItemUtil.getOroItem(5), ArmaduraNivelHelper.crearHerramientaNivel(3, Material.STONE_SHOVEL), ArmaduraNivelHelper.crearHerramientaNivel(4, Material.IRON_SHOVEL));
        helper.agregarTrade(BancoItemUtil.getOroItem(6), ArmaduraNivelHelper.crearArmaduraNivel(3, Material.CHAINMAIL_HELMET), ArmaduraNivelHelper.crearArmaduraNivel(4, Material.IRON_HELMET));
        helper.agregarTrade(BancoItemUtil.getOroItem(7), ArmaduraNivelHelper.crearArmaduraNivel(3, Material.CHAINMAIL_CHESTPLATE), ArmaduraNivelHelper.crearArmaduraNivel(4, Material.IRON_CHESTPLATE));
        helper.agregarTrade(BancoItemUtil.getOroItem(7), ArmaduraNivelHelper.crearArmaduraNivel(3, Material.CHAINMAIL_LEGGINGS), ArmaduraNivelHelper.crearArmaduraNivel(4, Material.IRON_LEGGINGS));
        helper.agregarTrade(BancoItemUtil.getOroItem(5), ArmaduraNivelHelper.crearArmaduraNivel(3, Material.CHAINMAIL_BOOTS), ArmaduraNivelHelper.crearArmaduraNivel(4, Material.IRON_BOOTS));

    }
}
