package me.firedragon5.islanddefender.filemanager.kits;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KitsFileManger {


	static KitsFileManger fileManager = new KitsFileManger();

	File file;

	FileConfiguration configFile;

	public void setup() {
		file = new File("plugins/IslandDefender/kits.yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		configFile = YamlConfiguration.loadConfiguration(file);
	}

	public void load() {

		checkConfig();

		configFile.options().copyDefaults(true);
		save();

	}


	public void save() {

		try {
			configFile.save(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void reload() {
		configFile = YamlConfiguration.loadConfiguration(file);
	}


	public void checkConfig() {

		configFile.addDefault("Menu-Size", 54);

//		Default kit
		configFile.addDefault("Default.Name", "Default");
//		Add a list of items in the kit with each items quantity
//		Default:
//		  Name: Default
//		  Items:
//		    - STONE_SWORD:
//		       quantity: 1
//		       enchants:
//		        - none
//		    - STONE_PICKAXE: 1
//		       quantity: 1
//		       enchants:
//		        - Efficiency: 1
//		        - Unbreaking: 1
//		    - STONE_AXE: 1
//		       quantity: 1
//		       enchants:
//		        - Efficiency: 1
//		    - APPLES:
//		       quantity: 64
//		       enchants:
//		        - none
//		    Name-of-item: Default

		// Add a list of items in the kit with each item's properties
		configFile.addDefault("Default.Items", Arrays.asList(
				"STONE_SWORD:1",
				"STONE_PICKAXE:1",
				"STONE_AXE:1",
				"APPLES:64"
		));

		// For each item, you can specify additional properties like enchants
		configFile.addDefault("Default.ItemProperties.STONE_SWORD.quantity", 1);
		configFile.addDefault("Default.ItemProperties.STONE_SWORD.enchants", Collections.singletonList("none"));

		configFile.addDefault("Default.ItemProperties.STONE_PICKAXE.quantity", 1);
		configFile.addDefault("Default.ItemProperties.STONE_PICKAXE.enchants", Arrays.asList("Efficiency:1", "Unbreaking:1"));

		configFile.addDefault("Default.ItemProperties.STONE_AXE.quantity", 1);
		configFile.addDefault("Default.ItemProperties.STONE_AXE.enchants", Collections.singletonList("Efficiency:1"));

		configFile.addDefault("Default.ItemProperties.APPLES.quantity", 64);
		configFile.addDefault("Default.ItemProperties.APPLES.enchants", Collections.singletonList("none"));


		save();
	}

	public static KitsFileManger getFileManager() {
		return fileManager;
	}


	//	getters
	public FileConfiguration getConfig() {
		return configFile;
	}

	public File getFile() {
		return file;
	}

	//	get name
	public String getName(String kitName) {
		return configFile.getString(kitName + ".Name");
	}

	//	get items
	public String[] getItems(String kitName) {
		return configFile.getStringList(kitName + ".Items").toArray(new String[0]);
	}

	//	get item quantity
	public int getItemQuantity(String kitName, String itemName) {
		return configFile.getInt(kitName + ".ItemProperties." + itemName + ".quantity");
	}

	//	get item enchants
	public String[] getItemEnchants(String kitName, String itemName) {
		return configFile.getStringList(kitName + ".ItemProperties." + itemName + ".enchants").toArray(new String[0]);
	}

	//	get menu size
	public int getMenuSize() {
		return configFile.getInt("Menu-Size");
	}

	//	get all kits
	public String[] getKits() {
		return configFile.getKeys(false).toArray(new String[0]);
	}


	public ItemStack applyEnchantments(ItemStack itemStack, String[] enchantmentStrings) {
		ItemMeta itemMeta = itemStack.getItemMeta();

		for (String enchantmentString : enchantmentStrings) {
			String[] enchantmentParts = enchantmentString.split(":");
			if (enchantmentParts.length == 2) {
				String enchantmentName = enchantmentParts[0];
				int enchantmentLevel = Integer.parseInt(enchantmentParts[1]);

				Enchantment enchantment = Enchantment.getByName(enchantmentName);
				if (enchantment != null) {
					itemMeta.addEnchant(enchantment, enchantmentLevel, true);
				}
			}
		}

		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	// In your code where you retrieve items from your config
	public ItemStack[] getItem(String kitName) {
		List<String> itemStrings = configFile.getStringList(kitName + ".Items");
		ItemStack[] items = new ItemStack[itemStrings.size()];

		for (int i = 0; i < itemStrings.size(); i++) {
			String itemString = itemStrings.get(i);
			String[] itemParts = itemString.split(":");
			if (itemParts.length >= 2) {
				String itemName = itemParts[0];
				int quantity = Integer.parseInt(itemParts[1]);

				ItemStack itemStack = new ItemStack(Material.matchMaterial(itemName), quantity);
				String[] enchantments = getItemEnchants(kitName, itemName);
				itemStack = applyEnchantments(itemStack, enchantments);

				items[i] = itemStack;
			}
		}

		return items;
	}


}
