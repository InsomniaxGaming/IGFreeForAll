package info.insomniax.ffa;

import java.util.Random;

import info.insomniax.ffa.config.Configuration;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Material;

public class BukkitPlugin extends JavaPlugin{
	
	Random random = new Random();
	
	World ffaWorld;
	Permissions permissions;
	
	@Override
	public void onEnable()
	{
		this.saveDefaultConfig();
		
		if(Configuration.WORLD_NAME != null)
			ffaWorld = Bukkit.getWorld(Configuration.WORLD_NAME);
		
		this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		
		permissions = new Permissions(this);
		
		if(!permissions.setupPermissions())
			this.getLogger().warning("Failed to load permissions");
	}
	
	@Override
	public void onDisable()
	{
		this.saveConfig();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("ffa"))
		{
			if(args.length == 0)
			{
				if(sender instanceof Player)
				{
					if(ffaWorld == null)
					{
						sendMessage(sender, "No current world is set. Please call our staff an array of profanities until they do something about it.");
						return true;
					}
					if(Configuration.ONLINE_WARRIORS.contains(sender.getName()))
					{
						if(permissions.has((Player)sender, Permissions.FFAPerm.LEAVE))
						{
							// They're too much of a wanker to enjoy our endearing bloodbath. Get their sorry ass out of here
							removeWarrior(sender.getName());
							//TODO Remove player from FFA world, give him all his old shit, etc etc
							return true;
						}
					}
					else
					{
						if(permissions.has((Player)sender, Permissions.FFAPerm.JOIN))
						{
							// This player has a death wish.. GIVE THEM WHAT THEY WANT!
							addWarrior(sender.getName());
							
							sendMessage(sender, summonAListOfPlayersWithinTheRequiredVascinityAsWellAsTheirDirectionRelativeToSomeSpecifiedBloke(sender.getName()));
							sendMessage(sender, ChatColor.GOLD + "MAY THE BLOODSHED BEGIN!");
							
							this.getConfig().set("owh.ffa.players."+sender.getName(), ((Player)sender).getInventory());
							this.spawnPlayer(sender.getName());
							//TODO save sender's inv, location, and any other relevant info
							return true;
						}
					}
				}
			}
			if(args.length > 1)
			{
				if(args[0].equalsIgnoreCase("world"))
				{
						
					if(args[1].equalsIgnoreCase("set"))
					{
						if(permissions.has((Player)sender, Permissions.FFAPerm.WORLDSET))
						{
							if(args.length > 2)
							{
								//If player gave sufficient information, set world to the given argument
								sendMessage(sender, "FFA world set to " + args[2]);
								setWorld(args[2]);
							}
							else
							{
								//If there is no data other than "set", set the world to the world the player is standing in
								sendMessage(sender, "FFA world set to current world");
								setWorld(((Player)sender).getWorld().getName());
							}
							
							return true;
						}
					}
					else if(args[1].equalsIgnoreCase("get"))
					{
						if(permissions.has((Player)sender, Permissions.FFAPerm.WORLDGET))
						{
							sendMessage(sender, "Current FFA world is " + Configuration.WORLD_NAME);
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	public void setWorld(String world)
	{
		Configuration.WORLD_NAME = world;
		ffaWorld = Bukkit.getWorld(world);
	}
	
	public void addWarrior(String name)
	{
		Configuration.ONLINE_WARRIORS.add(name);
		Configuration.WARRIORS.add(name);
	}
	
	public void removeWarrior(String name)
	{
		Configuration.ONLINE_WARRIORS.remove(name);
		Configuration.WARRIORS.remove(name);
	}
	
	/**Convenience method for determining whether to use sendMessage or logger, 'cuz I'm tired of dealing with it*/
	public void sendMessage(CommandSender sender, String message)
	{
		if(sender instanceof Player)
			sender.sendMessage(message);
		else
			this.getLogger().info(message);
	}
	
	public static String summonAListOfPlayersWithinTheRequiredVascinityAsWellAsTheirDirectionRelativeToSomeSpecifiedBloke(String bloke)
	{
		String beenWatchingTooManyBritishShowsLately = "";
		
		for(String warrior : Configuration.ONLINE_WARRIORS)
		{
			if(withinDist(bloke, warrior, Configuration.RADAR_DISTANCE))
			{
				//TODO retrieve warrior's direction relative to "bloke" and include it in my oddly named string
				String direction = "[direction]";
				beenWatchingTooManyBritishShowsLately += warrior + direction + " ";
			}
		}
		
		return beenWatchingTooManyBritishShowsLately;
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
		Location loc = new Location(ffaWorld, x, 256, z);
		
		for(int y = 0; y < 255; y++)
		{
			if(loc.getBlock().getType() == Material.AIR && loc.getBlock().getRelative(0,1,0).getType() == Material.AIR && !loc.getBlock().getRelative(0, -1, 0).isLiquid() && loc.getBlock().getRelative(0, -1, 0).getType() != Material.AIR)
			{
				validY = (int)loc.getY();
				break;
			}

			//Top-down iteration to avoid cave-spawning as much as possible
			loc.add(0,-1,0);
		}
		
		return validY;
	}

}
