package com.srjimi;

import com.srjimi.Aldeanos.Aldeanos;
import com.srjimi.Aldeanos.InteractuarConAldeano;
import com.srjimi.Aldeanos.ProteccionAldeanos;
import com.srjimi.Banco.*;
import com.srjimi.Chat.ChatListener;
import com.srjimi.Clases.AldeanoClases;
import com.srjimi.Clases.Clase;
import com.srjimi.Clases.ClasesListener;
import com.srjimi.Clases.ClasesMain;
import com.srjimi.Comandos.*;
import com.srjimi.Equipo.EquipoListener;
import com.srjimi.Equipo.EquipoManager;
import com.srjimi.General.SpawnListener;
import com.srjimi.General.SpawnManager;
import com.srjimi.Gremio.AldeanoGremio;
import com.srjimi.Gremio.MisionDiariaManager;
import com.srjimi.Aldeanos.AldeanosGuardarYproteger;
import com.srjimi.Listeners.IngresoSalidaListener;
import com.srjimi.Listeners.MuerteListener;
import com.srjimi.Nivel.NivelListener;
import com.srjimi.Nivel.NivelManager;
import com.srjimi.Scoreboard.ScoreboardManager;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public final class Main extends JavaPlugin implements Listener {

    public ScoreboardManager scoreboardManager;
    private File bancoFile;
    private FileConfiguration bancoConfig;
    private AldeanoBancoDeposito aldeanoBancoDeposito;
    private AldeanoBancoRetiro aldeanoBancoRetiro;
    private AldeanoBancoConversiones aldeanoBancoConversiones;
    private AldeanosGuardarYproteger aldeanosManager;
    private MisionDiariaManager misionDiariaManager;
    private AldeanoGremio aldeanoGremio;
    private NivelManager nivelManager;
    private EquipoManager equipoManager;
    private SpawnManager spawnManager;
    private static Main plugin;
    private ClasesMain clasesMain;
    private AldeanoClases aldeanoClases;

    @Override
    public void onEnable() {
        crearBancoYml();
        new BancoListener(this);
        new ProteccionAldeanos(this);
        new AldeanoGremio(this);
        BancoManager.setPlugin(this);
        com.srjimi.Nivel.NivelGuardar.cargarArchivo();
        nivelManager = new NivelManager(this);
        scoreboardManager = new ScoreboardManager(this,nivelManager,equipoManager);
        aldeanoBancoDeposito = new AldeanoBancoDeposito(this);
        aldeanoBancoRetiro = new AldeanoBancoRetiro(this);
        aldeanoBancoConversiones = new AldeanoBancoConversiones(this);
        aldeanosManager = new AldeanosGuardarYproteger(this);
        misionDiariaManager = new MisionDiariaManager();
        misionDiariaManager.inicializar(this,nivelManager);
        aldeanoGremio = new AldeanoGremio(this);
        equipoManager = new EquipoManager(this);
        spawnManager = new SpawnManager(this);
        clasesMain = new ClasesMain(this);
        aldeanoClases = new AldeanoClases(this);
        clasesMain = new ClasesMain(this);

        // comandos

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("anuncio", new ComandosAnuncios())
        );

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("equipo", new ComandosEquipo(this,equipoManager))
        );

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("banco", new ComandosBanco(aldeanoBancoDeposito,aldeanoBancoConversiones,aldeanoBancoRetiro))
        );

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("gremio", new ComandosGremio(this))
        );

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("spawn", new ComandosSpawn(spawnManager))
        );

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("setSpawn", new ComandosSetSpawn(spawnManager))
        );

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("mercado", new ComandosMercado(this))
        );

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("monedas", new ComandosMonedas())
        );

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("clases", new ComandosClases(this))
        );

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("niveles", new ComandosNiveles(nivelManager))
        );

        //Eventos
        getServer().getPluginManager().registerEvents(new IngresoSalidaListener(this), this);
        getServer().getPluginManager().registerEvents(new AldeanoBancoDeposito(this), this);
        getServer().getPluginManager().registerEvents(new AldeanoBancoRetiro(this), this);
        getServer().getPluginManager().registerEvents(aldeanosManager, this);
        getServer().getPluginManager().registerEvents(new InteractuarConAldeano(this,aldeanoBancoDeposito,aldeanoBancoRetiro), this);
        getServer().getPluginManager().registerEvents(new ChatListener(nivelManager), this);
        getServer().getPluginManager().registerEvents(new NivelListener(this,nivelManager), this);
        getServer().getPluginManager().registerEvents(new Aldeanos(this), this);
        getServer().getPluginManager().registerEvents(new EquipoListener(equipoManager), this);
        getServer().getPluginManager().registerEvents(new ClasesListener(this), this);
        getServer().getPluginManager().registerEvents(new MuerteListener(), this);


        getLogger().info("¡JimiRPG ha sido activado!");
    }

    @Override
    public void onDisable() {
        getLogger().info("¡JimiRPG ha sido desactivado!");
        guardarBancoConfig();
    }

    public void crearBancoYml() {
        bancoFile = new File(getDataFolder(), "Banco.yml");
        if (!bancoFile.exists()) {
            saveResource("Banco.yml", false); // Solo si ya tienes un template, si no quita esta línea
        }
        bancoConfig = YamlConfiguration.loadConfiguration(bancoFile);
    }

    public FileConfiguration getBancoConfig() {
        return bancoConfig;
    }

    public void guardarBancoConfig() {
        try {
            bancoConfig.save(bancoFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }
    public MisionDiariaManager getMisionDiariaManager() {return misionDiariaManager;}
    public AldeanoGremio getAldeanoGremio(){return aldeanoGremio;}
    public EquipoManager getEquipoManager() {return equipoManager;}
    public ClasesMain getClasesMain(){return clasesMain;}
    public AldeanoClases getAldeanoClases(){return aldeanoClases;}
}
