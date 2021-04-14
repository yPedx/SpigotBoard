package me.ypedx.spigotboard.scoreboard;

import me.clip.placeholderapi.PlaceholderAPI;
import me.ypedx.spigotboard.SpigotBoard;
import me.ypedx.spigotboard.util.StringUtils;
import me.ypedx.spigotboard.util.VersionHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class ScoreboardHandler {

    private final SpigotBoard instance;
    private ScoreboardSettings settings;
    private final HashSet<UUID> hiddenList;

    public ScoreboardHandler(SpigotBoard instance, ScoreboardSettings settings) {
        this.instance = instance;
        this.settings = settings;
        this.hiddenList = new HashSet<>();
    }

    public void addBoardToPlayer(Player player) {
        player.setScoreboard(getNewScoreboard(player));
    }

    public void removeBoardFromPlayer(Player player) {
        player.getScoreboard().clearSlot(settings.getDisplaySlot());
    }

    public void hideBoardFromPlayer(Player player) {
        hiddenList.add(player.getUniqueId());
        removeBoardFromPlayer(player);
    }

    public void unhideBoardFromPlayer(Player player) {
        hiddenList.remove(player.getUniqueId());
        addBoardToPlayer(player);
    }

    public boolean isHidingBoard(Player player) {
        return hiddenList.contains(player.getUniqueId());
    }

    public boolean hasBoard(Player player) {
        return player.getScoreboard().getObjective("SpigotBoard") != null;
    }

    public void updateScoreboard() {
        for (Player player : Bukkit.getOnlinePlayers()) {

            String world = player.getWorld().getName();
            List<String> disabledWorlds = instance.getConfigValues().getScoreboardDisabledWorlds();

            if(disabledWorlds.contains(world))
                if(hasBoard(player)) {
                    removeBoardFromPlayer(player);
                    continue;
                }

            if(!isHidingBoard(player) && !hasBoard(player))
                addBoardToPlayer(player);
            else
                if(hasBoard(player)) {
                    Scoreboard scoreboard = player.getScoreboard();
                    Objective objective = scoreboard.getObjective("SpigotBoard");

                    setTitle(objective, replaceAndColor(player, settings.getDisplayName()));
                    setLines(objective, player, settings.getLines());
                }
        }
    }

    public void updateSettings(ScoreboardSettings settings) {
        this.settings = settings;
    }

    private Scoreboard getNewScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("SpigotBoard", "dummy");

        registerTeams(scoreboard);
        setDisplaySlot(objective, settings.getDisplaySlot());
        setTitle(objective, replaceAndColor(player, settings.getDisplayName()));
        setLines(objective, player, settings.getLines());

        return scoreboard;
    }


    private void registerTeams(Scoreboard scoreboard) {
        for(int x = 0; x < 15; x++) {
            String uniqueIdentifier = ChatColor.values()[x].toString();
            Team t = scoreboard.registerNewTeam(String.valueOf(x));

            t.addEntry(uniqueIdentifier);
        }
    }

    private void setTitle(Objective objective, String title) {
        int maxTitleLength = VersionHandler.getMaxTitleLength();
        title = title.substring(0, Math.min(title.length(), maxTitleLength));
        objective.setDisplayName(title);
    }

    private void setLines(Objective objective, Player player, List<String> lines) {
        Scoreboard scoreboard = objective.getScoreboard();
        int score = 15;

        for(int x = 0; x < 15; x++) {
            Team t = scoreboard.getTeam(String.valueOf(x));

            if(x >= lines.size()) {
                for(String entry : t.getEntries())
                    scoreboard.resetScores(entry);
                continue;
            }

            String uniqueIdentifier = ChatColor.values()[x].toString();
            String line = replaceAndColor(player, lines.get(x));
            String[] splitLines = splitString(line);
            String prefix = splitLines[0], suffix = splitLines[1];

            t.setPrefix(prefix);
            t.setSuffix(suffix);
            objective.getScore(uniqueIdentifier).setScore(--score);
        }
    }

    private String[] splitString(String input) {
        int maxFixLength = VersionHandler.getMaxFixLength(), len = input.length();
        String prefix = input.substring(0, Math.min(len, maxFixLength)), suffix = "";
        String lastPrefixColors = ChatColor.getLastColors(prefix);

        if(len > maxFixLength) {
            suffix = (lastPrefixColors.isEmpty() ? ChatColor.WHITE.toString() : lastPrefixColors);
            suffix += input.substring(maxFixLength, Math.min(len, ((maxFixLength * 2) - suffix.length())));
        }

        return new String[] { prefix, suffix };
    }

    private void setDisplaySlot(Objective objective, DisplaySlot displaySlot) {
        objective.setDisplaySlot(displaySlot);
    }

    private String replaceAndColor(Player player, String input) {
        if(instance.canUsePlaceholderAPI())
            input = PlaceholderAPI.setPlaceholders(player, input);

        input = instance.getPlaceholders().replacePlaceholders(player, input);

        return StringUtils.color(input);
    }
}
