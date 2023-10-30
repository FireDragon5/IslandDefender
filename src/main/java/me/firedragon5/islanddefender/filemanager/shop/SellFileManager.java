package me.firedragon5.islanddefender.filemanager.shop;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Objects;

public class SellFileManager {

	File file;

	static SellFileManager instance = new SellFileManager();

	FileConfiguration sellConfig;

	public static SellFileManager getInstance() {
		return instance;
	}


	public void setup() {

		file = new File("plugins/IslandDefender/sell.yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		sellConfig = YamlConfiguration.loadConfiguration(file);
	}

	public void saveSellConfig() {
		try {
			sellConfig.save(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	//	reload
	public void reloadSellConfig() {
		sellConfig = YamlConfiguration.loadConfiguration(file);
	}

	//	load

	public void loadSellConfig() {

		checkConfig();

		sellConfig.options().copyDefaults(true);
		saveSellConfig();
	}

	public void checkConfig() {
		sellConfig.addDefault("Menu-size", 54);

		sellConfig.addDefault("Sell-all-item", "EMERALD_BLOCK");

//		Items values
		sellConfig.addDefault("Items.DIAMOND", 100);
		sellConfig.addDefault("Items.EMERALD", 50);
		sellConfig.addDefault("Items.GOLD_INGOT", 25);
		sellConfig.addDefault("Items.IRON_INGOT", 10);
		sellConfig.addDefault("Items.COAL", 5);
		sellConfig.addDefault("Items.REDSTONE", 2);
		sellConfig.addDefault("Items.LAPIS_LAZULI", 1);
		sellConfig.addDefault("Items.QUARTZ", 1);
		sellConfig.addDefault("Items.NETHERITE_SCRAP", 100);
		sellConfig.addDefault("Items.NETHERITE_INGOT", 1000);
		sellConfig.addDefault("Items.NETHERITE_BLOCK", 10000);
		sellConfig.addDefault("Items.NETHER_STAR", 100000);
		sellConfig.addDefault("Items.ENDER_PEARL", 100);
		sellConfig.addDefault("Items.ENDER_EYE", 1000);
		sellConfig.addDefault("Items.ENDER_CHEST", 10000);
		sellConfig.addDefault("Items.DRAGON_EGG", 100000);
		sellConfig.addDefault("Items.EMERALD_BLOCK", 450);
		sellConfig.addDefault("Items.DIAMOND_BLOCK", 900);
		sellConfig.addDefault("Items.GOLD_BLOCK", 450);
		sellConfig.addDefault("Items.IRON_BLOCK", 90);

		saveSellConfig();
	}


//	Getter

	public int getMenuSize() {
		return sellConfig.getInt("Menu-size");
	}

	public Material getSellAllItem() {
		return Material.getMaterial(Objects.requireNonNull(sellConfig.getString("Sell-all-item")));
	}


//	Get all the items values

	public int getItemValues() {
		for (String key : sellConfig.getConfigurationSection("Items").getKeys(false)) {
			return sellConfig.getInt("Items." + key);
		}
		return 0;
	}

	//	Make a method that checks all the items in the SellMenu and add up the values of the items
//	Then return the total value of the items
	public int getTotalValue() {
		int totalValue = 0;
		for (String key : sellConfig.getConfigurationSection("Items").getKeys(false)) {
			totalValue += sellConfig.getInt("Items." + key);
		}
		return totalValue;
	}


	public int getMaterialValue(Material material) {

		for (String key : sellConfig.getConfigurationSection("Items").getKeys(false)) {
			if (key.equalsIgnoreCase(material.toString())) {
				return sellConfig.getInt("Items." + key);
			}
		}
		return 0;


	}
}
