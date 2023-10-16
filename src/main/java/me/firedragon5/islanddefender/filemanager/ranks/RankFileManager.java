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
	public void loadRankConfig() {

		checkConfig();

		rankConfig.options().copyDefaults(true);
		saveRankConfig();
	}

	public void checkConfig() {

//		The luckperm run command
//		/lp user %player% parent set %rank%
		rankConfig.addDefault("luckperm-run-command", "lp user %player% parent set %rank%");
		rankConfig.addDefault("Menu-size", 54);


		if (!rankConfig.contains("Default")) {
			rankConfig.addDefault("Default.name", "&7Default");
			rankConfig.addDefault("Default.prefix", "&8&l[&7Default&8&l]");
			rankConfig.addDefault("Default.permission", "islanddefender.rank.default");
			rankConfig.addDefault("Default.cost", 0);
			rankConfig.addDefault("Default.displayBlock", "GREEN_WOOL");
			rankConfig.addDefault("Default.rank-perks", List.of("fly", "speed 10"));
			rankConfig.addDefault("Default.slot", 9);
			rankConfig.addDefault("Default.color", "&a");
			rankConfig.addDefault("Default.next-rank", "Max");
		}

		saveRankConfig();
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

	//	Menu size
	public int getMenuSize() {
		return rankConfig.getInt("Menu-size");
	}

	//	Prefix
	public String getPrefix(String rank) {
		return rankConfig.getString(rank + ".prefix");
	}

	//	Permission
	public String getPermission(String rank) {
		return rankConfig.getString(rank + ".permission");
	}

	//	Luckperm Permission command
	public String getLuckpermRunCommand() {
		return rankConfig.getString("luckperm-run-command");
	}

	//	Cost
	public int getCost(String rank) {
		return rankConfig.getInt(rank + ".cost");
	}

	//	Block
	public Material getDisplayBlock(String rank) {
		String materialName = rankConfig.getString(rank + ".displayBlock");
		if (materialName == null) {
			return Material.STONE;  // You can use any default material you prefer.
		}

		Material material = Material.matchMaterial(materialName);
		// Handle invalid material names gracefully.
		return Objects.requireNonNullElse(material, Material.STONE);

	}

	//	Next rank
	public String getNextRank(String rank) {
		return rankConfig.getString(rank + ".next-rank");
	}

	//	Slot
	public int getSlot(String rank) {
		return rankConfig.getInt(rank + ".slot");
	}

	//	Color
	public String getColor(String rank) {
		return rankConfig.getString(rank + ".color");
	}

	//	Rank perks
	public List<String> getRankPerks(String rank) {
		return rankConfig.getStringList(rank + ".rank-perks");
	}


//	Get all the ranks
//	'1':
//	  name: "&7Default"

	public String[] getRanks() {
		return rankConfig.getConfigurationSection("").getKeys(false).toArray(new String[0]);
	}


}
