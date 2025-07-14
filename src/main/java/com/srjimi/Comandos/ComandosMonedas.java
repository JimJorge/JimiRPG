package com.srjimi.Comandos;

import com.srjimi.Banco.BancoManager;
import com.srjimi.Main;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class ComandosMonedas implements BasicCommand {

    @Override
    public void execute(CommandSourceStack stack, String[] args) {
        if (!(stack.getSender() instanceof Player player)) {
            stack.getSender().sendMessage(MiniMessage.miniMessage().deserialize("<red>Solo jugadores pueden usar este comando."));
            return;
        }

        if (!player.hasPermission("rpg.admin")) {
            player.sendMessage(MiniMessage.miniMessage().deserialize("<red>No tienes permiso para usar este comando."));
            return;
        }

        if (args.length < 3 || !args[0].equalsIgnoreCase("add")) {
            player.sendMessage(MiniMessage.miniMessage().deserialize("<yellow>Uso:</yellow> <green>/monedas add <jugador> <cantidad></green>"));
            return;
        }

        String nombreObjetivo = args[1];
        Player objetivo = Bukkit.getPlayerExact(nombreObjetivo);

        if (objetivo == null || !objetivo.isOnline()) {
            player.sendMessage(MiniMessage.miniMessage().deserialize("<red>El jugador no está conectado."));
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            player.sendMessage(MiniMessage.miniMessage().deserialize("<red>La cantidad debe ser un número válido."));
            return;
        }

        BancoManager.addPlata(objetivo,cantidad);

        // Simulación temporal:
        player.sendMessage(MiniMessage.miniMessage().deserialize(
                "<green>Has dado <yellow>" + cantidad + "</yellow> monedas de plata a <white>" + objetivo.getName() + "</white>."));
        objetivo.sendMessage(MiniMessage.miniMessage().deserialize(
                "<green>¡Has recibido <yellow>" + cantidad + "</yellow> monedas de plata!"));
    }

    @Override
    public @Nullable String permission() {
        return "rpg.admin";
    }
}

