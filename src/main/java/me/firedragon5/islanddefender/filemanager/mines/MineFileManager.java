package me.firedragon5.islanddefender.filemanager.mines;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Arrays;

public class MineFileManager {

	static MineFileManager instance = new MineFileManager();

	File mineFile;

	FileConfiguration mineConfig;


	public void setup() {

		mineFile = new File("plugins/IslandDefender/mines.yml");

		if (!mineFile.exists()) {
			try {
				mineFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		mineConfig = YamlConfiguration.loadConfiguration(mineFile);
	}

	public static MineFileManager getFileManager() {
		return instance;
	}

	public void saveMineConfig() {
		try {
			mineConfig.save(mineFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reloadMineConfig() {
		mineConfig = YamlConfiguration.loadConfiguration(mineFile);
	}

	//	load
//	This is the format for the mines.yml file
//	mines:
//	  test:
//	    name: test
//	    blocks:
//	    - STONE
//	    - GRASS_BLOCK
//	    - DIRT
//	    - COBBLESTONE

//	    permissions: none
//	    spawn: 0, 0, 0
//	    rank: default
//	    cost: 0
//	    reset-time: 0
//	    display: STONE


	public void loadMineConfig() {
		// Add default values for the entire section
//		mineConfig.addDefault("mines.test", createDefaultMineConfig());

		mineConfig.options().copyDefaults(true);
		saveMineConfig();
	}

	private ConfigurationSection createDefaultMineConfig() {
		ConfigurationSection mineSection = mineConfig.createSection("test");

		mineSection.set("name", "test");
		mineSection.set("blocks", Arrays.asList("STONE", "GRASS_BLOCK", "DIRT", "COBBLESTONE", "OAK_PLANKS"));
		mineSection.set("spawn", "0, 0, 0");
		mineSection.set("rank", "default");
		mineSection.set("permission", "none");
		mineSection.set("cost", 0);
		mineSection.set("reset-time", 0);
		mineSection.set("display", "STONE");
		mineSection.set("permission-command", "lp group default permission set islanddefender.mine.test true");

		return mineSection;
	}

	//	Check if the yml file has all the correct values
	public void checkMineConfig() {

		if (mineConfig.getString("mines.") == null) {
			mineConfig.addDefault("mines.", "test");
			mineConfig.addDefault("mines.test.name", "test");
//		List of blocks
			mineConfig.addDefault("mines.test.blocks", "STONE");
			mineConfig.addDefault("mines.test.blocks", "GRASS_BLOCK");
			mineConfig.addDefault("mines.test.blocks", "DIRT");
			mineConfig.addDefault("mines.test.blocks", "COBBLESTONE");
			mineConfig.addDefault("mines.test.blocks", "OAK_PLANKS");

//		Spawn location
			mineConfig.addDefault("mines.test.spawn", "0, 0, 0");
//		Rank needed to access
			mineConfig.addDefault("mines.test.rank", "default");
//		Cost
			mineConfig.addDefault("mines.test.cost", "0");
//		Reset time
			mineConfig.addDefault("mines.test.reset-time", "0");
//		Display block for the gui
			mineConfig.addDefault("mines.test.display", "STONE");

//		Permission
			mineConfig.addDefault("mines.test.permission", "islanddefender.mine.test");


			mineConfig.options().copyDefaults(true);
			saveMineConfig();
		}

	}

//	------------Config----------------

	//	Get Display block
	public String getDisplayBlock(String mine) {
		return mineConfig.getString("mines." + mine + ".display");
	}

	//	Get Reset time
	public int getResetTime(String mine) {
		return mineConfig.getInt("mines." + mine + ".reset-time");
	}

	//	Get Cost
	public int getCost(String mine) {
		return mineConfig.getInt("mines." + mine + ".cost");
	}

	//	Get Rank
	public String getRank(String mine) {
		return mineConfig.getString("mines." + mine + ".rank");
	}

	//	Get Spawn
	public String getSpawn(String mine) {
		return mineConfig.getString("mines." + mine + ".spawn");
	}

	//	Get Blocks
	public @Nullable String getBlocks(String mine) {
		return mineConfig.getString("mines." + mine + ".blocks");
	}

	//	Get Name
	public String getName(String mine) {
		return mineConfig.getString("mines." + mine + ".name");
	}

	//	Get Permission
	public String getPermission(String mine) {
		return mineConfig.getString("mines." + mine + ".permission");
	}

	//	Get Permission command
	public String getPermissionCommand(String mine) {
		return mineConfig.getString("mines." + mine + ".permission-command");
	}


	//	For loop to get all the mines and return their names and display block
	public String getMines() {

		StringBuilder mines = new StringBuilder();

		for (String mine : mineConfig.getConfigurationSection("mines.").getKeys(false)) {

			mines.append(mine).append(" ").append(mineConfig.getString("mines." + mine + ".display")).append(" ");

		}

		return mines.toString();
	}

	//	Get all the mines
	public String[] getMineList() {
		return mineConfig.getConfigurationSection("mines.").getKeys(false).toArray(new String[0]);
	}

//	------------Config----------------


//	--------Admin ------------

	//	Create mine
	public void createMine(Player player, String name) {

//		Get player location
		String location = player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ();

		mineConfig.set("mines." + name + ".name", name);
		mineConfig.set("mines." + name + ".display", "STONE");
		mineConfig.set("mines." + name + ".blocks", "STONE");
		mineConfig.set("mines." + name + ".spawn", location);
		mineConfig.set("mines." + name + ".rank", "none");
		mineConfig.set("mines." + name + ".cost", "none");
		mineConfig.set("mines." + name + ".reset-time", "none");
//		Permission
		mineConfig.set("mines." + name + ".permission", "islanddefender.mine." + name);
//		Permission command that needs to be run
		mineConfig.set("mines." + name + ".permission-command", "lp group default permission set islanddefender.mine." + name + " true");

		mineConfig.options().copyDefaults(true);
		saveMineConfig();
	}

	//	Delete mine
	public void deleteMine(String name) {
		mineConfig.set("mines." + name, null);
		mineConfig.options().copyDefaults(true);
		saveMineConfig();
	}

//	--------Admin ------------


}
