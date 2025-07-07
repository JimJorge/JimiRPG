package com.srjimi;

import com.srjimi.Aldeanos.Aldeanos;
import com.srjimi.Aldeanos.InteractuarConAldeano;
import com.srjimi.Aldeanos.ProteccionAldeanos;
import com.srjimi.Banco.*;
import com.srjimi.Chat.ChatListener;
import com.srjimi.Comandos.*;
import com.srjimi.Gremio.AldeanoGremio;
import com.srjimi.Gremio.MisionDiariaManager;
import com.srjimi.Listeners.DamageListener;
import com.srjimi.Manager.ClaseManager;
import com.srjimi.Aldeanos.AldeanosGuardarYproteger;
import com.srjimi.Listeners.IngresoSalidaListener;
import com.srjimi.Nivel.NivelListener;
import com.srjimi.Scoreboard.ScoreboardManager;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public final class Main extends JavaPlugin implements Listener {

    public ClaseManager claseManager;
    public ScoreboardManager scoreboardManager;
    private File bancoFile;
    private FileConfiguration bancoConfig;
    private AldeanoBancoDeposito aldeanoBancoDeposito;
    private AldeanoBancoRetiro aldeanoBancoRetiro;
    private AldeanoBancoConversiones aldeanoBancoConversiones;
    BancoManager bancoManager;
    private AldeanosGuardarYproteger aldeanosManager;
    private MisionDiariaManager misionDiariaManager;
    private AldeanoGremio aldeanoGremio;
    private Aldeanos aldeanos;
    @Override
    public void onEnable() {
        crearBancoYml();
        new BancoListener(this);
        new ProteccionAldeanos(this);
        new AldeanoGremio(this);
        BancoManager.setPlugin(this);
        com.srjimi.Nivel.NivelGuardar.cargarArchivo();
        claseManager = new ClaseManager();
        scoreboardManager = new ScoreboardManager(this);
        claseManager = new ClaseManager();
        aldeanoBancoDeposito = new AldeanoBancoDeposito(this);
        aldeanoBancoRetiro = new AldeanoBancoRetiro(this);
        aldeanoBancoConversiones = new AldeanoBancoConversiones(this);
        aldeanosManager = new AldeanosGuardarYproteger(this);
        misionDiariaManager = new MisionDiariaManager();
        misionDiariaManager.inicializar(this);
        aldeanoGremio = new AldeanoGremio(this);

        // comandos
        Comandos comandos = new Comandos(claseManager);
        ComandosBanco comandosBanco = new ComandosBanco(this,aldeanoBancoDeposito,aldeanoBancoRetiro,aldeanoBancoConversiones);
        ComandosSpawn comandosSpawn = new ComandosSpawn();
        ComandosirSpawn comandosirSpawn = new ComandosirSpawn();
        ComandosGremio comandosGremio = new ComandosGremio(this);
        ComandosMercado comandosMercado = new ComandosMercado(this);

        this.getServer().getCommandMap().register("clase", new org.bukkit.command.Command("clase") {
            @Override
            public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                comandos.ejecutar(sender, args);
                return true;
            }
        });
        this.getServer().getCommandMap().register("banco", new org.bukkit.command.Command("banco") {
            @Override
            public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                comandosBanco.ejecutar(sender, args);
                return true;
            }
        });
        this.getServer().getCommandMap().register("spawn", new org.bukkit.command.Command("spawn") {
            @Override
            public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                comandosirSpawn.ejecutar(sender, args);
                return true;
            }
        });
        this.getServer().getCommandMap().register("setspawn", new org.bukkit.command.Command("setspawn") {
            @Override
            public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                comandosSpawn.ejecutar(sender, args);
                return true;
            }
        });
        this.getServer().getCommandMap().register("gremio", new org.bukkit.command.Command("gremio") {
            @Override
            public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                comandosGremio.ejecutar(sender, args);
                return true;
            }
        });
        this.getServer().getCommandMap().register("mercado", new org.bukkit.command.Command("mercado") {
            @Override
            public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                comandosMercado.ejecutar(sender, args);
                return true;
            }
        });
        //Eventos
        getServer().getPluginManager().registerEvents(new DamageListener(claseManager), this);
        getServer().getPluginManager().registerEvents(new IngresoSalidaListener(this), this);
        getServer().getPluginManager().registerEvents(new AldeanoBancoDeposito(this), this);
        getServer().getPluginManager().registerEvents(new AldeanoBancoRetiro(this), this);
        getServer().getPluginManager().registerEvents(aldeanosManager, this);
        getServer().getPluginManager().registerEvents(new InteractuarConAldeano(this,aldeanoBancoDeposito,aldeanoBancoRetiro), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new NivelListener(this), this);
        getServer().getPluginManager().registerEvents(new Aldeanos(this), this);

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
    public ClaseManager getManager() {return claseManager;}
    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }
    public AldeanosGuardarYproteger getAldeanosManager() {
        return aldeanosManager;
    }
    public MisionDiariaManager getMisionDiariaManager() {return misionDiariaManager;}
    public AldeanoGremio getAldeanoGremio(){return aldeanoGremio;}
}
