package me.ypedx.spigotboard.scoreboard;

import me.ypedx.spigotboard.SpigotBoard;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoreboardTask extends BukkitRunnable {

    private final ScoreboardHandler boardHandler;

    public ScoreboardTask(SpigotBoard instance) {
        this.boardHandler = instance.getBoardHandler();
    }

    @Override
    public void run() {
        boardHandler.updateScoreboard();
    }
}
