package com.srjimi.Comandos;

import com.srjimi.Banco.AldeanoBancoConversiones;
import com.srjimi.Banco.AldeanoBancoDeposito;
import com.srjimi.Banco.AldeanoBancoRetiro;
import com.srjimi.Main;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class ComandosBanco implements BasicCommand {

    private AldeanoBancoDeposito aldeanoBancoDeposito;
    private AldeanoBancoRetiro aldeanoBancoRetiro;
    private AldeanoBancoConversiones aldeanoBancoConversiones;

    public ComandosBanco(AldeanoBancoDeposito aldeanoBancoDeposito,AldeanoBancoConversiones aldeanoBancoConversiones,AldeanoBancoRetiro aldeanoBancoRetiro) {
        this.aldeanoBancoDeposito = aldeanoBancoDeposito;
        this.aldeanoBancoRetiro = aldeanoBancoRetiro;
        this.aldeanoBancoConversiones = aldeanoBancoConversiones;
    }

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
                    "<yellow>Usa: <green>/banco deposito</green> | <green>retiro</green> | <green>conversiones</green>"));
            return;
        }

        switch (args[0].toLowerCase()) {
            case "deposito" -> {
                aldeanoBancoDeposito.generarAldeano(player, player.getLocation());
                player.sendMessage(MiniMessage.miniMessage().deserialize("<green>Aldeano de depósito creado correctamente."));
            }

            case "retiro" -> {
                aldeanoBancoRetiro.generarAldeano(player, player.getLocation());
                player.sendMessage(MiniMessage.miniMessage().deserialize("<green>Aldeano de retiro creado correctamente."));
            }

            case "conversiones" -> {
                aldeanoBancoConversiones.crearAldeano(player.getLocation());
                player.sendMessage(MiniMessage.miniMessage().deserialize("<green>Aldeano de conversiones creado correctamente."));
            }

            default -> {
                player.sendMessage(MiniMessage.miniMessage().deserialize(
                        "<red>Subcomando no reconocido.</red> <gray>Usa:</gray> <yellow>/banco deposito | retiro | conversiones</yellow>"));
            }
        }
    }

    @Override
    public @Nullable String permission() {
        return "jimirpg.admin";
    }
}
