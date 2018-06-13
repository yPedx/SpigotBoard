package me.developer.ypedx.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import me.developer.ypedx.SpigotBoard;

public class BlockPlace implements Listener {
	
	
	@EventHandler
	public void bB(BlockPlaceEvent event) {
		
		Player player = event.getPlayer();
		
		
		if(SpigotBoard.instance.getConfig().getBoolean("Settings.disable-stats") == true) {
			
			return;
		}
		
		
		try {
		
			int bp =  SpigotBoard.instance.getStats().getInt("Stats."+player.getUniqueId()+".blocks-placed");
			
			bp++;
			
			SpigotBoard.instance.getStats().set("Stats."+player.getUniqueId()+".blocks-placed", bp);
			
			SpigotBoard.instance.saveStats();
		
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

}
