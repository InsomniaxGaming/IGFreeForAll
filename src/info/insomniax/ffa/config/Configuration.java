package info.insomniax.ffa.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;

public class Configuration {
	
	public static String WORLD_NAME; // Name of the world to throw newbz' unsuspecting arses in
	
	public static int SPAWN_DISTANCE; // Max distance away from a player newcomers can spawn
	public static int RADAR_DISTANCE; // Max distance away a player can be to report to newcomers
	
	public static List<String> ONLINE_WARRIORS = new ArrayList<String>(); // Names of all currently online and in battle players
	public static List<String> WARRIORS = new ArrayList<String>(); // Names of all players in battle
	
	private static String pathBase = "owh.ffa.";
	private static String invPath = pathBase + "inventories";
	
	public static void getSettings(FileConfiguration config)
	{
		WORLD_NAME = config.getString(pathBase + "world", null);
		SPAWN_DISTANCE = config.getInt(pathBase + "spawndistance", 100);
		RADAR_DISTANCE = config.getInt(pathBase + "radardistance", 200);
		
		if(config.isList(pathBase + "warriors"))
			WARRIORS = config.getStringList(pathBase + "warriors");
	}
	
	public static void saveInv(FileConfiguration config, Inventory inv)
	{
		
	}

}
