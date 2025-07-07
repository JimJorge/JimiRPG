package com.srjimi.Gremio;

import org.bukkit.entity.EntityType;

import org.bukkit.entity.EntityType;

public class Mision {
    private final String id;
    private final String nombre;
    private final String descripcion;
    private final EntityType mobObjetivo;
    private final int cantidadObjetivo;
    private final int recompensaXP;
    private final int recompensaPlata;
    private final int cooldown; // en segundos

    public Mision(String id, String nombre, String descripcion,
                  EntityType mobObjetivo, int cantidadObjetivo,
                  int recompensaXP, int recompensaPlata,
                  int cooldown) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.mobObjetivo = mobObjetivo;
        this.cantidadObjetivo = cantidadObjetivo;
        this.recompensaXP = recompensaXP;
        this.recompensaPlata = recompensaPlata;
        this.cooldown = cooldown;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public EntityType getMobObjetivo() { return mobObjetivo; }
    public int getCantidadObjetivo() { return cantidadObjetivo; }
    public int getRecompensaXP() { return recompensaXP; }
    public int getRecompensaPlata() { return recompensaPlata; }
    public int getCooldown() { return cooldown; }
}

