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
	public void loadMineConfig() {

		mineConfig.addDefault("mines.", "test");
		mineConfig.addDefault("mines.test.name", "test");
//		List of blocks
		mineConfig.addDefault("mines.test.blocks", "STONE");
		mineConfig.addDefault("mines.test.blocks", "GRASS_BLOCK");
		mineConfig.addDefault("mines.test.blocks", "DIRT");
		mineConfig.addDefault("mines.test.blocks", "COBBLESTONE");
		mineConfig.addDefault("mines.test.blocks", "OAK_PLANKS");

//		Spawn location
		mineConfig.addDefault("mines.test.spawn", "none");
//		Rank needed to access
		mineConfig.addDefault("mines.test.rank", "default");
//		Cost
		mineConfig.addDefault("mines.test.cost", "0");
//		Reset time
		mineConfig.addDefault("mines.test.reset", "0");
//		Display block for the gui
		mineConfig.addDefault("mines.test.display", "STONE");

		mineConfig.options().copyDefaults(true);
		saveMineConfig();
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
			mineConfig.addDefault("mines.test.reset", "0");
//		Display block for the gui
			mineConfig.addDefault("mines.test.display", "STONE");

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
		return mineConfig.getInt("mines." + mine + ".reset");
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


	//	For loop to get all the mines and return their names and display block
	public String getMines() {

		StringBuilder mines = new StringBuilder();

		for (String mine : mineConfig.getConfigurationSection("mines.").getKeys(false)) {

			mines.append(mine).append(" ").append(mineConfig.getString("mines." + mine + ".display")).append(" ");

		}

		return mines.toString();
	}

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
		mineConfig.set("mines." + name + ".display", "none");
		mineConfig.set("mines." + name + ".blocks", "none");
		mineConfig.set("mines." + name + ".spawn", location);
		mineConfig.set("mines." + name + ".rank", "none");
		mineConfig.set("mines." + name + ".cost", "none");
		mineConfig.set("mines." + name + ".reset", "none");

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
