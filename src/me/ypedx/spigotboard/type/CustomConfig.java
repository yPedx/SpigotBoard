package me.ypedx.spigotboard.type;

import me.ypedx.spigotboard.SpigotBoard;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.logging.Logger;

public class CustomConfig {

    private final SpigotBoard instance;
    private final File source;
    private final FileConfiguration config;
    private final Logger logger;

    public CustomConfig(SpigotBoard instance, File source) {
        this.instance = instance;
        this.source = source;
        this.config = new YamlConfiguration();
        this.logger = instance.getLogger();
    }

    public Configuration getConfig() {
        return this.config;
    }

    public void saveDefaultConfig() {
        try {
            if(!source.exists()) {
                source.getParentFile().mkdirs();
                instance.saveResource(source.getName(), false);
            }

            reloadConfig();
        } catch (Exception e) {
            logger.severe("Could not save default configuration file: " + source.getName());
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        try {
            config.load(source);
        } catch (Exception e) {
            logger.severe("Could not reload configuration file: " + source.getName());
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            config.save(source);
        } catch (Exception e) {
            logger.severe("Could not save configuration file: " + source.getName());
            e.printStackTrace();
        }
    }
}
