package me.ypedx.spigotboard.event;

import me.ypedx.spigotboard.SpigotBoard;
import me.ypedx.spigotboard.scoreboard.ScoreboardHandler;
import me.ypedx.spigotboard.util.ConfigValues;
import me.ypedx.spigotboard.util.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class CommandEvent implements Listener {

    private final SpigotBoard instance;
    private final ScoreboardHandler boardHandler;

    public CommandEvent(SpigotBoard instance) {
        this.instance = instance;
        this.boardHandler = instance.getBoardHandler();
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        String world = player.getWorld().getName();
        String command = e.getMessage();
        ConfigValues configValues = instance.getConfigValues();
        String toggleCommand = configValues.getScoreboardToggleCommand();
        List<String> disabledWorlds = configValues.getScoreboardDisabledWorlds();

        if(!command.startsWith("/" + toggleCommand))
            return;

        e.setCancelled(true);

        if(!player.hasPermission("spigotboard.toggle")) {
            player.sendMessage(StringUtils.color("&cNo permission."));
            return;
        }

        if(disabledWorlds.contains(world)) {
            player.sendMessage(StringUtils.color("&cThe scoreboard has been disabled in this world."));
            return;
        }

        if(boardHandler.isHidingBoard(player))
            boardHandler.unhideBoardFromPlayer(player);
        else
            boardHandler.hideBoardFromPlayer(player);

        player.sendMessage(StringUtils.color("&7The &ascoreboard &7has been &atoggled&7."));
    }
}
