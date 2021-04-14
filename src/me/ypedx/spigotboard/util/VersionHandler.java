package me.ypedx.spigotboard.util;

import org.bukkit.Bukkit;

public class VersionHandler {

    private VersionHandler() { }

    /*public static int getMaxEntryLength() {
        String version = Bukkit.getBukkitVersion();
        int max = 16;

        if(version.contains("1.8") || version.contains("1.9") || version.contains("1.10") ||
                version.contains("1.11") || version.contains("1.12") || version.contains("1.13") ||
                version.contains("1.14") || version.contains("1.15"))
            max = 38;

        return max;
    }*/

    public static int getMaxFixLength() {
        return colorsWillDisappear() ? 64 : 16;
    }

    public static int getMaxTitleLength() {
        return colorsWillDisappear() ? 128 : 32;
    }

    public static boolean colorsWillDisappear() {
        String[] version = Bukkit.getBukkitVersion().split("\\.");
        int v = Integer.parseInt(version[0] + version[1]);
        return v >= 113;
    }

    // Lengths found from testing

    // 1.7
    // Title: 32
    // Prefix/Suffix: 16
    // Entry: 16

    // 1.8 - 1.12
    // Title: 32
    // Prefix/Suffix: 16
    // Entry: 40

    // 1.13 - 1.15
    // Title: 128
    // Prefix/Suffix: 64
    // Entry: 40
}
