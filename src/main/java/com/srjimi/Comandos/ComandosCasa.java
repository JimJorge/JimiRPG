package com.srjimi.Comandos;

import com.srjimi.General.Casa;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class ComandosCasa implements BasicCommand {

    private final Casa casa;

    public ComandosCasa(Casa casa) {
        this.casa = casa;
    }

    @Override
    public void execute(CommandSourceStack stack, String[] args) {
        if (!(stack.getSender() instanceof Player jugador)) {
            stack.getSender().sendMessage(MiniMessage.miniMessage().deserialize("<red>Solo jugadores pueden usar este comando."));
            return;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("set")) {
            casa.guardarCasa(jugador);
            jugador.sendMessage(MiniMessage.miniMessage().deserialize("<green>¡Casa guardada correctamente!</green>"));
            return;
        }

        Location loc = casa.obtenerCasa(jugador);
        if (loc == null) {
            jugador.sendMessage(MiniMessage.miniMessage().deserialize("<red>No tienes una casa guardada. Usa </red><yellow>/casa set</yellow>"));
            return;
        }

        jugador.teleport(loc);
        jugador.sendMessage(MiniMessage.miniMessage().deserialize("<green>¡Te has teletransportado a tu casa!</green>"));
    }

    @Override
    public @Nullable String permission() {
        return null; // Opcional: puedes agregar "rpg.casa"
    }
}
