package me.ypedx.spigotboard.util;

import me.ypedx.spigotboard.SpigotBoard;
import org.bukkit.configuration.Configuration;

import java.util.List;

public class ConfigValues {

    private final SpigotBoard instance;

    private String scoreboardDisplayName, scoreboardToggleCommand, timezone, timeFormat;
    private List<String> scoreboardLines, scoreboardDisabledWorlds;
    private double scoreboardUpdateInterval;
    private boolean updateCheckerStatus, disableStats;

    public ConfigValues(SpigotBoard instance) {
        this.instance = instance;
        updateValues();
    }

    public void updateValues() {
        Configuration config = instance.getConfig();

        this.scoreboardDisplayName = config.getString("Scoreboard.title");
        this.scoreboardLines = config.getStringList("Scoreboard.text");
        this.scoreboardUpdateInterval = config.getDouble("Settings.update-interval-seconds");
        this.scoreboardToggleCommand = config.getString("Settings.toggle-command");
        this.scoreboardDisabledWorlds = config.getStringList("Settings.disabled-worlds");
        this.timezone = config.getString("Settings.timezone");
        this.timeFormat = config.getString("Settings.time-format");
        this.updateCheckerStatus = config.getBoolean("Settings.update-checker");
        this.disableStats = config.getBoolean("Settings.disable-stats");
    }

    public String getScoreboardDisplayName() {
        return scoreboardDisplayName;
    }

    public List<String> getScoreboardLines() {
        return scoreboardLines;
    }

    public double getScoreboardUpdateInterval() {
        return scoreboardUpdateInterval;
    }

    public String getScoreboardToggleCommand() {
        return scoreboardToggleCommand;
    }

    public List<String> getScoreboardDisabledWorlds() {
        return scoreboardDisabledWorlds;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public boolean getUpdateCheckerStatus() {
        return updateCheckerStatus;
    }

    public boolean getDisableStats() {
        return disableStats;
    }
}
