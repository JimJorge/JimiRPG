![image](https://github.com/user-attachments/assets/710a2cfc-bdf1-4340-a7d5-5ed0efb1a483)

# ⚔️ JimWord RPG

![Java](https://img.shields.io/badge/Java-17+-blue?logo=java)
![Minecraft](https://img.shields.io/badge/Minecraft-1.20.1-green?logo=minecraft)
![Vault](https://img.shields.io/badge/Vault-compatible-yellow?logo=gradle)
![License](https://img.shields.io/badge/license-MIT-blue.svg)

> Un plugin RPG avanzado para servidores Spigot/Paper 1.20.1  
> con economía, clases, protecciones, equipos, y aldeanos comerciantes personalizados.

---

## 🧩 Características principales

### 🧙 Clases & Niveles
- ✅ Sistema de clases personalizadas: *Guerrero*, *Mago*, *Tanque*.
- ✅ Niveles con sistema de progresión y color dinámico.
- ✅ Scoreboard lateral configurable con stats importantes.

### 🛡 Equipos / Clanes
- 👥 Crea y gestiona tu propio equipo con `/equipo`.
- 🛑 Friendly fire desactivado entre miembros.
- 🏷️ Nombre de equipo visible en el TAB y sobre los jugadores.
- 🧾 Configuración persistente en `equipos.yml`.

### 💰 Economía con aldeanos
- 🪙 Compra/vende con moneda propia: **Oro** y **Plata**.
- 🔄 Sistema de tradeo con aldeanos NPC personalizados.
- 🪨 Intercambio de minerales por monedas y objetos raros.

### 🏠 Protecciones de terreno
- 📦 Compra bloques de protección:
  - 🔹 20x20, 🔸 40x40, 🔶 60x60
- 🧱 Protegen una región completa hasta la altura máxima del mundo.
- 👤 Solo el dueño y miembros de su equipo pueden editar dentro.
- ✅ Guardadas en `protecciones.yml`.

---

## 💬 Comandos

### `/equipo`
```text
/equipo crear <nombre>        → Crea un equipo
/equipo invitar <jugador>     → Invita a un jugador
/equipo aceptar               → Acepta la invitación
/equipo salir                 → Sales de tu equipo
/equipo expulsar <jugador>    → Expulsa a un miembro

```
---
🧾 Scoreboard & Tab
📊 Scoreboard

-Jugador 👤
-Nivel ⚔
-Clase 🧙
-Equipo 🛡
-Rango ⭐
-Oro/Plata 💰

---
🧾 Tab personalizado

[Nv 15] [ADMIN] [⚒ EquipoX] Jugador123

---
🧱 Protecciones visuales
Coloca un bloque especial como Bloque Proteccion 20x20.

Crea una zona segura solo para ti o tu equipo.

Los cofres se protegen con carteles que indican el nombre del equipo.
---
📂 Archivos de configuración
equipos.yml: Datos de clanes.

spawn.yml: Coordenadas del lobby.

protecciones.yml: Posiciones y tamaños de zonas protegidas.
---
⚙️ Requisitos

-Spigot o Paper 1.20.1+
-Vault
-Multiverse-Core
-LuckPerms
---
👨‍💻 Autor
Desarrollado por SrJimi
Hecho con ❤️ para la comunidad de Minecraft.
