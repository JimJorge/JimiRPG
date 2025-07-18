package com.srjimi.Comandos;

import com.srjimi.General.SpawnManager;
import com.srjimi.Main;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class ComandosSpawn implements BasicCommand {

    private SpawnManager manager;

    public ComandosSpawn(SpawnManager manager) {this.manager = manager;}

    @Override
    public void execute(CommandSourceStack stack, String[] args) {
        if (!(stack.getSender() instanceof Player player)) {
            stack.getSender().sendMessage(Component.text("Solo jugadores pueden usar este comando."));
            return;
        }

        Location loc = manager.obtenerSpawn();
        if (loc == null) {
            player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Spawn no configurado aún."));
            return;
        }

        player.teleport(loc);
        player.sendMessage(MiniMessage.miniMessage().deserialize("<green>¡Has sido teletransportado al lobby!"));
    }

    @Override
    public @Nullable String permission() {
        return null; // Sin restricción de permisos, todos los jugadores pueden usarlo
    }
}
