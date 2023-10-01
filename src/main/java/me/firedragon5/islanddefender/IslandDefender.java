package me.firedragon5.islanddefender;


import me.firedragon5.islanddefender.commands.clans.ClanCommands;
import me.firedragon5.islanddefender.events.ChatEvent;
import me.firedragon5.islanddefender.events.JoinEvent;
import me.firedragon5.islanddefender.filemanager.clans.ClanFolderManager;
import me.firedragon5.islanddefender.menu.clan.ClanInfoMenu;
import org.bukkit.plugin.java.JavaPlugin;


public final class IslandDefender extends JavaPlugin {

	ClanFolderManager clanManager;

	@Override
	public void onEnable() {
		// Plugin startup logic

		clanManager = ClanFolderManager.getFileManager();
		clanManager.setup();
		clanManager.loadClanConfig();
		clanManager.checkClanConfig();


//        register Events
		getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		getServer().getPluginManager().registerEvents(new Utils(), this);
		getServer().getPluginManager().registerEvents(new ClanInfoMenu(), this);
		getServer().getPluginManager().registerEvents(new ChatEvent(), this);


//        register Commands
		getCommand("clan").setExecutor(new ClanCommands());

	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic

		clanManager.saveClanConfig();

	}
}
