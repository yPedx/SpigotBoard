package me.ypedx.spigotboard.handler;

import me.ypedx.spigotboard.SpigotBoard;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdaterTask extends BukkitRunnable {

    private final SpigotBoard instance;

    public UpdaterTask(SpigotBoard instance) {
        this.instance = instance;
    }

    @Override
    public void run() {
        instance.getUpdater().checkForUpdates();
    }
}
