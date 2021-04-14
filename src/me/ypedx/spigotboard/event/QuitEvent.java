package me.ypedx.spigotboard.event;

import me.ypedx.spigotboard.SpigotBoard;
import me.ypedx.spigotboard.scoreboard.ScoreboardHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    private final ScoreboardHandler boardHandler;

    public QuitEvent(SpigotBoard instance) {
        this.boardHandler = instance.getBoardHandler();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        if(boardHandler.hasBoard(player))
            boardHandler.removeBoardFromPlayer(player);
    }
}
