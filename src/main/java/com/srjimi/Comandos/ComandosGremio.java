package com.srjimi.Comandos;

import com.srjimi.Main;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class ComandosGremio implements BasicCommand {

    private Main plugin;

    public ComandosGremio(Main plugin) {this.plugin = plugin;}

    @Override
    public void execute(CommandSourceStack stack, String[] args) {
        if (!(stack.getSender() instanceof Player player)) {
            stack.getSender().sendMessage(Component.text("Solo jugadores pueden usar este comando."));
            return;
        }
        if (!player.hasPermission("jimirpg.admin")) {
            player.sendMessage(MiniMessage.miniMessage().deserialize("<red>No tienes permiso para usar este comando."));
            return;
        }

        if (args.length == 0) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(
                    "<yellow>Usa: <green>/gremio misionesdiarias</green> | <green>misionesextra</green>"));
            return;
        }

        String subcomando = args[0].toLowerCase();
        Location loc = player.getLocation();

        switch (subcomando) {
            case "misionesdiarias" -> {
                plugin.getAldeanoGremio().crearAldeanoMisiones(loc);
                player.sendMessage(MiniMessage.miniMessage().deserialize("<green>Aldeano de misiones diarias creado correctamente."));
            }

            case "misionesextra" -> {
                // Lógica pendiente de implementar
                player.sendMessage(MiniMessage.miniMessage().deserialize("<green>Aldeano de misiones extra creado correctamente."));
            }

            default -> {
                player.sendMessage(MiniMessage.miniMessage().deserialize(
                        "<red>Subcomando no reconocido.</red> <gray>Usa:</gray> <yellow>/gremio misionesdiarias | misionesextra</yellow>"));
            }
        }
    }

    @Override
    public @Nullable String permission() {
        return "jimirpg.admin";
    }
}
