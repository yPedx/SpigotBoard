package me.ypedx.spigotboard.util;

import org.bukkit.ChatColor;

public class StringUtils {

    private StringUtils() { }

    public static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
