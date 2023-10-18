package me.firedragon5.islanddefender;


import me.firedragon5.islanddefender.commands.clans.ClanCommands;
import me.firedragon5.islanddefender.commands.hub.HubCommand;
import me.firedragon5.islanddefender.commands.island.IslandCommand;
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
import me.firedragon5.islanddefender.menu.ranks.RankPurchaseMenu;
import org.bukkit.plugin.java.JavaPlugin;


public final class IslandDefender extends JavaPlugin {

	ClanFolderManager clanManager;
	MineFileManager mineManager;
	ConfigManger configManager;
	RankFileManager rankFileManager;


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
		getServer().getPluginManager().registerEvents(new RankPurchaseMenu(), this);

//        register Commands
		new StaffCommand();
		new StaffChatCommand();
		new RankCommand();
		new ClanCommands();
		new MineCommand();
		new IslandDefenderCommands();
		new IslandCommand();
		new HubCommand();


//		Create a world called hub, make it a void world
//		if (Bukkit.getWorld("hub") == null) {
//			WorldCreator hubCreator = new WorldCreator("hub");
//			hubCreator.generator(new HubGenerator());
//			hubCreator.type(WorldType.FLAT);
//			hubCreator.createWorld();
//
//		}

////		Create a world with the name islandWorld. It must be a superflat world
//		if (Bukkit.getWorld("islandWorld") == null) {
//			WorldCreator islandCreator = new WorldCreator("islandWorld");
//			islandCreator.generator(new CustomIslandGenerator());
////			Stop mobs from spawning
//			islandCreator.generateStructures(false);
//
//			islandCreator.type(WorldType.FLAT);
//			islandCreator.createWorld();
//
//		}

	}


	@Override
	public void onDisable() {
		// Plugin shutdown logic

		clanManager.saveClanConfig();
		mineManager.saveMineConfig();
		configManager.saveConfig();
		rankFileManager.saveRankConfig();


	}
}
