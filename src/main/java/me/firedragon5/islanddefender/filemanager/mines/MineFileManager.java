package me.firedragon5.islanddefender.filemanager.mines;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

//Default:
//  name: Default
//  display: STONE
//  blocks: STONE
//  spawn: 0, 0, 0
//  rank: Default
//  cost: 0
//  reset-time: 0
//	mineColor: '&a'
//  next-mine: max
//  slot: 0
//  pit-locations:
//    top-block: 0, 0 ,0
//    bottom-block: 0, 0, 0


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
			mineConfig.addDefault("Default.blocks", List.of("STONE", "COAL"));
			mineConfig.addDefault("Default.blocks.STONE.percentage", 90);
			mineConfig.addDefault("Default.blocks.COAL.percentage", 10);
			mineConfig.addDefault("Default.spawn", "0, 0, 0");
			mineConfig.addDefault("Default.rank", "Default");
			mineConfig.addDefault("Default.cost", 0);
			mineConfig.addDefault("Default.reset-time", 0);
			mineConfig.addDefault("Default.next-mine", "max");
			mineConfig.addDefault("Default.mineColor", "&a");
			mineConfig.addDefault("Default.slot", 0);
			mineConfig.addDefault("Default.pit-locations.top-block", "0, 0, 0");
			mineConfig.addDefault("Default.pit-locations.bottom-block", "0, 0, 0");

			mineConfig.options().copyDefaults(true);

		}

		saveMineConfig();

	}

//	------------Config----------------

	//	Get Display block
	public Material getDisplayBlock(String mine) {
		String materialName = mineConfig.getString(mine + ".display");
		if (materialName == null) {
			return Material.STONE;  // You can use any default material you prefer.
		}

		Material material = Material.matchMaterial(materialName);
		// Handle invalid material names gracefully.
		return Objects.requireNonNullElse(material, Material.STONE);
	}

	//	Get Reset time
	public int getResetTime(String mine) {
		return mineConfig.getInt(mine + ".reset-time");
	}

	//	Get Cost
	public int getCost(String mine) {
		return mineConfig.getInt(mine + ".cost");
	}

	//	Get Rank
	public String getRank(String mine) {
		return mineConfig.getString(mine + ".rank");
	}

	//	Get Spawn
	public String getSpawn(String mine) {
		return mineConfig.getString(mine + ".spawn");
	}

	//	Get Blocks
	public @Nullable String getBlocks(String mine) {
		return mineConfig.getString(mine + ".blocks");
	}

	//	Get Name
	public String getName(String mine) {
		return mineConfig.getString(mine + ".name");
	}

	//	Get Next mine
	public String getNextMine(String mine) {
		return mineConfig.getString(mine + ".next-mine");
	}

	//	Get all the blocks in the mine
	public Material getBlockList(String mine) {
		return Material.matchMaterial(Objects.requireNonNull(mineConfig.getString(mine + ".blocks")));
	}

	//	Get all the blocks in the mine as a list
	public String[] getBlockListAsList(String mine) {
		return mineConfig.getString(mine + ".blocks").split(", ");
	}

	//	Get the percentage of the blocks
	public int getPercentage(String mine) {
		return mineConfig.getInt(mine + ".blocks." + Arrays.toString(getBlockListAsList(mine)) + ".percentage");
	}

	//	Get Slot
	public int getSlot(String mine) {
		return mineConfig.getInt(mine + ".slot");
	}

	//	Get Menu size
	public int getMenuSize() {
		return mineConfig.getInt("Menu-size");
	}

	//	Get Pit locations bottom block
	public String getPitLocationsBottomBlock(String mine) {
		return mineConfig.getString(mine + ".pit-locations.bottom-block");
	}

	//	Get Pit locations top block
	public String getPitLocationsTopBlock(String mine) {
		return mineConfig.getString(mine + ".pit-locations.top-block");
	}


	//	get the x y z of the pit locations
	public int getPitLocationsX(String mine, String location) {
		String[] split = mineConfig.getString(mine + ".pit-locations." + location).split(", ");
		return Integer.parseInt(split[0]);
	}

	public int getPitLocationsY(String mine, String location) {
		String[] split = mineConfig.getString(mine + ".pit-locations." + location).split(", ");
		return Integer.parseInt(split[1]);
	}

	public int getPitLocationsZ(String mine, String location) {
		String[] split = mineConfig.getString(mine + ".pit-locations." + location).split(", ");
		return Integer.parseInt(split[2]);
	}


	//	Set pit locations top block
	public void setPitLocationsTopBlock(String mine, String location) {
		mineConfig.set(mine + ".pit-locations.top-block", location);
		saveMineConfig();
	}

	//	set pit locations bottom block
	public void setPitLocationsBottomBlock(String mine, String location) {
		mineConfig.set(mine + ".pit-locations.bottom-block", location);
		saveMineConfig();
	}


	//	Get all the mines
	public String[] getMineList() {

		return mineConfig.getKeys(false).toArray(new String[0]);

	}

	public String getColor(String mine) {

		return mineConfig.getString(mine + ".mineColor");


	}

//	------------Config----------------


//	--------Admin ------------

	//	Create mine
	public void createMine(Player player, String name) {

//		Get player location
		String location = player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ();

		mineConfig.set(name + ".name", name);
		mineConfig.set(name + ".display", "STONE");
		mineConfig.set(name + ".blocks", "STONE");
		mineConfig.set(name + ".spawn", location);
		mineConfig.set(name + ".rank", "none");
		mineConfig.set(name + ".cost", "none");
		mineConfig.set(name + ".reset-time", "none");
		mineConfig.set(name + ".mineColor", "&a");
		mineConfig.set(name + ".next-mine", "Max");
		mineConfig.set(name + ".slot", 0);
		mineConfig.set(name + ".pit-locations.top-block", "0, 0, 0");
		mineConfig.set(name + ".pit-locations.bottom-block", "0, 0, 0");
		mineConfig.options().copyDefaults(true);
		saveMineConfig();
	}

	//	Delete mine
	public void deleteMine(String name) {
		mineConfig.set(name, null);
		mineConfig.options().copyDefaults(true);
		saveMineConfig();
	}


//	--------Admin ------------


}
