package me.firedragon5.islanddefender.filemanager.mines;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.io.File;

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

//	  test:
//	    name: test
//	    blocks:
//	    - STONE
//	    - GRASS_BLOCK
//	    - DIRT
//	    - COBBLESTONE
//	    spawn: 0, 0, 0
//	    rank: default
//	    cost: 0
//	    reset-time: 0
//	    display: STONE


	public void loadMineConfig() {

		checkMineConfig();


		mineConfig.options().copyDefaults(true);
		saveMineConfig();
	}


	//	Check if the yml file has all the correct values
	public void checkMineConfig() {

		mineConfig.addDefault("Menu-size", 54);

		if (!mineConfig.contains("Default")) {
			mineConfig.addDefault("Default.name", "Default");
			mineConfig.addDefault("Default.display", "STONE");
			mineConfig.addDefault("Default.blocks", "STONE");
			mineConfig.addDefault("Default.spawn", "0, 0, 0");
			mineConfig.addDefault("Default.rank", "default");
			mineConfig.addDefault("Default.cost", 0);
			mineConfig.addDefault("Default.reset-time", 0);
			mineConfig.addDefault("Default.next-mine", "max");
			mineConfig.addDefault("Default.slot", 0);
			mineConfig.options().copyDefaults(true);

		}

		saveMineConfig();

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

	//	Get Next mine
	public String getNextMine(String mine) {
		return mineConfig.getString("mines." + mine + ".next-mine");
	}

	//	Get Slot
	public int getSlot(String mine) {
		return mineConfig.getInt("mines." + mine + ".slot");
	}

	//	Get Menu size
	public int getMenuSize() {
		return mineConfig.getInt("Menu-size");
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
		mineConfig.set("mines." + name + ".next-mine", "Max");
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
