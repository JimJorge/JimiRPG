package com.srjimi.Aldeanos;

import com.srjimi.Banco.AldeanoBancoDeposito;
import com.srjimi.Banco.AldeanoBancoRetiro;
import com.srjimi.Main;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class InteractuarConAldeano implements Listener {

    private final Main plugin;
    private final AldeanoBancoDeposito bancoDeposito;
    private final AldeanoBancoRetiro bancoRetiro;

    public InteractuarConAldeano(Main plugin, AldeanoBancoDeposito deposito, AldeanoBancoRetiro retiro) {
        this.plugin = plugin;
        this.bancoDeposito = deposito;
        this.bancoRetiro = retiro;
    }

    @EventHandler
    public void alInteractuarConAldeano(PlayerInteractEntityEvent event) {
        Player jugador = event.getPlayer();
        Entity entidad = event.getRightClicked();

        if (!(entidad instanceof Villager)) return;

        Villager aldeano = (Villager) entidad;
        PersistentDataContainer data = aldeano.getPersistentDataContainer();

        NamespacedKey depositoKey = new NamespacedKey(plugin, "aldeano_banco_deposito");
        NamespacedKey retiroKey = new NamespacedKey(plugin, "aldeano_banco_retiro");

        if (data.has(depositoKey, PersistentDataType.STRING)) {
            event.setCancelled(true);
            bancoDeposito.abrirDeposito(jugador);
        }

        if (data.has(retiroKey, PersistentDataType.STRING)) {
            event.setCancelled(true);
            bancoRetiro.abrirGUI(jugador);
        }
    }
}
