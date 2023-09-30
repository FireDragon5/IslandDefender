package me.firedragon5.clashcraft.filemanager.clans;

import me.firedragon5.clashcraft.filemanager.player.PlayerFileManager;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class ClanFolderManager {


//	Folder structure
//	plugins/ClashCraft/clans/<clanname>.yml
//	plugins/ClashCraft/clans.yml

	File clanFolder;

	File clanFile;

	FileConfiguration clanConfig;

	static ClanFolderManager fileManager = new ClanFolderManager();

	public static ClanFolderManager getFileManager() {
		return fileManager;
	}

	public void setup() {

		clanFile = new File("plugins/ClashCraft/clans.yml");

		if (!clanFile.exists()) {
			try {
				clanFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		clanConfig = YamlConfiguration.loadConfiguration(clanFile);


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


//	Add a new clan to the folder,
//	For example: /clan/<clanname> folder
public void addClan(Player player, String clanName, String clanTag) {
	File clanFile = new File("plugins/ClashCraft/clans/" + clanName + ".yml");
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
		}else {
			UtilsMessage.errorMessage(player, "Clan already exists!");
			return;
		}

		clanConfig = YamlConfiguration.loadConfiguration(clanFile);

		// Set default values
		clanConfig.addDefault("clan-name", clanName);
		clanConfig.addDefault("clan-tag", clanTag);
		clanConfig.addDefault("clan-leader", player.getName());
		clanConfig.addDefault("clan-members", "none");
		clanConfig.addDefault("clan-allies", "none");
		clanConfig.addDefault("clan-power", "none");
		clanConfig.addDefault("clan-balance", "none");


		clanConfig.options().copyDefaults(true);
		clanConfig.save(clanFile);

//		Send message that clan was created
		UtilsMessage.correctMessage(player, "Clan created! &a/clan info " + clanName
				+ " &7to view info about your clan!");

		PlayerFileManager.setPlayerClanName(player, clanName);


	} catch (Exception e) {
		e.printStackTrace();
	}
}

//	Remove a clan from the folder
	public void removeClan(String clanName) {
		File clanFile = new File("plugins/ClashCraft/clans/" + clanName + ".yml");
		if (clanFile.exists()) {
			clanFile.delete();
		}
	}

//	Check if a clan exists
	public boolean clanExists(String clanName) {
		File clanFile = new File("plugins/ClashCraft/clans/" + clanName + ".yml");
		return clanFile.exists();
	}

//	Get the clan file
	public File getClanFile(String clanName) {
		return new File("plugins/ClashCraft/clans/" + clanName + ".yml");
	}

//	Join clan
	public void joinClan(String clanName, Player playerName) {
		File clanFile = new File("plugins/ClashCraft/clans/" + clanName + ".yml");
		FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);

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

//	Leave clan
	public void leaveClan(Player playerName) {
		File clanFile = new File("plugins/ClashCraft/clans/" + playerName.getName() + ".yml");
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

		clanFolder = new File("plugins/ClashCraft/clans");

		for (File file : clanFolder.listFiles()) {
			if (file.isFile()) {
				clanCount++;
			}
		}

		return clanCount != getMaxClans();
	}

//	Check blacklist names and tag
	public boolean isBlacklisted(String clanName, String clanTag) {
		List<String> blacklistedClanNames = getBlacklistedClanNames();
		List<String> blacklistedClanTags = getBlacklistedClanTags();

		return blacklistedClanNames.contains(clanName) || blacklistedClanTags.contains(clanTag);
	}


//	Get a list of the clans on the server and their names
	public List<String> getClanList() {
		clanFolder = new File("plugins/ClashCraft/clans");
		List<String> clanList = new ArrayList<>();

		for (File file : clanFolder.listFiles()) {
			if (file.isFile()) {
				clanList.add(file.getName().replace(".yml", ""));
			}
		}

		return clanList;
	}



//-------------- Clan Config -----------------//
//	This is the config for the clans.yml file


//	Load the clans.yml file with the following default values
	public void loadClanConfig() {

//		List of black list names and tags
		List<String> blacklistedClanNames = Arrays.asList("clan", "clans");

		clanConfig.addDefault("max-clans", 5);
		clanConfig.addDefault("max-members", 10);
		clanConfig.addDefault("rank-to-create", "default");

		clanConfig.addDefault("blacklisted-clan-names", blacklistedClanNames);
		clanConfig.addDefault("blacklisted-clan-tags", blacklistedClanNames);
		clanConfig.addDefault("clan-cost", 1000);


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

		saveClanConfig();
	}


//	Max clans
	public int getMaxClans() {
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

// List of blacklisted clan names

	public List<String> getBlacklistedClanNames() {

		Logger.getLogger("ClashCraft").info(clanConfig.getStringList("blacklisted-clan-names").toString());

		return clanConfig.getStringList("blacklisted-clan-names");
	}

	public void setBlacklistedClanNames(List<String> blacklistedClanNames) {
		clanConfig.set("blacklisted-clan-names", blacklistedClanNames);
	}

// List of blacklisted clan tags

	public List<String> getBlacklistedClanTags() {
		return clanConfig.getStringList("blacklisted-clan-tags");
	}

	public void setBlacklistedClanTags(List<String> blacklistedClanTags) {
		clanConfig.set("blacklisted-clan-tags", blacklistedClanTags);
	}

//--------------------------------------------//



//---------- Methods to get details about the clan  ---------------//


//	Clan name
	public String getClanName(String clanName) {
		File clanFile = new File("plugins/ClashCraft/clans/" + clanName + ".yml");
		FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
		return clanConfig.getString("clan-name");
	}

//	Clan tag
	public String getClanTag(String clanName) {
		File clanFile = new File("plugins/ClashCraft/clans/" + clanName + ".yml");
		FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
		return clanConfig.getString("clan-tag");
	}

//	Clan leader
	public String getClanLeader(String clanName) {
		File clanFile = new File("plugins/ClashCraft/clans/" + clanName + ".yml");
		FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
		return clanConfig.getString("clan-leader");
	}

//	Clan members
	public List<String> getClanMembers(String clanName) {
		File clanFile = new File("plugins/ClashCraft/clans/" + clanName + ".yml");
		FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
		return clanConfig.getStringList("clan-members");
	}

//	Clan allies
	public List<String> getClanAllies(String clanName) {
		File clanFile = new File("plugins/ClashCraft/clans/" + clanName + ".yml");
		FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
		return clanConfig.getStringList("clan-allies");
	}

//	Clan enemies
	public List<String> getClanEnemies(String clanName) {
		File clanFile = new File("plugins/ClashCraft/clans/" + clanName + ".yml");
		FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
		return clanConfig.getStringList("clan-enemies");
	}


//	 Clans power
	public int getClanPower(String clanName) {
		File clanFile = new File("plugins/ClashCraft/clans/" + clanName + ".yml");
		FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
		return clanConfig.getInt("clan-power");
	}

//	Clan balance
	public int getClanBalance(String clanName) {
		File clanFile = new File("plugins/ClashCraft/clans/" + clanName + ".yml");
		FileConfiguration clanConfig = YamlConfiguration.loadConfiguration(clanFile);
		return clanConfig.getInt("clan-balance");
	}





//--------------------------------------------//









}
