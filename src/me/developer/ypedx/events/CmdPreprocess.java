package me.developer.ypedx.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.developer.ypedx.SpigotBoard;
import me.developer.ypedx.utils.ScoreboardManager;
import me.developer.ypedx.utils.U;

public class CmdPreprocess extends U implements Listener {
	
	
	@EventHandler
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
		
		Player player = event.getPlayer();
		
		
		if(event.getMessage().equalsIgnoreCase("/"+SpigotBoard.instance.getConfig().getString("Settings.toggleCommand"))) {
			
			event.setCancelled(true);
			
			if(!player.hasPermission("spigotboard.toggle")) { player.sendMessage("§cYou do not have permission to use this command.");  return; }
			
			if(SpigotBoard.instance.getConfig().getStringList("Settings.disabled-worlds").contains(player.getWorld().getName())) { player.sendMessage("§cThe scoreboard is disabled in this world.");  return; }
			
			if(hide.contains(player)) {
				
				hide.remove(player);
				
				ScoreboardManager.createScoreboard(player);
				
				player.sendMessage("§cScoreboard §7is no longer hidden.");
				
				return;
			}
			
			
			hide.add(player);
			
			ScoreboardManager.disableScoreboard(player);
			
			player.sendMessage("§cScoreboard §7is now hidden.");
			
			return;
		}
		
	}

}
