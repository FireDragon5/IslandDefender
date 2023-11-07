package me.firedragon5.islanddefender;


import me.firedragon5.islanddefender.board.Board;
import me.firedragon5.islanddefender.commands.clans.clanCommands.ClanCommands;
import me.firedragon5.islanddefender.commands.friends.FriendCommand;
import me.firedragon5.islanddefender.commands.hub.HubCommand;
import me.firedragon5.islanddefender.commands.island.IslandCommand;
import me.firedragon5.islanddefender.commands.kits.KitCommand;
import me.firedragon5.islanddefender.commands.mines.MineCommand;
import me.firedragon5.islanddefender.commands.mines.MineRegionsCommand;
import me.firedragon5.islanddefender.commands.money.BalanceCommand;
import me.firedragon5.islanddefender.commands.money.CoinCommand;
import me.firedragon5.islanddefender.commands.playerStatsCommand.PlayerStatsCommand;
import me.firedragon5.islanddefender.commands.ranks.RankCommand;
import me.firedragon5.islanddefender.commands.shop.SellCommand;
import me.firedragon5.islanddefender.commands.shop.ShopCommands;
import me.firedragon5.islanddefender.commands.staff.InvSeeCommand;
import me.firedragon5.islanddefender.commands.staff.MuteCommand;
import me.firedragon5.islanddefender.commands.staff.StaffCommand;
import me.firedragon5.islanddefender.commands.staff.VanishCommand;
import me.firedragon5.islanddefender.commands.staff.admin.AdminCommand;
import me.firedragon5.islanddefender.commands.trade.TradeCommand;
import me.firedragon5.islanddefender.events.ChatEvent;
import me.firedragon5.islanddefender.events.CosmeticListener;
import me.firedragon5.islanddefender.events.JoinEvent;
import me.firedragon5.islanddefender.events.QuitEvent;
import me.firedragon5.islanddefender.filemanager.clans.ClanFolderManager;
import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
import me.firedragon5.islanddefender.filemanager.kits.KitsFileManager;
import me.firedragon5.islanddefender.filemanager.mines.MineFileManager;
import me.firedragon5.islanddefender.filemanager.ranks.RankFileManager;
import me.firedragon5.islanddefender.filemanager.shop.SellFileManager;
import me.firedragon5.islanddefender.generator.IslandGenerator;
import me.firedragon5.islanddefender.instance.Cosmetic;
import me.firedragon5.islanddefender.menu.Invsee.InvSeeMenu;
import me.firedragon5.islanddefender.menu.clan.ClanInfoMenu;
import me.firedragon5.islanddefender.menu.friends.FriendsMenu;
import me.firedragon5.islanddefender.menu.kits.KitsMenu;
import me.firedragon5.islanddefender.menu.mines.MineMenu;
import me.firedragon5.islanddefender.menu.mines.MinePurchaseMenu;
import me.firedragon5.islanddefender.menu.playerStats.PlayerStatsMenu;
import me.firedragon5.islanddefender.menu.ranks.RankMenu;
import me.firedragon5.islanddefender.menu.ranks.RankPurchaseMenu;
import me.firedragon5.islanddefender.menu.shop.SellMenu;
import me.firedragon5.islanddefender.task.MinesTask;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;


public final class IslandDefender extends JavaPlugin {

	ClanFolderManager clanManager;
	MineFileManager mineManager;
	ConfigManger configManager;
	RankFileManager rankFileManager;
	SellFileManager sellFileManager;
	KitsFileManager kitsFileManger;


	//	This is a hashmap for all the pending friend requests
	public static HashMap<Player, Player> pendingFriendRequests = new HashMap<>();
	public static boolean isChatMuted = false;
	public final Set<UUID> vanished = new HashSet<>();

	private final HashMap<UUID, List<Cosmetic>> activeCosmetics = new HashMap<>();


	//	instance
	private static IslandDefender instance;

	//	task
	private BukkitTask task;
	private BukkitTask boardTask;



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
		sellFileManager = SellFileManager.getFileInstance();
		sellFileManager.setup();
		sellFileManager.loadSellConfig();

//		KitsManager
		kitsFileManger = KitsFileManager.getFileManager();
		kitsFileManger.setup();
		kitsFileManger.load();


//        register Events
		getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		getServer().getPluginManager().registerEvents(new Utils(), this);
		getServer().getPluginManager().registerEvents(new ClanInfoMenu(), this);
		getServer().getPluginManager().registerEvents(new ChatEvent(), this);
		getServer().getPluginManager().registerEvents(new QuitEvent(this), this);
		getServer().getPluginManager().registerEvents(new MineMenu(), this);
		getServer().getPluginManager().registerEvents(new MinePurchaseMenu(), this);
		getServer().getPluginManager().registerEvents(new RankMenu(), this);
		getServer().getPluginManager().registerEvents(new RankPurchaseMenu(), this);
		getServer().getPluginManager().registerEvents(new FriendsMenu(), this);
		getServer().getPluginManager().registerEvents(new SellMenu(), this);
		getServer().getPluginManager().registerEvents(new KitsMenu(), this);
		getServer().getPluginManager().registerEvents(new InvSeeMenu(), this);
		getServer().getPluginManager().registerEvents(new PlayerStatsMenu(), this);
		getServer().getPluginManager().registerEvents(new CosmeticListener(), this);


//        register Commands
		new StaffCommand();
		new RankCommand();
		new ClanCommands();
		new MineCommand();
		new IslandCommand();
		new HubCommand();
		new MineRegionsCommand();
		new FriendCommand();
		new SellCommand();
		new ShopCommands();
		new BalanceCommand();
		new CoinCommand();
		new KitCommand();
		new TradeCommand();
		new AdminCommand();
		new PlayerStatsCommand();
		new InvSeeCommand();
		new MuteCommand();
		new VanishCommand();




//		task
		task = getServer().getScheduler().runTaskTimer(this, MinesTask.getInstance(), 0,
				MineFileManager.getFileManager().getResetTicksInMin());
		boardTask = getServer().getScheduler().runTaskTimer(this, Board.getInstance(), 0, 20);

//		Create a world with the name islandWorld. It must be a superflat world
		if (Bukkit.getWorld("islandWorld") == null) {
			WorldCreator islandCreator = new WorldCreator("islandWorld");
			islandCreator.generator(new IslandGenerator());
//			Stop mobs from spawning
			islandCreator.generateStructures(false);
//			remove all mobs like slimes and animals
			islandCreator.createWorld().getEntities().forEach(Entity::remove);
			islandCreator.createWorld();

		}

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

		if (boardTask != null && !boardTask.isCancelled()) {
			boardTask.cancel();
		}


	}

	public static IslandDefender getInstance() {
		return instance;
	}

	public HashMap<UUID, List<Cosmetic>> getActiveCosmetics() {
		return activeCosmetics;
	}





}
