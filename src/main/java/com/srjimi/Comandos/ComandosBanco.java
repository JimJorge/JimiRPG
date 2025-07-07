package com.srjimi.Comandos;

import com.srjimi.Banco.AldeanoBancoConversiones;
import com.srjimi.Banco.AldeanoBancoDeposito;
import com.srjimi.Banco.AldeanoBancoRetiro;
import com.srjimi.Main;
import com.srjimi.Manager.ClaseManager;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class ComandosBanco {

    private final AldeanoBancoDeposito aldeanoBancoDeposito;
    private final AldeanoBancoRetiro aldeanoBancoRetiro;
    private final AldeanoBancoConversiones aldeanoBancoConversiones;
    private Main plugin;

    public ComandosBanco(Main plugin, AldeanoBancoDeposito aldeanoBancoDeposito, AldeanoBancoRetiro aldeanoBancoRetiro, AldeanoBancoConversiones aldeanoBancoConversiones) {
        this.plugin = plugin;
        this.aldeanoBancoDeposito = aldeanoBancoDeposito;
        this.aldeanoBancoRetiro = aldeanoBancoRetiro;
        this.aldeanoBancoConversiones = aldeanoBancoConversiones;
    }

    public void ejecutar(CommandSender sender, String[] args) {
        if (!(sender instanceof Player jugador)) {
            sender.sendMessage(Component.text("Solo jugadores pueden usar este comando."));
            return;
        }

        if (args.length == 0) {
            jugador.sendMessage(Component.text("Usa /Banco deposito | retiro | converciones>"));
            return;
        }
        if (!jugador.hasPermission("jimirpg.admin")) {
            jugador.sendMessage("§cNo tienes permiso para usar este comando.");
            return;
        }
        String clase = args[0].toLowerCase();

        switch (clase) {
            case "deposito":
                aldeanoBancoDeposito.generarAldeano(jugador,jugador.getLocation());

                jugador.sendMessage(ChatColor.GREEN + "Aldeano del banco creado y protegido.");
                break;

            case "retiro":
                aldeanoBancoRetiro.generarAldeano(jugador,jugador.getLocation());

                jugador.sendMessage(ChatColor.GREEN + "Aldeano del banco creado y protegido.");
                break;
            case "converciones":
                aldeanoBancoConversiones.crearAldeano(jugador.getLocation());
            default:
                jugador.sendMessage("Subcomando no reconocido. Usa /banco <deposito | retiro | converciones>");
        }
    }
}
