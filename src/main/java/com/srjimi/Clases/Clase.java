package com.srjimi.Clases;

public enum Clase {
    GUERRERO,
    MAGO,
    ARQUERO;

    public static Clase desdeNombre(String nombre) {
        return switch (nombre.toLowerCase()) {
            case "guerrero" -> GUERRERO;
            case "mago" -> MAGO;
            case "arquero" -> ARQUERO;
            default -> null;
        };
    }
}