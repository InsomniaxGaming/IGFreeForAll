package info.insomniax.ffa;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class Permissions {
	
	public enum FFAPerm
	{
		JOIN, 		// Join FFA
		LEAVE, 		// Leave FFA
		WORLDGET,	// Get the specified FFA world
		WORLDSET	// Set the specified FFA world
	}
	
	public static String permBase = "owh.ffa.base.";
	
	private Permission permission = null;
    private static Economy economy = null;
    private static Chat chat = null;
    
    public BukkitPlugin myPlugin;
    
    public Permissions(BukkitPlugin instance)
    {
    	myPlugin = instance;
    }

    public boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> permissionProvider = myPlugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    public boolean setupChat()
    {
        RegisteredServiceProvider<Chat> chatProvider = myPlugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }

    public boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = myPlugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
    
    public boolean has(Player player, String node)
    {
    	return permission.has(player, node);
    }
    
    public boolean has(Player player, FFAPerm perm)
    {
    	return permission.has(player, permBase + perm.toString().toLowerCase());
    }

}
