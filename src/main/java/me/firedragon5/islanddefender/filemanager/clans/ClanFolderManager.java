package me.firedragon5.islanddefender.filemanager.clans;

import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ClanFolderManager {


//	Folder structure
//	plugins/IslandDefender/clans/<clanname>.yml
//	plugins/IslandDefender/clans.yml

	static ClanFolderManager fileManager = new ClanFolderManager();
	File clanFolder;
	File clanFile;
	FileConfiguration clanConfig;

	public static ClanFolderManager getFileManager() {
		return fileManager;
	}


	public FileConfiguration getClanConfig() {
		return clanConfig;
	}

	//	Save
	public void saveClanConfig() {
		try {
			clanConfig.save(clanFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//	Reload
	public void reloadClanConfig() {
		clanConfig = YamlConfiguration.loadConfiguration(clanFile);
	}


	public void setup() {

		clanFile = new File("plugins/IslandDefender/clans.yml");

		if (!clanFile.exists()) {
			try {
				clanFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		clanConfig = YamlConfiguration.loadConfiguration(clanFile);


	}


	//	Load the clans.yml file with the following default values
	public void loadClanConfig() {

		checkClanConfig();
		clanConfig.options().copyDefaults(true);
		saveClanConfig();
	}

	//	Check if the yml file has all the correct values
	public void checkClanConfig() {

		//		List of black list names and tags
		List<String> blacklistedClanNames = Arrays.asList("clan", "clans");

		if (clanConfig.getString("max-clans") == null) {
			clanConfig.set("max-clans", 5);
		}

		if (clanConfig.getString("max-members") == null) {
			clanConfig.set("max-members", 10);
		}

		if (clanConfig.getString("rank-to-create") == null) {
			clanConfig.set("rank-to-create", "default");
		}

		if (clanConfig.getStringList("blacklisted-clan-names").isEmpty()) {
			clanConfig.set("blacklisted-clan-names", blacklistedClanNames);
		}

		if (clanConfig.getStringList("blacklisted-clan-tags").isEmpty()) {
			clanConfig.set("blacklisted-clan-tags", blacklistedClanNames);
		}

		if (clanConfig.getString("clan-cost") == null) {
			clanConfig.set("clan-cost", 1000);
		}

//		Admin spy clan chat format
		if (clanConfig.getString("admin-spy-clan-chat-format") == null) {
			clanConfig.set("admin-spy-clan-chat-format", "&7[&4&lAdminSpy&7] &7[&b&lClan&7] &b%player%&7: &f%message%");
		}

//		Clan chat format
		if (clanConfig.getString("clan-chat-format") == null) {
			clanConfig.set("clan-chat-format", "&7[&b&lClan&7] &b%player%&7: &f%message%");
		}

		saveClanConfig();

	}


//-------------- Clan Config -----------------//
//	This is the config for the clans.yml file

	//	Add a new clan to the folder,
//	For example: /clan/<clanname> folder
	public void createClan(Player player, String clanName, String clanTag) {

//		If the clan folder is not created create it]
		clanFolder = new File("plugins/IslandDefender/clans");
		if (!clanFolder.exists()) {
			clanFolder.mkdir();
		}

		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");
		YamlConfiguration clanConfig;


//	Check max clans
		if (!canCreateClan()) {
			UtilsMessage.errorMessage(player, "The server has reached max's clans!");
			return;
		}

//	Check if the clan name or tag is blacklisted
		if (isBlacklisted(clanName, clanTag)) {
			UtilsMessage.errorMessage(player, "The clan name or tag is blacklisted!");
			return;
		}


		try {
			if (!clanFile.exists()) {
				clanFile.createNewFile();
			} else {
				UtilsMessage.errorMessage(player, "Clan already exists!");
				return;
			}

			clanConfig = YamlConfiguration.loadConfiguration(clanFile);

//		Get the created time of the clan
//		2023-9-30

			String date = LocalDate.now().toString();


			// Set default values
			clanConfig.addDefault("clan-name", clanName);
			clanConfig.addDefault("clan-tag", clanTag);
			clanConfig.addDefault("clan-leader", player.getName());
			clanConfig.addDefault("clan-created", date);
			clanConfig.addDefault("clan-members", "none");
			clanConfig.addDefault("clan-allies", "none");
			clanConfig.addDefault("clan-power", "none");
			clanConfig.addDefault("clan-balance", "none");
			clanConfig.addDefault("clan-visible", "public");


			clanConfig.options().copyDefaults(true);
			clanConfig.save(clanFile);

//		Send  message that clan was created
			UtilsMessage.sendMessage(player, "&7Clan created! For more info " +
					"about your clan do &b&l/clan info");

			PlayerFileManager.setPlayerClanName(player, clanName);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//	Clan chat format
	public String getClanChatFormat() {
		return clanConfig.getString("clan-chat-format");
	}

	//	Remove a clan from the folder
	public void deleteClan(Player player) {

		String clanName = PlayerFileManager.getPlayerClanName(player);

		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");
		if (clanFile.exists()) {
			clanFile.delete();
		}
	}

	//	Check if a clan exists
	public boolean clanExists(String clanName) {
		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");
		return clanFile.exists();
	}

	//	Get the clan file
	public File getClanFile(String clanName) {
		return new File("plugins/IslandDefender/clans/" + clanName + ".yml");
	}

	//	Join clan
	public void joinClan(String clanName, Player playerName) {
		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");
		FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);

//		Players can only join public clans
		if (!Objects.equals(clanConfig.getString("clan-visible"), "public")) {
			UtilsMessage.errorMessage(playerName, "This clan is not public ask for an invite!");
			return;
		}

		List<String> clanMembers = clanConfig.getStringList("clan-members");
		clanMembers.add(playerName.getName());
		clanConfig.set("clan-members", clanMembers);

		PlayerFileManager.setPlayerClanName(playerName, clanName);

//		Save the file
		try {
			clanConfig.save(clanFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

// List of blacklisted clan names

	//	Leave clan
	public void leaveClan(Player playerName) {

		String clanName = PlayerFileManager.getPlayerClanName(playerName);

		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");
		FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);

		List<String> clanMembers = clanConfig.getStringList("clan-members");
		clanMembers.remove(playerName.getName());
		clanConfig.set("clan-members", clanMembers);

		PlayerFileManager.setPlayerClanName(playerName, "none");

//		Save the file
		try {
			clanConfig.save(clanFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//	Check if the clan amount is not equal to the max clans
	public boolean canCreateClan() {
		int clanCount = 0;

		clanFolder = new File("plugins/IslandDefender/clans");

		for (File file : clanFolder.listFiles()) {
			if (file.isFile()) {
				clanCount++;
			}
		}

		return clanCount != getMaxClans();
	}

// List of blacklisted clan tags

	//	Check blacklist names and tag
	public boolean isBlacklisted(String clanName, String clanTag) {
		List<String> blacklistedClanNames = getBlacklistedClanNames();
		List<String> blacklistedClanTags = getBlacklistedClanTags();

		return blacklistedClanNames.contains(clanName) || blacklistedClanTags.contains(clanTag);
	}

	//	Get a list of the clans on the server and their names'
//	Remove the [] from the list
	public List<String> getClanList() {
		List<String> clanList = new ArrayList<>();

		clanFolder = new File("plugins/IslandDefender/clans");

		for (File file : clanFolder.listFiles()) {
			if (file.isFile()) {
				clanList.add(file.getName().replace(".yml", ""));
			}
		}

		return clanList;
	}

//--------------------------------------------//


//---------- Methods to get details about the clan  ---------------//

	//	Max clans
	private int getMaxClans() {
		return clanConfig.getInt("max-clans");
	}

	public void setMaxClans(int maxClans) {
		clanConfig.set("max-clans", maxClans);
	}

	//	Max members
	public int getMaxMembers() {
		return clanConfig.getInt("max-members");
	}

	public void setMaxMembers(int maxMembers) {
		clanConfig.set("max-members", maxMembers);
	}

	//	Rank to create a clan
	public String getRankToCreate() {
		return clanConfig.getString("rank-to-create");
	}

	public void setRankToCreate(String rankToCreate) {
		clanConfig.set("rank-to-create", rankToCreate);
	}

	public List<String> getBlacklistedClanNames() {

		return clanConfig.getStringList("blacklisted-clan-names");
	}

	public void setBlacklistedClanNames(List<String> blacklistedClanNames) {
		clanConfig.set("blacklisted-clan-names", blacklistedClanNames);
	}

	public List<String> getBlacklistedClanTags() {
		return clanConfig.getStringList("blacklisted-clan-tags");
	}

	public void setBlacklistedClanTags(List<String> blacklistedClanTags) {
		clanConfig.set("blacklisted-clan-tags", blacklistedClanTags);
	}

	//	Clan name
	public String getClanName(String clanName) {
		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");
		FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
		return clanConfig.getString("clan-name");
	}

	//	Clan tag
	public String getClanTag(String clanName) {
		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");
		FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
		return clanConfig.getString("clan-tag");
	}

	//	Check if the sender is the clan leader
	public String getClanLeader(String clanName) {
		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");
		FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
		return clanConfig.getString("clan-leader");
	}

	//	Clan members
	public List<String> getClanMembers(String clanName) {
		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");
		FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
		return clanConfig.getStringList("clan-members");
	}

	//	Clan allies
	public List<String> getClanAllies(String clanName) {
		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");
		FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
		return clanConfig.getStringList("clan-allies");
	}

	//	Clan chat format
	public String getClanChatFormat(String clanName) {
		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");
		FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
		return clanConfig.getString("clan-chat-format");
	}

//	get admin spy clan chat format

	public String getAdminSpyClanChatFormat() {
		return clanConfig.getString("admin-spy-clan-chat-format");
	}



//	----------Player--------


	//	Clan name
	public static String getPlayerClanName(Player player) {

		String clanName = PlayerFileManager.getPlayerClanName(player);

		File playerFile = new File("plugins/IslandDefender/players/" + clanName + ".yml");
		FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
		return playerConfig.getString("clan-name");
	}

	//	Clan tag
	public static String getPlayerClanTag(Player player) {

		String clanName = PlayerFileManager.getPlayerClanName(player);

//		Go to the clan file and get the clan tag
		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");

		if (clanFile.exists()) {
			FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
			return clanConfig.getString("clan-tag");
		}


		return "none";
	}

	//	Clan rank
	public static String getPlayerClanRank(Player player) {

		String clanName = PlayerFileManager.getPlayerClanName(player);

		File playerFile = new File("plugins/IslandDefender/players/" + clanName + ".yml");
		FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
		return playerConfig.getString("clan-rank");
	}

	//	Clan power
	public static int getPlayerClanPower(Player player) {

		String clanName = PlayerFileManager.getPlayerClanName(player);

		File playerFile = new File("plugins/IslandDefender/players/" + clanName + ".yml");
		FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
		return playerConfig.getInt("clan-power");
	}

	//	Clan balance
	public static int getPlayerClanBalance(Player player) {

		String clanName = PlayerFileManager.getPlayerClanName(player);

		File playerFile = new File("plugins/IslandDefender/players/" + clanName + ".yml");
		FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
		return playerConfig.getInt("clan-balance");
	}

	//	Clan created
	public static String getPlayerClanCreated(Player player) {

		String clanName = PlayerFileManager.getPlayerClanName(player);


		File playerFile = new File("plugins/IslandDefender/players/" + clanName + ".yml");
		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
			return playerConfig.getString("clan-created");
		}
		return null;
	}


	//	Clan enemies
	public List<String> getClanEnemies(String clanName) {
		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");
		FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
		return clanConfig.getStringList("clan-enemies");
	}

	//	 Clans power
	public int getClanPower(String clanName) {
		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");
		FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
		return clanConfig.getInt("clan-power");
	}

	//	Clan balance
	public int getClanBalance(String clanName) {
		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");
		FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
		return clanConfig.getInt("clan-balance");
	}

	public String getClanCreated(String clanName) {

		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");
		if (clanFile.exists()) {
			FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
			return clanConfig.getString("clan-created");
		}
		return null;
	}

	//	Clan visible
	public String getClanVisible(String clanName) {

		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");
		if (clanFile.exists()) {
			FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
			return clanConfig.getString("clan-visible");
		}
		return null;
	}

	//	Set clan visible
	public void setClanVisible(Player player, String visible) {

		String clanName = PlayerFileManager.getPlayerClanName(player);


		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");
		if (clanFile.exists()) {
			FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
			clanConfig.set("clan-visible", visible);
		}
	}

	//	isLeader
	public boolean isLeader(Player player) {

		String clanName = PlayerFileManager.getPlayerClanName(player);

		return Objects.equals(getClanLeader(clanName), player.getName());
	}


	public void invitePlayer(Player player, Player target) {

		//		Check if player can do /invite
		if (!isLeader(player)) {

			UtilsMessage.errorMessage(player, "&cYou are not the correct clan rank to do this!");

			return;
		}

		//		Check if the player is in a clan
		if (!PlayerFileManager.isInClan(player)) {
			UtilsMessage.errorMessage(player, "&cYou are not in a clan!");
			return;
		}

		//		Check if the player is in a clan if so they can't invite
		if (PlayerFileManager.isInClan(target)) {
			UtilsMessage.errorMessage(player, "&cThis player is already in a clan!");
			return;
		}

		//		Invite the target to your clan
		invitePlayer(player, target);

		UtilsMessage.sendMessage(player, "&aYou have invited &e" + target.getName() + " &ato your clan!");
		UtilsMessage.sendMessage(target, "&aYou have been invited to &e"
				+ PlayerFileManager.getPlayerClanName(player) + "&a's clan!");
		UtilsMessage.sendMessage(target, "&aDo &e/clan join " +
				PlayerFileManager.getPlayerClanName(player) + " &ato join!");


	}


	public void kickPlayer(Player player, Player target) {

//		Check if the player is the correct clan rank to preform this command
		if (!isLeader(player)) {

			UtilsMessage.errorMessage(player, "&cYou are not the correct clan rank to do this!");

			return;
		}


//		Check if the target is in the same clan as the player
		if (!Objects.equals(PlayerFileManager.getPlayerClanName(player),
				PlayerFileManager.getPlayerClanName(target))) {
			UtilsMessage.errorMessage(player, "&cThis player is not in your clan!");
			return;
		}

//		Remove the player form the clan list
		leaveClan(target);

//		Send message to the player
		UtilsMessage.sendMessage(player, "&aYou have kicked &e" + target.getName() + " &afrom your clan!");

//		If the target is offline then send them a message when they join
		if (!target.isOnline()) {
			UtilsMessage.sendMessage(target, "&aYou have been kicked from &e"
					+ PlayerFileManager.getPlayerClanName(player) + "&a's clan!");
			UtilsMessage.sendMessage(target, "&aDo &e/clan join " +
					PlayerFileManager.getPlayerClanName(player) + " &ato join!");
		} else {
			UtilsMessage.sendMessage(target, "&aYou have been kicked from &e"
					+ PlayerFileManager.getPlayerClanName(player) + "&a's clan!");
			UtilsMessage.sendMessage(target, "&aDo &e/clan join " +
					PlayerFileManager.getPlayerClanName(player) + " &ato join!");

		}


	}

//	Clan chat

	public void clanChat(Player player, String message) {

		String clanName = PlayerFileManager.getPlayerClanName(player);

		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");

		if (clanFile.exists()) {
			FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);

			List<String> clanMembers = clanConfig.getStringList("clan-members");

			for (String member : clanMembers) {
				Player target = player.getServer().getPlayer(member);

				if (target != null) {
					UtilsMessage.sendMessage(target, getClanChatFormat(clanName)
							.replace("%player%", player.getName())
							.replace("%message%", message));
				}
			}
		}
	}

	//	Clan chat
	public void clanChat(Player player, String message, String clanName) {

		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");

		if (clanFile.exists()) {
			FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);

//			Add the clan members and the leader of the clan to the list
			List<String> clanMembers = clanConfig.getStringList("clan-members");
			clanMembers.add(clanConfig.getString("clan-leader"));
			for (String member : clanMembers) {
				Player target = Bukkit.getPlayer(member);

				if (target != null) {
					UtilsMessage.sendMessage(target, getClanChatFormat(clanName)
							.replace("%player%", player.getName())
							.replace("%message%", message));
				}
			}
		}
	}




//--------------------------------------------//


//-------------------Admin---------------------//

	//	Clan power
	public void setClanPowerAdmin(String clanName, int power) {
		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");
		if (clanFile.exists()) {
			FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
			clanConfig.set("clan-power", power);
		}
	}

	//	Clan balance
	public void setClanBalanceAdmin(String clanName, int balance) {
		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");
		if (clanFile.exists()) {
			FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
			clanConfig.set("clan-balance", balance);
		}
	}

	//	Clan allies
	public void setClanAlliesAdmin(String clanName, List<String> allies) {
		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");
		if (clanFile.exists()) {
			FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
			clanConfig.set("clan-allies", allies);
		}
	}

	//	Clan enemies
	public void setClanEnemiesAdmin(String clanName, List<String> enemies) {
		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");
		if (clanFile.exists()) {
			FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
			clanConfig.set("clan-enemies", enemies);
		}
	}


	//	Clan created
	public void setClanCreatedAdmin(String clanName, String date) {

		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");
		if (clanFile.exists()) {
			FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
			clanConfig.set("clan-created", date);
		}
	}

	//	Clan visible
	public void setClanVisibleAdmin(String clanName, String visible) {

		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");
		if (clanFile.exists()) {
			FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);

			clanConfig.set("clan-visible", visible);
		}
	}

	//	Delete clan
	public void deleteClanAdmin(String clanName) {
		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");
		if (clanFile.exists()) {
			clanFile.delete();
		}
	}

//	Clan spy chat this will allow admins to view clan chat

	//	Clan chat
	public void clanChatAdmin(Player playerAdmin, String message, String clanName) {

		File clanFile = new File("plugins/IslandDefender/clans/" + clanName + ".yml");

		if (clanFile.exists()) {
			FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);

//			Add the clan members and the leader of the clan to the list
			List<String> clanMembers = clanConfig.getStringList("clan-members");
			clanMembers.add(clanConfig.getString("clan-leader"));
			clanMembers.add(playerAdmin.getName());

			for (String member : clanMembers) {
				Player target = Bukkit.getPlayer(member);

				if (target != null) {
					UtilsMessage.sendMessage(target, getAdminSpyClanChatFormat()
							.replace("%player%", playerAdmin.getName())
							.replace("%message%", message));
				}
			}
		}
	}






//--------------------------------------------//


}
