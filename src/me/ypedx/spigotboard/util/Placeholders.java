package me.ypedx.spigotboard.util;

import me.ypedx.spigotboard.SpigotBoard;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.regex.Pattern;

public class Placeholders {

    private final ConfigValues configValues;
    private final Configuration statsConfig;
    private final Runtime runTime;
    private final int mb = 1048576;

    public Placeholders(SpigotBoard instance) {
        this.configValues = instance.getConfigValues();
        this.statsConfig = instance.getStatsConfig().getConfig();
        this.runTime = Runtime.getRuntime();
    }

    private String q(String quote) {
        return Pattern.quote(quote);
    }

    public String replacePlaceholders(Player player, String input) {

        UUID uuid = player.getUniqueId();
        Pattern pattern = Pattern.compile("(?i)");

        input = input.replaceAll(pattern + q("{player}"), player.getName())
                .replaceAll(pattern + q("{displayname}"), player.getDisplayName())

                .replaceAll(pattern + q("{online-players}"), String.valueOf(Bukkit.getOnlinePlayers().size()))
                .replaceAll(pattern + q("{max-players}"), String.valueOf(Bukkit.getMaxPlayers()))

                .replaceAll(pattern + q("{time}"), getTime())
                .replaceAll(pattern + q("{x}"), getX(player))
                .replaceAll(pattern + q("{y}"), getY(player))
                .replaceAll(pattern + q("{z}"), getZ(player))
                .replaceAll(pattern + q("{flight}"), String.valueOf(isFlying(player)))
                .replaceAll(pattern + q("{health}"), String.valueOf(player.getHealth()))

                .replaceAll(pattern + q("{kills}"), String.valueOf(getKills(uuid)))
                .replaceAll(pattern + q("{deaths}"), String.valueOf(getDeaths(uuid)))
                .replaceAll(pattern + q("{blocks-broken}"), String.valueOf(getBlocksBroken(uuid)))
                .replaceAll(pattern + q("{blocks-placed}"), String.valueOf(getBlocksPlaced(uuid)))

                .replaceAll(pattern + q("{usedram}"), getUsedRAM())
                .replaceAll(pattern + q("{maxram}"), getMaxRAM())
                .replaceAll(pattern + q("{server-version}"), Bukkit.getVersion())

                .replaceAll(pattern + q("{port}"), String.valueOf(Bukkit.getPort()));

        return input;
    }

    public String getUsedRAM() {
        return (runTime.totalMemory() - runTime.freeMemory()) / mb + " MB";
    }

    public String getMaxRAM() {
        return runTime.maxMemory() / mb + " MB";
    }

    public String getTime() {
        try {
            ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of(configValues.getTimezone()));
            return DateTimeFormatter.ofPattern(configValues.getTimeFormat()).format(zdt);
        } catch (Exception e) {
            return "N/A";
        }
    }

    public String getX(Player player) {
        return String.valueOf(player.getLocation().getBlockX());
    }

    public String getY(Player player) {
        return String.valueOf(player.getLocation().getBlockY());
    }

    public String getZ(Player player) {
        return String.valueOf(player.getLocation().getBlockZ());
    }

    public boolean isFlying(Player player) {
        return player.isFlying();
    }

    public int getKills(UUID uuid) {
        return statsConfig.getInt("Stats." + uuid + ".kills");
    }

    public int getDeaths(UUID uuid) {
        return statsConfig.getInt("Stats." + uuid + ".deaths");
    }

    public int getBlocksBroken(UUID uuid) {
        return statsConfig.getInt("Stats." + uuid + ".blocks-broken");
    }

    public int getBlocksPlaced(UUID uuid) {
        return statsConfig.getInt("Stats." + uuid + ".blocks-placed");
    }
}
