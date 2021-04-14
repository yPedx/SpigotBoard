package me.ypedx.spigotboard.event;

import me.ypedx.spigotboard.SpigotBoard;
import me.ypedx.spigotboard.scoreboard.ScoreboardHandler;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class JoinEvent implements Listener {

    private final SpigotBoard instance;
    private final ScoreboardHandler boardHandler;

    public JoinEvent(SpigotBoard instance) {
        this.instance = instance;
        this.boardHandler = instance.getBoardHandler();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        String world = player.getWorld().getName();
        List<String> disabledWorlds = instance.getConfigValues().getScoreboardDisabledWorlds();

        if(player.hasPermission("spigotboard.admin"))
            if(instance.usingUpdateChecker())
                if(instance.getUpdater().updateAvailable()) {
                    TextComponent[] components = instance.getUpdater().constructNewUpdateMessage();
                    try {
                        for (TextComponent component : components)
                            player.spigot().sendMessage(ChatMessageType.CHAT, component);
                    } catch (NoClassDefFoundError | NoSuchMethodError ex) {
                        player.sendMessage(components[0].getText());
                    }
                }

        if(disabledWorlds.contains(world))
            return;

        if(!boardHandler.isHidingBoard(player))
            boardHandler.addBoardToPlayer(player);
    }
}
