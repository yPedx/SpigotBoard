package me.ypedx.spigotboard.command;

import me.ypedx.spigotboard.SpigotBoard;
import me.ypedx.spigotboard.scoreboard.ScoreboardHandler;
import me.ypedx.spigotboard.scoreboard.ScoreboardSettings;
import me.ypedx.spigotboard.util.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SpigotBoardCommand implements CommandExecutor {

    private final SpigotBoard instance;
    private final ScoreboardHandler boardHandler;

    public SpigotBoardCommand(SpigotBoard instance) {
        this.instance = instance;
        this.boardHandler = instance.getBoardHandler();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!sender.hasPermission("spigotboard.admin")) {
            sender.sendMessage(StringUtils.color("&cNo permission."));
            return false;
        }

        if(args.length == 0) {
            sender.sendMessage(StringUtils.color("&cSpigot&7Board &8» &cInvalid usage. Help: &7/spigotboard help"));
            return false;
        }

        if(args[0].equalsIgnoreCase("reload")) {
            instance.reloadConfig();
            instance.getConfigValues().updateValues();
            instance.checkPlaceholderAPI();

            ScoreboardSettings settings = instance.getNewScoreboardSettings();

            boardHandler.updateSettings(settings);
            boardHandler.updateScoreboard();

            Bukkit.getScheduler().cancelTask(instance.getScoreboardTaskID());
            instance.startScoreboardUpdateTask(settings.getUpdateInterval());

            sender.sendMessage(StringUtils.color("&cSpigot&7Board &8» &7Plugin reloaded &asuccessfully."));
            return true;
        }

        if(args[0].equalsIgnoreCase("help")) {
            String toggleCommand = instance.getConfigValues().getScoreboardToggleCommand();

            sender.sendMessage(StringUtils.color("&8&m-+-----[&c Spigot&7Board &8&m]-----+-"));
            sender.sendMessage("");
            sender.sendMessage(StringUtils.color("&c* &7/spigotboard reload"));
            sender.sendMessage(StringUtils.color("&c* §7/" + toggleCommand));
            sender.sendMessage("");
            sender.sendMessage(StringUtils.color("&8&m-+-----------------------+-"));

            return true;
        }

        sender.sendMessage(StringUtils.color("&cSpigot&7Board &8» &cInvalid usage. Help: &7/spigotboard help"));
        return false;
    }
}
