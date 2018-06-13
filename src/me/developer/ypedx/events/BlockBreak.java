package me.developer.ypedx.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.developer.ypedx.SpigotBoard;

public class BlockBreak implements Listener {
	
	
	@EventHandler
	public void bB(BlockBreakEvent event) {
		
		Player player = event.getPlayer();
		
		
		if(SpigotBoard.instance.getConfig().getBoolean("Settings.disable-stats") == true) {
			
			return;
		}
		
		
		try {
		
			int bb =  SpigotBoard.instance.getStats().getInt("Stats."+player.getUniqueId()+".blocks-broken");
			
			bb++;
			
			SpigotBoard.instance.getStats().set("Stats."+player.getUniqueId()+".blocks-broken", bb);
			
			SpigotBoard.instance.saveStats();
		
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

}
