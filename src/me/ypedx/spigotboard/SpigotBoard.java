package me.ypedx.spigotboard;

import me.ypedx.spigotboard.command.SpigotBoardCommand;
import me.ypedx.spigotboard.event.*;
import me.ypedx.spigotboard.handler.Updater;
import me.ypedx.spigotboard.scoreboard.ScoreboardHandler;
import me.ypedx.spigotboard.scoreboard.ScoreboardSettings;
import me.ypedx.spigotboard.scoreboard.ScoreboardTask;
import me.ypedx.spigotboard.type.CustomConfig;
import me.ypedx.spigotboard.util.ConfigValues;
import me.ypedx.spigotboard.util.Placeholders;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;

import java.io.File;

public class SpigotBoard extends JavaPlugin {

    private ScoreboardHandler boardHandler;
    private ConfigValues configValues;
    private Updater updater;
    private boolean placeholderAPI;
    private Placeholders placeholders;
    private int scoreboardTaskID;
    private CustomConfig statsConfig;

    public ScoreboardHandler getBoardHandler() {
        return boardHandler;
    }

    public boolean canUsePlaceholderAPI() {
        return placeholderAPI;
    }

    public ConfigValues getConfigValues() {
        return configValues;
    }

    public Updater getUpdater() {
        return updater;
    }

    public int getScoreboardTaskID() {
        return scoreboardTaskID;
    }

    public CustomConfig getStatsConfig() {
        return statsConfig;
    }

    public boolean usingUpdateChecker() {
        return updater != null;
    }

    public Placeholders getPlaceholders() {
        return placeholders;
    }

    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        statsConfig = getNewConfig("stats.yml");
        statsConfig.saveDefaultConfig();

        configValues = new ConfigValues(this);
        placeholders = new Placeholders(this);

        checkPlaceholderAPI();

        ScoreboardSettings settings = getNewScoreboardSettings();
        boardHandler = new ScoreboardHandler(this, settings);

        if(configValues.getUpdateCheckerStatus()) {
            updater = new Updater(this);
        }

        startScoreboardUpdateTask(settings.getUpdateInterval());

        registerEvents();
        registerCommands();
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new JoinEvent(this), this);
        pm.registerEvents(new QuitEvent(this), this);
        pm.registerEvents(new CommandEvent(this), this);
        pm.registerEvents(new BlockBEvent(this), this);
        pm.registerEvents(new BlockPEvent(this), this);
        pm.registerEvents(new DeathEvent(this), this);
    }

    private void registerCommands() {
        Server s = getServer();

        s.getPluginCommand("spigotboard").setExecutor(new SpigotBoardCommand(this));
    }

    public void checkPlaceholderAPI() {
        placeholderAPI = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    public void startScoreboardUpdateTask(double interval) {
        scoreboardTaskID = new ScoreboardTask(this).runTaskTimer(this, 0, (long) (interval * 20.0)).getTaskId();
    }

    public ScoreboardSettings getNewScoreboardSettings() {

        return new ScoreboardSettings(configValues.getScoreboardDisplayName(), configValues.getScoreboardLines(),
                configValues.getScoreboardUpdateInterval(), DisplaySlot.SIDEBAR);
    }

    private CustomConfig getNewConfig(String name) {
        return new CustomConfig(this, new File(this.getDataFolder(), name));
    }
}
