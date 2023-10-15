package me.firedragon5.islanddefender.filemanager.player;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

public class PlayerFileManager {

//	Folder structure
//	plugins/islanddefender/players/<playername>.yml


	// Add the player to the folder
	public static void addPlayer(Player playerName) {

		UUID playerUUID = playerName.getUniqueId();

//		Check if the folder exists
		File playerFolder = new File("plugins/islanddefender/players");

		if (!playerFolder.exists()) {
			playerFolder.mkdirs();
		}


//		Check if the file exists
		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (!playerFile.exists()) {
			try {
				playerFile.createNewFile();

//				Add default values
				FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

//				clan name
				playerConfig.set("clan-name", "none");
//				rank
				playerConfig.set("rank", "default");
//				coins
				playerConfig.set("coins", 0);
//				crystals
				playerConfig.set("crystals", 0);
//				mana
				playerConfig.set("mana", 0);
//				mine
				playerConfig.set("mine", "Default");

				playerConfig.save(playerFile);
				playerConfig.options().copyDefaults(true);
				playerConfig.save(playerFile);


			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	//	make a method that checks if the players has all the correct stuff in their yml file
	public static void checkPlayer(Player playerName) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			if (playerConfig.getString("clan-name") == null) {
				playerConfig.set("clan-name", "none");
			}
			if (playerConfig.getString("rank") == null) {
				playerConfig.set("rank", "Default");
			}

//			Coins
			if (playerConfig.getString("coins") == null) {
				playerConfig.set("coins", 0);
			}

//			Crystals
			if (playerConfig.getString("crystals") == null) {
				playerConfig.set("crystals", 0);
			}

//			Mana
			if (playerConfig.getString("mana") == null) {
				playerConfig.set("mana", 0);
			}

//			mine
			if (playerConfig.getString("mine") == null) {
				playerConfig.set("mine", "Default");
			}


			try {
				playerConfig.save(playerFile);
				playerConfig.options().copyDefaults(true);
				playerConfig.save(playerFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	//	Get player clan name
	public static String getPlayerClanName(Player playerName) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			return playerConfig.getString("clan-name");
		}

//		Return null if the player file does not exist
		return null;

	}

	//	Get mine name
	public static String getPlayerMine(Player playerName) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			return playerConfig.getString("mine");
		}

		return null;
	}

	//	Set mine name
	public static void setPlayerMine(Player playerName, String mineName) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			playerConfig.set("mine", mineName);

			try {
				playerConfig.save(playerFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}


	//	Get player rank
	public static String getPlayerRank(Player playerName) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			return playerConfig.getString("rank");
		}

		return null;
	}

	//	Set player clan name
	public static void setPlayerClanName(Player playerName, String clanName) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			playerConfig.set("clan-name", clanName);


			try {
				playerConfig.save(playerFile);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			addPlayer(playerName);
			setPlayerClanName(playerName, clanName);
		}
	}

	//	Set player rank
	public static void setPlayerRank(Player playerName, String rank) {

//		get the player uuid
		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			playerConfig.set("rank", rank);

			try {
				playerConfig.save(playerFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}


	}

	//	Get player coins
	public static int getPlayerCoins(Player playerName) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			return playerConfig.getInt("coins");
		}

		return 0;
	}

	//	Set player coins
	public static void setPlayerCoins(Player playerName, int coins) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			playerConfig.set("coins", coins);

			try {
				playerConfig.save(playerFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	//	Get player crystals
	public static int getPlayerCrystals(Player playerName) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			return playerConfig.getInt("crystals");
		}

		return 0;
	}

	//	Set player crystals
	public static void setPlayerCrystals(Player playerName, int crystals) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			playerConfig.set("crystals", crystals);

			try {
				playerConfig.save(playerFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	//	Get player mana
	public static int getPlayerMana(Player playerName) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			return playerConfig.getInt("mana");
		}

		return 0;
	}

	//	Set player mana
	public static void setPlayerMana(Player playerName, int mana) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			playerConfig.set("mana", mana);

			try {
				playerConfig.save(playerFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	//	Remove Coins
	public static void removePlayerCoins(Player playerName, int coins) {
		int currentCoins = getPlayerCoins(playerName);
		int newCoins = currentCoins - coins;
		setPlayerCoins(playerName, newCoins);
	}

	//	Add Coins
	public static void addPlayerCoins(Player playerName, int coins) {
		int currentCoins = getPlayerCoins(playerName);
		int newCoins = currentCoins + coins;
		setPlayerCoins(playerName, newCoins);
	}

	//	Remove Crystals
	public static void removePlayerCrystals(Player playerName, int crystals) {
		int currentCrystals = getPlayerCrystals(playerName);
		int newCrystals = currentCrystals - crystals;
		setPlayerCrystals(playerName, newCrystals);
	}

	//	Add Crystals
	public static void addPlayerCrystals(Player playerName, int crystals) {
		int currentCrystals = getPlayerCrystals(playerName);
		int newCrystals = currentCrystals + crystals;
		setPlayerCrystals(playerName, newCrystals);
	}

	//	Remove Mana
	public static void removePlayerMana(Player playerName, int mana) {
		int currentMana = getPlayerMana(playerName);
		int newMana = currentMana - mana;
		setPlayerMana(playerName, newMana);
	}

	//	Add Mana
	public static void addPlayerMana(Player playerName, int mana) {
		int currentMana = getPlayerMana(playerName);
		int newMana = currentMana + mana;
		setPlayerMana(playerName, newMana);
	}


}
