package com.srjimi.Clases;

import org.bukkit.entity.Player;
import com.srjimi.Main;

public abstract class Clase {

    protected Main plugin;
    protected String nombre;

    public Clase(Main plugin, String nombre) {
        this.plugin = plugin;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    // Método obligatorio que cada clase debe definir
    public abstract void activarHabilidad(Player player);

    // Si quieres también este método
    public abstract void quitarHabilidades(Player player);

}
