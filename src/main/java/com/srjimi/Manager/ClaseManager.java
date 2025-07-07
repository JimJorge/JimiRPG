package com.srjimi.Manager;

import java.util.HashMap;
import java.util.UUID;

public class ClaseManager {

    private final HashMap<UUID, String> clases = new HashMap<>();

    public void setClase(UUID uuid, String clase) {
        clases.put(uuid, clase.toLowerCase());
    }

    public String getClase(UUID uuid) {
        return clases.getOrDefault(uuid, "ninguna");
    }

    public boolean tieneClase(UUID uuid) {
        return clases.containsKey(uuid);
    }
}
