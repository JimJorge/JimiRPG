package com.srjimi.Comandos;

import com.srjimi.General.SpawnManager;
import com.srjimi.Main;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class ComandosSetSpawn implements BasicCommand {

    private SpawnManager manager;

    public ComandosSetSpawn(SpawnManager manager) {this.manager = manager;}

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

        manager.guardarSpawn(player.getLocation());
        player.sendMessage(MiniMessage.miniMessage().deserialize("<green>Spawn guardado correctamente."));
    }

    @Override
    public @Nullable String permission() {
        return "jimirpg.admin";
    }
}
