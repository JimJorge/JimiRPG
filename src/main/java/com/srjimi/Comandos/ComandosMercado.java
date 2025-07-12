package com.srjimi.Comandos;

import com.srjimi.Main;
import com.srjimi.Mercado.Compra.AldeanoCompraMinerales;
import com.srjimi.Mercado.Venta.*;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class ComandosMercado implements BasicCommand {

    private Main plugin;

    public ComandosMercado(Main plugin) {this.plugin = plugin;}

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
                    "<yellow>Usa:</yellow> <green>/mercado armaduralvl1 | armaduralvl2 | armaduralvl3 | armaduralvl4 | armaduralvl5 | armaduralvl6 | semillas | comida | bloques | minerales</green>"
            ));
            return;
        }

        String subcomando = args[0].toLowerCase();
        Location loc = player.getLocation();

        switch (subcomando) {
            case "armaduralvl1" -> {
                new AldeanoArmaduraLvl1(plugin).generarAldeano(loc);
                mensajeOK(player, "Aldeano armaduralvl1 creado y protegido.");
            }
            case "armaduralvl2" -> {
                new AldeanoArmaduraLvl2(plugin).generarAldeano(loc);
                mensajeOK(player, "Aldeano armaduralvl2 creado y protegido.");
            }
            case "armaduralvl3" -> {
                new AldeanoArmaduraLvl3(plugin).generarAldeano(loc);
                mensajeOK(player, "Aldeano armaduralvl3 creado y protegido.");
            }
            case "armaduralvl4" -> {
                new AldeanoArmaduraLvl4(plugin).generarAldeano(loc);
                mensajeOK(player, "Aldeano armaduralvl4 creado y protegido.");
            }
            case "armaduralvl5" -> {
                new AldeanoArmaduraLvl5(plugin).generarAldeano(loc);
                mensajeOK(player, "Aldeano armaduralvl5 creado y protegido.");
            }
            case "armaduralvl6" -> {
                new AldeanoArmaduraLvl6(plugin).generarAldeano(loc);
                mensajeOK(player, "Aldeano armaduralvl6 creado y protegido.");
            }
            case "semillas" -> {
                new AldeanoSemillas(plugin).generarAldeano(loc);
                mensajeOK(player, "Aldeano semillas creado y protegido.");
            }
            case "bloques" -> {
                new AldeanoBloques(plugin).generarAldeano(loc);
                mensajeOK(player, "Aldeano bloques creado y protegido.");
            }
            case "comida" -> {
                new AldeanoComida(plugin).generarAldeano(loc);
                mensajeOK(player, "Aldeano comida creado y protegido.");
            }
            case "minerales" -> {
                new AldeanoCompraMinerales(plugin).generarAldeano(loc);
                mensajeOK(player, "Aldeano minerales creado y protegido.");
            }
            default -> {
                player.sendMessage(MiniMessage.miniMessage().deserialize(
                        "<red>Subcomando no reconocido.</red> <gray>Usa:</gray> <yellow>/mercado armaduralvl1 | armaduralvl2 | armaduralvl3 | armaduralvl4 | armaduralvl5 | armaduralvl6 | semillas | comida | bloques | minerales</yellow>"
                ));
            }
        }
    }

    private void mensajeOK(Player player, String msg) {
        player.sendMessage(MiniMessage.miniMessage().deserialize("<green>" + msg + "</green>"));
    }

    @Override
    public @Nullable String permission() {
        return "jimirpg.admin";
    }
}
