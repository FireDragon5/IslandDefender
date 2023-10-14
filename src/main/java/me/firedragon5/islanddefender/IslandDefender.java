package me.firedragon5.islanddefender;


import me.firedragon5.islanddefender.commands.clans.ClanCommands;
import me.firedragon5.islanddefender.commands.islanddefender.IslandDefenderCommands;
import me.firedragon5.islanddefender.commands.mines.MineCommand;
import me.firedragon5.islanddefender.commands.ranks.RankCommand;
import me.firedragon5.islanddefender.commands.staff.StaffChatCommand;
import me.firedragon5.islanddefender.commands.staff.StaffCommand;
import me.firedragon5.islanddefender.events.ChatEvent;
import me.firedragon5.islanddefender.events.JoinEvent;
import me.firedragon5.islanddefender.events.LeaveEvent;
import me.firedragon5.islanddefender.filemanager.clans.ClanFolderManager;
import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
import me.firedragon5.islanddefender.filemanager.mines.MineFileManager;
import me.firedragon5.islanddefender.filemanager.ranks.RankFileManager;
import me.firedragon5.islanddefender.menu.clan.ClanInfoMenu;
import me.firedragon5.islanddefender.menu.mines.MineMenu;
import me.firedragon5.islanddefender.menu.mines.MinePurchaseMenu;
import me.firedragon5.islanddefender.menu.ranks.RankMenu;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


public final class IslandDefender extends JavaPlugin {

	ClanFolderManager clanManager;
	MineFileManager mineManager;
	ConfigManger configManager;
	RankFileManager rankFileManager;

	private static Economy econ = null;
	private static final Permission perms = null;


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

//		RankManager
		rankFileManager = RankFileManager.getFileManager();
		rankFileManager.setup();
		rankFileManager.loadRankConfig();


//        register Events
		getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		getServer().getPluginManager().registerEvents(new Utils(), this);
		getServer().getPluginManager().registerEvents(new ClanInfoMenu(), this);
		getServer().getPluginManager().registerEvents(new ChatEvent(), this);
		getServer().getPluginManager().registerEvents(new LeaveEvent(), this);
		getServer().getPluginManager().registerEvents(new MineMenu(), this);
		getServer().getPluginManager().registerEvents(new MinePurchaseMenu(), this);
		getServer().getPluginManager().registerEvents(new RankMenu(), this);


//        register Commands
		getCommand("clan").setExecutor(new ClanCommands());
		getCommand("staffchat").setExecutor(new StaffChatCommand());
		getCommand("staff").setExecutor(new StaffCommand());
		getCommand("mine").setExecutor(new MineCommand());
		getCommand("islanddefender").setExecutor(new IslandDefenderCommands());
		getCommand("rank").setExecutor(new RankCommand());


		if (!setupEconomy()) {
			getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
		}
	}


	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return true;
	}

	public static Economy getEconomy() {
		return econ;
	}

	public static Permission getPermissions() {
		return perms;
	}


	@Override
	public void onDisable() {
		// Plugin shutdown logic

		clanManager.saveClanConfig();

	}
}
