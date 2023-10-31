package me.firedragon5.islanddefender.filemanager.mines;

import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
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
		mineConfig.addDefault("reset-time", 20);
//		Command to reset the mines
		mineConfig.addDefault("reset-command", "mineregions fill %mineName%");

		if (!mineConfig.contains("Default")) {
			mineConfig.addDefault("Default.name", "Default");
			mineConfig.addDefault("Default.display", "STONE");
			mineConfig.addDefault("Default.blocks-in-pit", List.of("STONE", "COAL"));
			mineConfig.addDefault("Default.blocks.STONE.percentage", 90);
			mineConfig.addDefault("Default.blocks.COAL.percentage", 10);
			mineConfig.addDefault("Default.spawn", "0, 0, 0");
			mineConfig.addDefault("Default.rank", "Default");
			mineConfig.addDefault("Default.cost", 0);
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
	public int getResetTime() {

		return mineConfig.getInt(".reset-time");
	}

	//	Convert the ticks to min
	public int getResetTicksInMin() {

		return mineConfig.getInt(".reset-time") * 60 * 20;
	}

	//	Get Reset command
	public void getResetCommand() {

//		Reset all the mines in the config with a 20 sec delay between each mine
		for (String mine : getMineList()) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
					mineConfig.getString("reset-command").replace("%mineName%", mine));
		}

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
		return mineConfig.getString(mine + ".blocks-in-pit");
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
	public List<Material> getBlockList(String mine) {
		List<String> materialNames = mineConfig.getStringList(mine + ".blocks-in-pit");
		List<Material> validMaterials = new ArrayList<>();

		for (String materialName : materialNames) {
			Material material = Material.matchMaterial(materialName);
			System.out.println(materialName);
			if (material != null) {
				System.out.println(material);
				validMaterials.add(material);
			}
		}

		// If no valid materials are found, return a list with a default material (e.g., Material.STONE).
		if (validMaterials.isEmpty()) {
			validMaterials.add(Material.STONE);
		}

		return validMaterials;
	}

	// Get the list of block names from the config for a specific mine
	public List<String> getBlockListAsList(String mine) {
		ConfigurationSection blocksSection = mineConfig.getConfigurationSection(mine + ".blocks");
		if (blocksSection == null) {
			return new ArrayList<>(); // Return an empty list or handle the absence of the section as needed.
		}
		return new ArrayList<>(blocksSection.getKeys(false));
	}

	// Get the percentage of a specific block in a mine
	public int getPercentage(String mine, String blockName) {
		return mineConfig.getInt(mine + ".blocks." + blockName + ".percentage");
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
	public List<String> getMineList() {


		return List.of(mineConfig.getKeys(false).stream()
				.filter(key -> !key.equals("Menu-size") && !key.equals("reset-time") && !key.equals("reset-command"))
				.toArray(String[]::new));
	}

	public String getColor(String mine) {

		return mineConfig.getString(mine + ".mineColor");


	}

//	------------Config----------------


//	--------Admin ------------

	//	Create mine
	public void createMine(Player player, String name) {

		if (mineConfig.contains(name)) {
			UtilsMessage.sendMessage(player, "&cA mine with that name already exists!");
			return;
		} else if (name.equalsIgnoreCase("<name>")) {
			UtilsMessage.sendMessage(player, "&cYou must specify a name!");
			return;
		}


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
