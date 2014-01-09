package info.insomniax.ffa.bukkit;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class Permissions {
	
	private static Permission permission = null;
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
    
    public static boolean has(Player player, String node)
    {
    	return permission.has(player, node);
    }
    
    

}
