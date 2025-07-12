package com.srjimi.Comandos;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class ComandosAnuncios implements BasicCommand {

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        final Component name = commandSourceStack.getExecutor() != null
                ? commandSourceStack.getExecutor().name()
                : commandSourceStack.getSender().name();

        if (args.length == 0) {
            commandSourceStack.getSender().sendRichMessage("<red>¡No puedes enviar un anuncio vacío!");
            return;
        }

        final String message = String.join(" ", args);
        final Component broadcastMessage = MiniMessage.miniMessage().deserialize(
                "<gold><bold>JIMWORD RPG </gold><green><bold>ANUNCIO</green><dark_gray>»</dark_gray> <message>",
                Placeholder.unparsed("message", message)
        );

        Bukkit.broadcast(broadcastMessage);
    }

    @Override
    public @Nullable String permission() {
        return "jimirpg.admin";
    }
}