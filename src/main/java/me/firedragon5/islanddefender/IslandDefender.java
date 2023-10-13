package me.firedragon5.islanddefender;


import me.firedragon5.islanddefender.commands.clans.ClanCommands;
import me.firedragon5.islanddefender.commands.mines.MineCommand;
import me.firedragon5.islanddefender.commands.staff.StaffChatCommand;
import me.firedragon5.islanddefender.commands.staff.StaffCommand;
import me.firedragon5.islanddefender.events.ChatEvent;
import me.firedragon5.islanddefender.events.JoinEvent;
import me.firedragon5.islanddefender.events.LeaveEvent;
import me.firedragon5.islanddefender.filemanager.clans.ClanFolderManager;
import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
import me.firedragon5.islanddefender.filemanager.mines.MineFileManager;
import me.firedragon5.islanddefender.menu.clan.ClanInfoMenu;
import me.firedragon5.islanddefender.menu.mines.MineMenu;
import me.firedragon5.islanddefender.menu.mines.MinePurchaseMenu;
import org.bukkit.plugin.java.JavaPlugin;


public final class IslandDefender extends JavaPlugin {

	ClanFolderManager clanManager;
	MineFileManager mineManager;

	ConfigManger configManager;

	@Override
	public void onEnable() {
		// Plugin startup logic

//		ClanManager
		clanManager = ClanFolderManager.getFileManager();
		clanManager.setup();
		clanManager.loadClanConfig();
		clanManager.checkClanConfig();

//		MineManager
		mineManager = MineFileManager.getFileManager();
		mineManager.setup();
		mineManager.loadMineConfig();

//		ConfigManager
		configManager = ConfigManger.getFileManager();
		configManager.setup();
		configManager.loadConfigFile();
		configManager.checkConfig();


//        register Events
		getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		getServer().getPluginManager().registerEvents(new Utils(), this);
		getServer().getPluginManager().registerEvents(new ClanInfoMenu(), this);
		getServer().getPluginManager().registerEvents(new ChatEvent(), this);
		getServer().getPluginManager().registerEvents(new LeaveEvent(), this);
		getServer().getPluginManager().registerEvents(new MineMenu(), this);
		getServer().getPluginManager().registerEvents(new MinePurchaseMenu(), this);


//        register Commands
		getCommand("clan").setExecutor(new ClanCommands());
		getCommand("staffchat").setExecutor(new StaffChatCommand());
		getCommand("staff").setExecutor(new StaffCommand());
		getCommand("mine").setExecutor(new MineCommand());
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic

		clanManager.saveClanConfig();

	}
}
