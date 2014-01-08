package info.insomniax.ffa.bukkit;

import java.util.Random;

import info.insomniax.ffa.core.FFA;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Material;

public class BukkitPlugin extends JavaPlugin{
	
	Random random = new Random();
	
	World ffaWorld;
	
	public void onEnable()
	{
		ffaWorld = Bukkit.getWorld(FFA.WORLD_NAME);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("ffa"))
		{
			if(FFA.WARRIORS.contains(sender.getName()))
			{
				// Remove player from FFA world, give him all his old shit, etc etc
			}
			else
			{
				// This player has a death wish.. GIVE THEM WHAT THEY WANT!
			}
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
	
	public void spawnPlayer(String player)
	{
		//Get location of a random player
		Location loc = ffaWorld.getPlayers().get(random.nextInt(ffaWorld.getPlayers().size())).getLocation();
		
		//Create some x and z offsets
		int x = random.nextInt(100);
		int z = random.nextInt(100);
		
		int validY;
		
		//Change x and z until a suitable Y location can be found
		while((validY = findValidY(loc.getX()+x,loc.getZ()+z)) == -1)
		{
			x = random.nextInt(100);
			z = random.nextInt(100);			
		}
		
		//By this point, we should have a location to send our player to

		Location tpLoc = new Location(ffaWorld, loc.getX()+x, validY, loc.getZ()+z);
		Bukkit.getPlayer(player).teleport(tpLoc);
		
	}
	
	/**
	 * Find the lowest valid Y coordinate
	 * @return first valid Y coordinate, or -1 if none was found
	 * */
	public int findValidY(double x, double z)
	{
		int validY = -1;
		Location loc = new Location(ffaWorld, x, 0, z);
		
		for(int y = 0; y < 255; y++)
		{
			if(loc.getBlock().getType() == Material.AIR && loc.getBlock().getRelative(0,1,0).getType() == Material.AIR)
			{
				validY = (int)loc.getY();
				break;
			}
			loc.add(0,1,0);
		}
		
		return validY;
	}

}
