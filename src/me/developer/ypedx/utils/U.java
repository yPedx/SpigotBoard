package me.developer.ypedx.utils;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.clip.placeholderapi.PlaceholderAPI;
import me.developer.ypedx.SpigotBoard;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class U implements Listener {
	
	
	public static Economy economy = null;
    public static Permission permission = null;
    
    public static int PAPI = 1;
    public static int Vault = 1;
    public static int Essentials = 1;
    
    public static String[] colorcodes = { "&0", "&1", "&2", "&3", "&4", "&5", "&6", "&7", "&8", "&9", "&a", "&b", "&c", "&d", "&e", "&f" };
    
	public static ArrayList<Player> hide = new ArrayList<Player>();
	
	public static int isb = SpigotBoard.instance.getConfig().getInt("Settings.interval-seconds");
    
    public static String g = "N/A", b = "N/A";
    
    
    
    public static String getServerVersion() {
    	
    	return Bukkit.getServer().getClass().getPackage().getName().substring(23);
    }
    
    
    
    public static String replaceValues(Player player, String x) {
    	
		if(Vault != 0) {
			
			if(permission.hasGroupSupport() == true) {
				
				g = permission.getPrimaryGroup(player);
			}
			
		}
    		
		if(Essentials != 0) {
			
			b = String.valueOf(economy.getBalance(player));
			
		}
    	
    	
    	String m = ChatColor.translateAlternateColorCodes('&', x
	    		.replace("{Player}", player.getName())
		        .replace("{Displayname}", player.getDisplayName())
		        
		        .replace("{Online}", String.valueOf(Bukkit.getOnlinePlayers().size()))
		        .replace("{MaxPlayers}", String.valueOf(Bukkit.getMaxPlayers()))
		        
		        .replace("{Group}", g)
		        .replace("{Money}", b)
		        
		        .replace("{Time}", Placeholders.getTime())
		        .replace("{Coords}", Placeholders.getLocation(player))
		        .replace("{Flight}", String.valueOf(Placeholders.isFlying(player)))
		        .replace("{Health}", String.valueOf(player.getHealth()))
		        
		        .replace("{Kills}", String.valueOf(Placeholders.getKills(player.getUniqueId())))
		        .replace("{Deaths}", String.valueOf(Placeholders.getDeaths(player.getUniqueId())))
		        .replace("{BlocksBroken}", String.valueOf(Placeholders.getBB(player.getUniqueId())))
		        .replace("{BlocksPlaced}", String.valueOf(Placeholders.getBP(player.getUniqueId())))
		        
		        .replace("{RAM}", Placeholders.getRAM())
		        .replace("{MaxRAM}", Placeholders.getMaxRAM())
		        .replace("{ServerVersion}", Bukkit.getVersion())
		       
		        .replace("{Port}", String.valueOf(Bukkit.getPort()))
		        
		        .replace("null", "N/A"));
	    
	    
	    if(PAPI == 1) {
	    	
	    	return PlaceholderAPI.setPlaceholders(player, m);
	    	
	    } else {
	    	
	    	return m;
	    }
    }

}
