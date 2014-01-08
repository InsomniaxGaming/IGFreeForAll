package info.insomniax.ffa.bukkit;

import info.insomniax.ffa.core.FFA;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener{
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		if(FFA.WARRIORS.contains(e.getPlayer().getName()))
		{
			FFA.ONLINE_WARRIORS.add(e.getPlayer().getName());
		}		
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e)
	{
		if(FFA.WARRIORS.contains(e.getPlayer().getName()))
		{
			FFA.ONLINE_WARRIORS.remove(e.getPlayer().getName());
		}		
	}
}
