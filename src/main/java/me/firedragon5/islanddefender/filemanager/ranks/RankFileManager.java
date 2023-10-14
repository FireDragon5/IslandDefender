package me.firedragon5.islanddefender.filemanager.ranks;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class RankFileManager {


	static RankFileManager instance = new RankFileManager();

	File rankFile;

	FileConfiguration rankConfig;

	public static RankFileManager getFileManager() {
		return instance;
	}

	public void setup() {

		rankFile = new File("plugins/IslandDefender/ranks.yml");

		if (!rankFile.exists()) {
			try {
				rankFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		rankConfig = YamlConfiguration.loadConfiguration(rankFile);
	}

	public void saveRankConfig() {
		try {
			rankConfig.save(rankFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//	load

//	1:
//	  name: "&7Default"
//	  prefix: "&7"
//	  permission: "islanddefender.default"
//	  cost: 0
//	  displayBlock: "GREEN_WOOL"
//	  rank-perks:
//	    - "fly"
//	    - "speed 10"
//	  next-rank: "2"


	public void loadRankConfig() {

		checkConfig();

		rankConfig.options().copyDefaults(true);
		saveRankConfig();
	}

	public void checkConfig() {
		if (!rankConfig.contains("1")) {
			rankConfig.addDefault("1.name", "&7Default");
			rankConfig.addDefault("1.prefix", "&7");
			rankConfig.addDefault("1.permission", "islanddefender.default");
			rankConfig.addDefault("1.cost", 0);
			rankConfig.addDefault("1.displayBlock", "GREEN_WOOL");
			rankConfig.addDefault("1.rank-perks", List.of("fly", "speed 10"));
			rankConfig.addDefault("1.next-rank", "2");
			saveRankConfig();
		}
	}


	public void reloadRankConfig() {
		rankConfig = YamlConfiguration.loadConfiguration(rankFile);
	}

	public FileConfiguration getRankConfig() {
		return rankConfig;
	}


//	Getters

	//	Name
	public String getName(String rank) {
		return rankConfig.getString(rank + ".name");
	}

	//	Prefix
	public String getPrefix(String rank) {
		return rankConfig.getString(rank + ".prefix");
	}

	//	Permission
	public String getPermission(String rank) {
		return rankConfig.getString(rank + ".permission");
	}

	//	Cost
	public int getCost(String rank) {
		return rankConfig.getInt(rank + ".cost");
	}

	//	Block
	public Material getDisplayBlock(String rank) {
		return Material.getMaterial(Objects.requireNonNull(rankConfig.getString(rank + ".displayBlock")));
	}

	//	Next rank
	public String getNextRank(String rank) {
		return rankConfig.getString(rank + ".next-rank");
	}

	//	Rank perks
	public List<String> getRankPerks(String rank) {
		return rankConfig.getStringList(rank + ".rank-perks");
	}


//	Get all the ranks
//	'1':
//	  name: "&7Default"

	public String[] getRanks() {
		return rankConfig.getKeys(false).toArray(new String[0]);
	}


}
