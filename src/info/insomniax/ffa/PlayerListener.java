package info.insomniax.ffa;

import info.insomniax.ffa.config.Configuration;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener{
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		if(Configuration.WARRIORS.contains(e.getPlayer().getName()))
		{
			Configuration.ONLINE_WARRIORS.add(e.getPlayer().getName());
		}		
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e)
	{
		if(Configuration.WARRIORS.contains(e.getPlayer().getName()))
		{
			Configuration.ONLINE_WARRIORS.remove(e.getPlayer().getName());
		}		
	}
}
