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

public class AldeanoArmaduraLvl3 implements Listener {

    private final Main plugin;

    public AldeanoArmaduraLvl3(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void generarAldeano(Location location) {
        Villager aldeano = location.getWorld().spawn(location, Villager.class);
        aldeano.setProfession(Villager.Profession.ARMORER);
        aldeano.setCustomName("§e§lArmadura Nivel 3");
        aldeano.setCustomNameVisible(true);
        aldeano.setInvulnerable(true);
        aldeano.setAI(false);
        aldeano.setPersistent(true);
        aldeano.setSilent(true);

        // Marcar al aldeano para identificación con namespaced key
        NamespacedKey key = new NamespacedKey(plugin, "aldeano_mercado");
        aldeano.getPersistentDataContainer().set(key, PersistentDataType.STRING, "armaduralvl3"); // cambia a "armas", "armaduras", etc.

        VillagerTradeHelper helper = new VillagerTradeHelper(aldeano);
        helper.limpiarTrades();
        helper.agregarTrade(BancoItemUtil.getOroItem(5), ArmaduraNivelHelper.crearEspadaNivel(2, Material.GOLDEN_SWORD),ArmaduraNivelHelper.crearEspadaNivel(3, Material.STONE_SWORD));
        helper.agregarTrade(BancoItemUtil.getOroItem(4), ArmaduraNivelHelper.crearHerramientaNivel(2, Material.GOLDEN_PICKAXE),ArmaduraNivelHelper.crearHerramientaNivel(3, Material.STONE_PICKAXE));
        helper.agregarTrade(BancoItemUtil.getOroItem(4), ArmaduraNivelHelper.crearHerramientaNivel(2, Material.GOLDEN_AXE),ArmaduraNivelHelper.crearHerramientaNivel(3, Material.STONE_AXE));
        helper.agregarTrade(BancoItemUtil.getOroItem(4), ArmaduraNivelHelper.crearHerramientaNivel(2, Material.GOLDEN_SHOVEL), ArmaduraNivelHelper.crearHerramientaNivel(3, Material.STONE_SHOVEL));
        helper.agregarTrade(BancoItemUtil.getOroItem(4), ArmaduraNivelHelper.crearArmaduraNivel(2, Material.GOLDEN_HELMET), ArmaduraNivelHelper.crearArmaduraNivel(3, Material.CHAINMAIL_HELMET));
        helper.agregarTrade(BancoItemUtil.getOroItem(5), ArmaduraNivelHelper.crearArmaduraNivel(2, Material.GOLDEN_CHESTPLATE), ArmaduraNivelHelper.crearArmaduraNivel(3, Material.CHAINMAIL_CHESTPLATE));
        helper.agregarTrade(BancoItemUtil.getOroItem(5), ArmaduraNivelHelper.crearArmaduraNivel(2, Material.GOLDEN_LEGGINGS), ArmaduraNivelHelper.crearArmaduraNivel(3, Material.CHAINMAIL_LEGGINGS));
        helper.agregarTrade(BancoItemUtil.getOroItem(4), ArmaduraNivelHelper.crearArmaduraNivel(2, Material.GOLDEN_BOOTS), ArmaduraNivelHelper.crearArmaduraNivel(3, Material.CHAINMAIL_BOOTS));

    }
}
