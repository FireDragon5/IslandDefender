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
				playerConfig.set("rank", "none");
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
		}
	}


}
