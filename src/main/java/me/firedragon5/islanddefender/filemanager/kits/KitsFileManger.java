package me.firedragon5.islanddefender.filemanager.kits;

import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.*;

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
		configFile.addDefault("Default.Display-Name", "Default");
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

//		Material to display the block in the menu
		configFile.addDefault("Default.Material", "DIAMOND_SWORD");

		// For each item, you can specify additional properties like enchants
		configFile.addDefault("Default.ItemProperties.STONE_SWORD.quantity", 1);
		configFile.addDefault("Default.ItemProperties.STONE_SWORD.enchants", Collections.singletonList("none"));

		configFile.addDefault("Default.ItemProperties.STONE_PICKAXE.quantity", 1);
		configFile.addDefault("Default.ItemProperties.STONE_PICKAXE.enchants", Arrays.asList("Efficiency:1", "Unbreaking: 1"));

		configFile.addDefault("Default.ItemProperties.STONE_AXE.quantity", 1);
		configFile.addDefault("Default.ItemProperties.STONE_AXE.enchants", Collections.singletonList("Efficiency: 1"));

		configFile.addDefault("Default.ItemProperties.APPLES.quantity", 64);
		configFile.addDefault("Default.ItemProperties.APPLES.enchants", Collections.singletonList("none"));

//		Permissions to use the kit
		configFile.addDefault("Default.Permission", "islanddefender.kit.default");

//		Time to wait before using the kit again
		configFile.addDefault("Default.Cooldown-min", 0);

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
		return configFile.getString(kitName + ".Display-Name");
	}

	// get items
	public String[] getItems(String kitName) {
		List<String> itemsWithUnderscores = configFile.getStringList(kitName + ".Items");
		List<String> itemsWithoutUnderscores = new ArrayList<>();

		for (String item : itemsWithUnderscores) {
			// Remove underscores and convert to lowercase
			String formattedItem = item.replace("_", " ");
//			make the first letter uppercase
			formattedItem = formattedItem.substring(0, 1).toUpperCase() + formattedItem.substring(1);
			itemsWithoutUnderscores.add(formattedItem);
		}

		return itemsWithoutUnderscores.toArray(new String[0]);
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
		return configFile.getKeys(false).stream().filter(key -> !key.equalsIgnoreCase("Menu-Size")).toArray(String[]::new);
	}

	//	Get material
	public Material getMaterial(String kitName) {
		String materialName = configFile.getString(kitName + ".Material");
		if (materialName == null) {
			return Material.STONE;  // You can use any default material you prefer.
		}

		Material material = Material.matchMaterial(materialName);
		// Handle invalid material names gracefully.
		return Objects.requireNonNullElse(material, Material.STONE);
	}


	public ItemStack applyEnchantments(ItemStack itemStack, String[] enchantmentStrings) {
		ItemMeta itemMeta = itemStack.getItemMeta();

		for (String enchantmentString : enchantmentStrings) {
			String[] enchantmentParts = enchantmentString.split(":");
			if (enchantmentParts.length == 2) {
				String enchantmentName = enchantmentParts[0];
				int enchantmentLevel = Integer.parseInt(enchantmentParts[1]);

				Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(enchantmentName.toLowerCase())); // Use the lowercase name
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

				Material material = Material.matchMaterial(itemName);
				if (material != null) {
					ItemStack itemStack = new ItemStack(material, quantity);
					String[] enchantments = getItemEnchants(kitName, itemName);
					itemStack = applyEnchantments(itemStack, enchantments);

					items[i] = itemStack;
				}
			}
		}

		return items;
	}


	public void giveKit(Player player, String kitName) {
		ItemStack[] items = getItem(kitName);
		player.getInventory().addItem(items);

		UtilsMessage.sendMessage(player, "&aYou have claimed the &7" + getName(kitName) + " &akit!");
		player.closeInventory();

	}

	//	Get permission
	public String getPermission(String kitName) {
		return configFile.getString(kitName + ".Permission");
	}

	//	Get cooldown
	public int getCoolDown(String kitName) {
		return configFile.getInt(kitName + ".Cooldown-min");
	}


}
