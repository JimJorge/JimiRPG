package com.srjimi.Comandos;

import com.srjimi.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.srjimi.Mercado.Venta.*;

public class ComandosMercado {
    private Main plugin;

    public ComandosMercado(Main plugin) {this.plugin = plugin;}

    public void ejecutar(CommandSender sender, String[] args) {
        if (!(sender instanceof Player jugador)) {
            sender.sendMessage(Component.text("Solo jugadores pueden usar este comando."));
            return;
        }

        if (args.length == 0) {
            jugador.sendMessage(Component.text("Usa /mercado armaduralvl1 | armaduralvl2 | armaduralvl3 | armaduralvl4 | armaduralvl5 | armaduralvl6 | semillas | comida | bloques | minerales>"));
            return;
        }
        if (!jugador.hasPermission("jimirpg.admin")) {
            jugador.sendMessage("§cNo tienes permiso para usar este comando.");
            return;
        }

        String clase = args[0].toLowerCase();
        Location loc = jugador.getLocation();

        switch (clase) {
            case "armaduralvl1":
                new AldeanoArmaduraLvl1(plugin).generarAldeano(loc);
                jugador.sendMessage(ChatColor.GREEN + "Aldeano armaduralvl1 creado y protegido.");
                break;
            case "armaduralvl2":
                new AldeanoArmaduraLvl2(plugin).generarAldeano(loc);
                jugador.sendMessage(ChatColor.GREEN + "Aldeano armaduralvl2 creado y protegido.");
                break;
            case "armaduralvl3":
                new AldeanoArmaduraLvl3(plugin).generarAldeano(loc);
                jugador.sendMessage(ChatColor.GREEN + "Aldeano armaduralvl3 creado y protegido.");
                break;
            case "armaduralvl4":
                new AldeanoArmaduraLvl4(plugin).generarAldeano(loc);
                jugador.sendMessage(ChatColor.GREEN + "Aldeano armaduralvl4 creado y protegido.");
                break;
            case "armaduralvl5":
                new AldeanoArmaduraLvl5(plugin).generarAldeano(loc);
                jugador.sendMessage(ChatColor.GREEN + "Aldeano armaduralvl5 creado y protegido.");
                break;
            case "armaduralvl6":
                new AldeanoArmaduraLvl6(plugin).generarAldeano(loc);
                jugador.sendMessage(ChatColor.GREEN + "Aldeano armaduralvl6 creado y protegido.");
                break;
            case "semillas":
                new AldeanoSemillas(plugin).generarAldeano(loc);
                jugador.sendMessage(ChatColor.GREEN + "Aldeano semillas creado y protegido.");
                break;
            case "bloques":
                new AldeanoBloques(plugin).generarAldeano(loc);
                jugador.sendMessage(ChatColor.GREEN + "Aldeano bloques creado y protegido.");
                break;
            case "comida":
                new AldeanoComida(plugin).generarAldeano(loc);
                jugador.sendMessage(ChatColor.GREEN + "Aldeano comida creado y protegido.");
                break;
            case "minerales":
                new AldeanoComida(plugin).generarAldeano(loc);
                jugador.sendMessage(ChatColor.GREEN + "Aldeano minerales creado y protegido.");
                break;
            default:
                jugador.sendMessage("Subcomando no reconocido. Usa /mercado armaduralvl1 | armaduralvl2 | armaduralvl3 | armaduralvl4 | armaduralvl5 | armaduralvl6 | semillas | comida | bloques | minerales>");
        }
    }
}
