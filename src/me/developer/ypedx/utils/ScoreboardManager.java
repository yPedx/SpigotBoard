package me.developer.ypedx.utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import me.developer.ypedx.SpigotBoard;
 
public class ScoreboardManager extends U {
	
	
	public static void addScoreboard() {
		
	    for (Player all : Bukkit.getOnlinePlayers()) {
	    	
	      if (hide.contains(all)) {
	    	  
	    	  disableScoreboard(all);
	    	  
	    	  return;
	      }
	      
	      if (SpigotBoard.instance.getConfig().getStringList("Settings.disabled-worlds").contains(all.getWorld().getName())) {
	    	  
	    	  disableScoreboard(all);
	    	  
	    	  return;
	      }
	      
	      createScoreboard(all);
	    }
	}
	
	
	
	public static void createScoreboard(Player player) {
		
	    Scoreboard board = Bukkit.getServer().getScoreboardManager().getNewScoreboard();

	    Objective o = board.registerNewObjective("Scoreboard", "dummy");
	    

	    o.setDisplayName(replaceValues(player, SpigotBoard.instance.getConfig().getString("Scoreboard.title")));
	    
	    o.setDisplaySlot(DisplaySlot.SIDEBAR);
	    

	    List<String> text = SpigotBoard.instance.getConfig().getStringList("Scoreboard.text");
	    
	    int size = text.size();
	    
	    String f = "";
	    
	    
	    for(String s : text) {
	    	
	      f = replaceValues(player, s);
	      
	      
	      int currentLine = size - 1;
	      
	      if ((currentLine <= 15) && (currentLine-- > 0)) {
	    	  
	        f = f + colorcodes[(currentLine--)];
	      }
	      
	      
	      o.getScore(ChatColor.translateAlternateColorCodes('&', f)).setScore(--size);
	    }
	    
	    player.setScoreboard(board);
	  }

	
	
	  public static void disableScoreboard(Player player) {
	    
	    Scoreboard board = Bukkit.getServer().getScoreboardManager().getNewScoreboard();

        board.clearSlot(DisplaySlot.SIDEBAR);
        
        player.setScoreboard(board);
	  }
	}