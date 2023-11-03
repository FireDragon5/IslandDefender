package me.firedragon5.islanddefender;


import me.firedragon5.islanddefender.commands.clans.adminCommands.AdminClanCommands;
import me.firedragon5.islanddefender.commands.clans.clanCommands.ClanCommands;
import me.firedragon5.islanddefender.commands.friends.FriendCommand;
import me.firedragon5.islanddefender.commands.hub.HubCommand;
import me.firedragon5.islanddefender.commands.island.IslandCommand;
import me.firedragon5.islanddefender.commands.islanddefender.IslandDefenderCommands;
import me.firedragon5.islanddefender.commands.kits.KitCommand;
import me.firedragon5.islanddefender.commands.mines.MineCommand;
import me.firedragon5.islanddefender.commands.mines.MineRegionsCommand;
import me.firedragon5.islanddefender.commands.money.BalanceCommand;
import me.firedragon5.islanddefender.commands.money.CoinCommand;
import me.firedragon5.islanddefender.commands.ranks.RankCommand;
import me.firedragon5.islanddefender.commands.shop.SellCommand;
import me.firedragon5.islanddefender.commands.shop.ShopCommands;
import me.firedragon5.islanddefender.commands.staff.AdminCommand;
import me.firedragon5.islanddefender.commands.staff.StaffCommand;
import me.firedragon5.islanddefender.commands.trade.TradeCommand;
import me.firedragon5.islanddefender.events.ChatEvent;
import me.firedragon5.islanddefender.events.JoinEvent;
import me.firedragon5.islanddefender.events.QuitEvent;
import me.firedragon5.islanddefender.filemanager.clans.ClanFolderManager;
import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
import me.firedragon5.islanddefender.filemanager.kits.KitsFileManger;
import me.firedragon5.islanddefender.filemanager.mines.MineFileManager;
import me.firedragon5.islanddefender.filemanager.ranks.RankFileManager;
import me.firedragon5.islanddefender.filemanager.shop.SellFileManager;
import me.firedragon5.islanddefender.menu.clan.ClanInfoMenu;
import me.firedragon5.islanddefender.menu.friends.FriendsMenu;
import me.firedragon5.islanddefender.menu.kits.KitsMenu;
import me.firedragon5.islanddefender.menu.mines.MineMenu;
import me.firedragon5.islanddefender.menu.mines.MinePurchaseMenu;
import me.firedragon5.islanddefender.menu.ranks.RankMenu;
import me.firedragon5.islanddefender.menu.ranks.RankPurchaseMenu;
import me.firedragon5.islanddefender.menu.shop.SellMenu;
import me.firedragon5.islanddefender.task.MinesTask;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;


public final class IslandDefender extends JavaPlugin {

	ClanFolderManager clanManager;
	MineFileManager mineManager;
	ConfigManger configManager;
	RankFileManager rankFileManager;
	SellFileManager sellFileManager;

	KitsFileManger kitsFileManger;


	//	This is a hashmap for all the pending friend requests
	public static HashMap<Player, Player> pendingFriendRequests = new HashMap<>();

	//	instance
	private static IslandDefender instance;

	//	task
	private BukkitTask task;


	@Override
	public void onEnable() {
		// Plugin startup logic

		LuckPerms luckPerms = LuckPermsProvider.get();
		for (Player player : Bukkit.getOnlinePlayers()) {
			luckPerms.getUserManager().loadUser(player.getUniqueId());
		}
		instance = this;


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

//		SellManager
		sellFileManager = SellFileManager.getInstance();
		sellFileManager.setup();
		sellFileManager.loadSellConfig();

//		KitsManager
		kitsFileManger = KitsFileManger.getFileManager();
		kitsFileManger.setup();
		kitsFileManger.load();

//        register Events
		getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		getServer().getPluginManager().registerEvents(new Utils(), this);
		getServer().getPluginManager().registerEvents(new ClanInfoMenu(), this);
		getServer().getPluginManager().registerEvents(new ChatEvent(), this);
		getServer().getPluginManager().registerEvents(new QuitEvent(), this);
		getServer().getPluginManager().registerEvents(new MineMenu(), this);
		getServer().getPluginManager().registerEvents(new MinePurchaseMenu(), this);
		getServer().getPluginManager().registerEvents(new RankMenu(), this);
		getServer().getPluginManager().registerEvents(new RankPurchaseMenu(), this);
		getServer().getPluginManager().registerEvents(new FriendsMenu(), this);
		getServer().getPluginManager().registerEvents(new SellMenu(), this);
		getServer().getPluginManager().registerEvents(new KitsMenu(), this);


//        register Commands
		new StaffCommand();
		new RankCommand();
		new ClanCommands();
		new MineCommand();
		new IslandDefenderCommands();
		new IslandCommand();
		new HubCommand();
		new MineRegionsCommand();
		new FriendCommand();
		new SellCommand();
		new ShopCommands();
		new BalanceCommand();
		new CoinCommand();
		new KitCommand();
		new AdminClanCommands();
		new TradeCommand();
		new AdminCommand();


//		task
		task = new MinesTask().runTaskTimer(this, 0, MineFileManager.getFileManager().getResetTicksInMin());


	}


	@Override
	public void onDisable() {
		// Plugin shutdown logic

		clanManager.saveClanConfig();
		mineManager.saveMineConfig();
		configManager.saveConfig();
		rankFileManager.saveRankConfig();
		sellFileManager.saveSellConfig();
		kitsFileManger.save();

//		Stop the task
		if (task != null && !task.isCancelled()) {
			task.cancel();
		}


	}

	public static IslandDefender getInstance() {
		return instance;
	}


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
