package com.srjimi.Comandos;


import com.srjimi.Banco.BancoManager;
import com.srjimi.Main;
import com.srjimi.Nivel.NivelManager;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class ComandosNiveles implements BasicCommand {

    private NivelManager nivelManager;

    public ComandosNiveles(NivelManager nivelManager) {this.nivelManager = nivelManager;}

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
            player.sendMessage(MiniMessage.miniMessage().deserialize("<yellow>Uso:</yellow> <green>/niveles add <jugador> <cantidad></green>"));
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


        nivelManager.addXP(player, cantidad);

        // Simulación temporal:
        player.sendMessage(MiniMessage.miniMessage().deserialize(
                "<green>Has dado <yellow>" + cantidad + "</yellow> xp a <white>" + objetivo.getName() + "</white>."));
        objetivo.sendMessage(MiniMessage.miniMessage().deserialize(
                "<green>¡Has recibido <yellow>" + cantidad + "</yellow> xp!"));
    }

    @Override
    public @Nullable String permission() {
        return "rpg.admin";
    }
}

