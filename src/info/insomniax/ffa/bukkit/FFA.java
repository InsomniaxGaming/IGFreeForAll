package info.insomniax.ffa.bukkit;

import info.insomniax.ffa.core.InsomniaxForAll;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class FFA extends JavaPlugin{
	
	InsomniaxForAll insomniax;
	
	public void onEnable()
	{
		insomniax = new InsomniaxForAll();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("ffa"))
		{
			// This player has a death wish.. GIVE THEM WHAT THEY WANT!
		}
		
		return false;
	}
	
	public static boolean withinDist(String p1, String p2, double distance)
	{
		//Retrieve locations of given players
		Location loc1 = Bukkit.getPlayer(p1).getLocation();
		Location loc2 = Bukkit.getPlayer(p2).getLocation();
		
		//Return whether they are within the specified distance
		return loc1.distanceSquared(loc2) < Math.pow(distance, 2);
	}

}
