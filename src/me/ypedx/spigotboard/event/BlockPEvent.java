package me.ypedx.spigotboard.event;

import me.ypedx.spigotboard.SpigotBoard;
import me.ypedx.spigotboard.type.CustomConfig;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPEvent implements Listener {

    private final SpigotBoard instance;
    private final CustomConfig statsConfig;

    public BlockPEvent(SpigotBoard instance) {
        this.instance = instance;
        this.statsConfig = instance.getStatsConfig();
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(instance.getConfigValues().getDisableStats())
            return;

        Player player = event.getPlayer();
        Configuration config = statsConfig.getConfig();
        String path = "Stats." + player.getUniqueId() + ".blocks-placed";
        int prevCount = config.getInt(path);

        config.set(path, ++prevCount);
        statsConfig.saveConfig();
    }
}
