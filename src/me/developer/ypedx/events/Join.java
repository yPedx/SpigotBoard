package me.developer.ypedx.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.developer.ypedx.SpigotBoard;
import me.developer.ypedx.utils.U;
import me.developer.ypedx.utils.Updater;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Join extends U implements Listener {
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		
		
		if(SpigotBoard.instance.getConfig().getBoolean("Settings.update-reminder") == true) {
			
			if(player.isOp()) {
				
				if(!SpigotBoard.instance.getDescription().getVersion().equals(Updater.getLatestVersion())) {
					
					if(getServerVersion().equalsIgnoreCase("v1_8_R1") || getServerVersion().equalsIgnoreCase("v1_8_R3")) {
						
						System.out.println("[SpigotBoard] Server version detected (1.8.x). Not compatible with clickable message in chat.");
					    	
						player.sendMessage("§cSpigot§7Board §8» §7An update was found! Newest version: "+Updater.getLatestVersion());
				        
			            player.sendMessage("Download: "+Updater.getResourceURL());
						
						return;
						
					} else {
						
						player.sendMessage("§cSpigot§7Board §8» §7An update was found! Newest version: "+Updater.getLatestVersion());
						
						
						TextComponent s = new TextComponent("Spigot");
						
						s.setColor(ChatColor.RED);
						
						
						TextComponent b = new TextComponent("Board");
						
						b.setColor(ChatColor.GRAY);
						
						
						TextComponent c = new TextComponent(" » ");
						
						c.setColor(ChatColor.DARK_GRAY);
						
						
						TextComponent m = new TextComponent("Click here for download link.");
						
						m.setColor(ChatColor.RED);
						
						m.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, Updater.getResourceURL()));
						
						m.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Click to open download link.").create()));
						
						
						s.addExtra(b);
						
						s.addExtra(c);
						
						s.addExtra(m);
						
						
						player.spigot().sendMessage(s);
						
						return;
					}
					
				} else {
					
					player.sendMessage("§cSpigot§7Board §8» §7No new updates found.");
					
					return;
				}
				
			}
			
		} else {
			
			return;
		}
		
		
		try {
		
		if(!SpigotBoard.instance.getStats().contains("Stats."+player.getUniqueId())) {
			
			SpigotBoard.instance.getStats().set("Stats."+player.getUniqueId()+".player-name", player.getName());
			SpigotBoard.instance.getStats().set("Stats."+player.getUniqueId()+".kills", 0);
			SpigotBoard.instance.getStats().set("Stats."+player.getUniqueId()+".deaths", 0);
			SpigotBoard.instance.getStats().set("Stats."+player.getUniqueId()+".blocks-broken", 0);
			SpigotBoard.instance.getStats().set("Stats."+player.getUniqueId()+".blocks-placed", 0);
			
		} else {
			
			SpigotBoard.instance.getStats().set("Stats."+player.getUniqueId()+".player-name", player.getName());
		}
		
		SpigotBoard.instance.saveStats();
		
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

}
