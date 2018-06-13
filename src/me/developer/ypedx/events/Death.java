package me.developer.ypedx.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.developer.ypedx.SpigotBoard;

public class Death implements Listener {
	
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		
		
		if(!(event.getEntity() instanceof Player)) {
			
			return;
		}
		
		
		if(SpigotBoard.instance.getConfig().getBoolean("Settings.disable-stats") == true) {
			
			return;
		}
		
		
		try {
			
			Player killed = event.getEntity();
			
			int deaths = SpigotBoard.instance.getStats().getInt("Stats."+killed.getUniqueId()+".deaths");
			
			
			if(!(event.getEntity().getKiller() instanceof Player) || event.getEntity().getKiller() == null){
				
				deaths++;
				
				SpigotBoard.instance.getStats().set("Stats."+killed.getUniqueId()+".deaths", deaths);
				
				
				SpigotBoard.instance.saveStats();
				
				return;
			}
			
			
			Player killer = event.getEntity().getKiller();
			
			int kills = SpigotBoard.instance.getStats().getInt("Stats."+killer.getUniqueId()+".kills");
			
			
			kills++;
			
			deaths++;
			
			
			SpigotBoard.instance.getStats().set("Stats."+killer.getUniqueId()+".kills", kills);
			
			SpigotBoard.instance.getStats().set("Stats."+killed.getUniqueId()+".deaths", deaths);
			
			
			SpigotBoard.instance.saveStats();
			
			return;
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

}
